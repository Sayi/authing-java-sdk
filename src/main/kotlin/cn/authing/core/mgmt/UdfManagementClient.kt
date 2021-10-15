package cn.authing.core.mgmt

import cn.authing.core.graphql.GraphQLCall
import cn.authing.core.graphql.GraphQLResponse
import cn.authing.core.types.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * 用户自定义字段管理类
 */
class UdfManagementClient(private val client: ManagementClient) {
    /**
     * 获取自定义字段定义
     */
    fun list(targetType: UdfTargetType): GraphQLCall<UdfResponse, List<UserDefinedField>> {
        val param = UdfParam(targetType)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<UdfResponse>>() {}) {
            it.result
        }
    }

    /**
     * 设置自定义字段元数据
     */
    fun set(
        targetType: UdfTargetType,
        key: String,
        dataType: UdfDataType,
        label: String
    ): GraphQLCall<SetUdfResponse, UserDefinedField> {
        val param = SetUdfParam(targetType, key, dataType, label)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<SetUdfResponse>>() {}) {
            it.result
        }
    }

    /**
     * 删除自定义字段
     */
    fun remove(targetType: UdfTargetType, key: String): GraphQLCall<RemoveUdfResponse, CommonMessage> {
        val param = RemoveUdfParam(targetType, key)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<RemoveUdfResponse>>() {}) {
            it.result
        }
    }

    /**
     * 批量添加自定义数据
     */
    fun setUdvBatch(
        targetType: UdfTargetType,
        targetId: String,
        udvList: List<UserDefinedDataInput>? = null
    ): GraphQLCall<SetUdvBatchResponse, List<UserDefinedData>> {
        val param = SetUdvBatchParam(targetType, targetId).withUdvList(udvList)

        udvList?.forEach { item -> item.value = GsonBuilder().create().toJson(item.value) }

        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<SetUdvBatchResponse>>() {}) { it.result }
    }

    /**
     * 获取某一实体的自定义字段数据列表
     */
    fun listUdv(
        targetType: UdfTargetType,
        targetId: String
    ): GraphQLCall<UdvResponse, List<UserDefinedData>> {
        val param = UdvParam(targetType, targetId)

        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<UdvResponse>>() {}) { it.result }

    }
}