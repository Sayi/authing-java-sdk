package cn.authing.core.mgmt

import cn.authing.core.Utils
import cn.authing.core.graphql.GraphQLCall
import cn.authing.core.graphql.GraphQLResponse
import cn.authing.core.http.HttpCall
import cn.authing.core.types.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * 用户管理类
 */
class UsersManagementClient(private val client: ManagementClient) {

    /**
     * 获取用户列表
     */
    @JvmOverloads
    fun list(
        page: Int? = null,
        limit: Int? = null,
        sortBy: SortByEnum? = null
    ): GraphQLCall<UsersResponse, PaginatedUsers> {
        val param = UsersParam(page, limit, sortBy)
        return list(param)
    }

    /**
     * 获取用户列表
     */
    fun list(param: UsersParam): GraphQLCall<UsersResponse, PaginatedUsers> {
        if (null == param.withCustomData || false == param.withCustomData) {
            return client.createGraphQLCall(
                param.createRequest(),
                object : TypeToken<GraphQLResponse<UsersResponse>>() {}) {
                it.result
            }
        }

        var param1  = UsersWithCustomDataParam(param.page,param.limit,param.sortBy);
        return client.createGraphQLCall(
            param1.createRequest(),
            object : TypeToken<GraphQLResponse<UsersResponse>>() {}) {
            it.result
        }
    }

    /**
     * 创建用户
     */
    @JvmOverloads
    fun create(userInfo: CreateUserInput, options: CreateUserOptions? = null): HttpCall<RestfulResponse<User>, User> {
        val param = CreateUserParam(userInfo).withKeepPassword(options?.keepPassword)
        if (param.userInfo.password !== null) {
            param.userInfo.password = client.encrypt(param.userInfo.password!!)
        }
        return client.createHttpPostCall(
            "${client.host}/api/v2/users",
            GsonBuilder().create().toJson(param),
            object : TypeToken<RestfulResponse<User>> () {}
        ) { it.data }
    }

    /**
     * 修改用户资料
     */
    fun update(userId: String, updates: UpdateUserInput): HttpCall<RestfulResponse<User>, User> {

        if (updates.password != null && !updates.password.equals("")) {
            updates.password = client.encrypt(updates.password)
        }

        return client.createHttpPostCall(
            "${client.host}/api/v2/users/${userId}",
            GsonBuilder().create().toJson(updates),
            object : TypeToken<RestfulResponse<User>> () {}
        ) {
            it.data
        }

    }

    /**
     * 获取用户详情
     */
    fun detail(userId: String, withCustomData: Boolean): HttpCall<RestfulResponse<User>, User>  {
        return client.createHttpGetCall(
            "${client.host}/api/v2/users/$userId?with_custom_data=${withCustomData}",
            object : TypeToken<RestfulResponse<User>> () {}
        ) { it.data }
    }

    /**
     * 搜索用户
     */
    @JvmOverloads
    fun search(
        query: String, fields: List<String>? = null, page: Int? = null, limit: Int? = null
    ): GraphQLCall<SearchUserResponse, PaginatedUsers> {
        val param = SearchUserParam(query, fields, page, limit)
        return search(param)
    }

    /**
     * 搜索用户
     */
    fun search(param: SearchUserParam): GraphQLCall<SearchUserResponse, PaginatedUsers> {
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<SearchUserResponse>>() {}) {
            it.result
        }
    }

    /**
     * 查找用户
     */
    fun find(param: FindUserParam): GraphQLCall<FindUserResponse, User> {
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<FindUserResponse>>() {}) {
            it.result
        }
    }

    /**
     * 通过 ID、username、email、phone、email、externalId 批量获取用户详情
     */
    @JvmOverloads
    fun batch(
        identifiers: List<String>,
        options: BatchGetUserOptions? = null
    ): HttpCall<RestfulResponse<List<User>>, List<User>> {
        return client.createHttpPostCall(
            "${client.host}/api/v2/users/batch",
            Gson().toJson(BatchGetUserPostData(identifiers, options?.queryField)),
            object : TypeToken<RestfulResponse<List<User>>>() {}) {
            it.data
        }
    }

    /**
     * 删除用户
     */
    fun delete(userId: String): GraphQLCall<DeleteUserResponse, CommonMessage> {
        val param = DeleteUserParam(userId)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<DeleteUserResponse>>() {}) {
            it.result
        }
    }

    /**
     * 批量删除用户
     */
    fun deleteMany(userIds: List<String>): GraphQLCall<DeleteUsersResponse, CommonMessage> {
        val param = DeleteUsersParam(userIds)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<DeleteUsersResponse>>() {}) {
            it.result
        }
    }

    /**
     * 检查用户是否存在，目前可检测的字段有用户名、邮箱、手机号。
     */
    fun exists(param: IsUserExistsParam): HttpCall<RestfulResponse<Boolean>, Boolean> {
        val url = "${client.host}/api/v2/users/is-user-exists"

        return client.createHttpGetCall(
            Utils().getQueryUrl(url, param),
            object : TypeToken<RestfulResponse<Boolean>> () {}
        ) {
            it.data
        }
    }

    /**
     * 获取用户角色列表
     * TODO: 高版本删除
     */
    @Deprecated("请使用listRoles(userId: String, namespace: String?)替换此方法")
    fun listRoles(userId: String): HttpCall<RestfulResponse<PaginatedRoles>, PaginatedRoles> {
        return listRoles(userId, null);
    }

    /**
     * 获取用户角色列表
     */
    fun listRoles(userId: String, namespace: String?): HttpCall<RestfulResponse<PaginatedRoles>, PaginatedRoles> {
        var url = "${client.host}/api/v2/users/${userId}/roles"

        url += if (namespace != null) "?namespace=${namespace}" else ""

        return client.createHttpGetCall(
            url,
            object : TypeToken<RestfulResponse<PaginatedRoles>> () {}
        ) {
            it.data
        }
    }

    /**
     * 将用户加入角色
     * TODO: 高版本删除
     */
    @Deprecated("请使用addRoles(userId: String, roles: List<String>, namespace: String?)替换此方法")
    fun addRoles(userId: String, roleCodes: List<String>): HttpCall<RestfulResponse<CommonMessage>, CommonMessage> {
        return addRoles(userId, roleCodes, null);
    }

    /**
     *  将用户加入角色
     */
    fun addRoles(
        userId: String,
        roleCodes: List<String>,
        namespace: String?
    ): HttpCall<RestfulResponse<CommonMessage>, CommonMessage> {
        val options = RestAddRolesParams(userId = userId, namespace = namespace, list = roleCodes)
        return client.createHttpPostCall(
            "${client.host}/api/v2/users/${options.userId}/roles",
            GsonBuilder().create().toJson(options),
            object : TypeToken<RestfulResponse<CommonMessage>> () {}
        ) {
            it.data
        }
    }

    /**
     * 将用户从角色中移除
     * TODO: 高版本删除
     */
    @Deprecated("请使用removeRoles(userId: String, roles: List<String>, namespace: String?)替换此方法")
    fun removeRoles(userId: String, roleCodes: List<String>): GraphQLCall<RevokeRoleResponse, CommonMessage> {
        return removeRoles(userId, roleCodes, null);
    }

    /**
     * 将用户从角色中移除
     */
    fun removeRoles(
        userId: String,
        roleCodes: List<String>,
        namespace: String?
    ): GraphQLCall<RevokeRoleResponse, CommonMessage> {
        val param = RevokeRoleParam().withUserIds(listOf(userId)).withRoleCodes(roleCodes).withNamespace(namespace)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<RevokeRoleResponse>>() {}) {
            it.result
        }
    }

    /**
     * 刷新用户 token
     */
    fun refreshToken(userId: String): GraphQLCall<RefreshTokenResponse, RefreshToken> {
        val param = RefreshTokenParam(userId)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<RefreshTokenResponse>>() {}) {
            it.result
        }
    }

    /**
     * 获取用户分组列表
     */
    fun listGroups(userId: String): GraphQLCall<GetUserGroupsResponse, PaginatedGroups> {
        val param = GetUserGroupsParam(userId)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<GetUserGroupsResponse>>() {}) {
            it.result.groups!!
        }
    }

    /**
     * 将用户加入分组
     */
    fun addGroup(userId: String, group: String): GraphQLCall<AddUserToGroupResponse, CommonMessage> {
        val param = AddUserToGroupParam(listOf(userId), group)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<AddUserToGroupResponse>>() {}) {
            it.result
        }
    }

    /**
     * 将用户退出分组
     */
    fun removeGroup(userId: String, group: String): GraphQLCall<RemoveUserFromGroupResponse, CommonMessage> {
        val param = RemoveUserFromGroupParam(listOf(userId), group)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<RemoveUserFromGroupResponse>>() {}) {
            it.result
        }
    }

    /**
     * 获取策略列表
     */
    @JvmOverloads
    fun listPolicies(
        userId: String,
        page: Int = 1,
        limit: Int = 10
    ): GraphQLCall<PolicyAssignmentsResponse, PaginatedPolicyAssignments> {
        val param =
            PolicyAssignmentsParam().withTargetType(PolicyAssignmentTargetType.USER).withTargetIdentifier(userId)
                .withPage(page).withLimit(limit)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<PolicyAssignmentsResponse>>() {}) {
            it.result
        }
    }

    /**
     * 批量添加策略
     */
    fun addPolicies(
        userId: String,
        policies: List<String>
    ): GraphQLCall<AddPolicyAssignmentsResponse, CommonMessage> {
        val param =
            AddPolicyAssignmentsParam(policies, PolicyAssignmentTargetType.USER).withTargetIdentifiers(listOf(userId))
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<AddPolicyAssignmentsResponse>>() {}) {
            it.result
        }
    }

    /**
     * 批量移除策略
     */
    fun removePolicies(
        userId: String,
        policies: List<String>
    ): GraphQLCall<RemovePolicyAssignmentsResponse, CommonMessage> {
        val param = RemovePolicyAssignmentsParam(
            policies,
            PolicyAssignmentTargetType.USER
        ).withTargetIdentifiers(listOf(userId))
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<RemovePolicyAssignmentsResponse>>() {}) {
            it.result
        }
    }

    /**
     * 获取当前用户的自定义数据列表
     */
    fun listUdv(userId: String): GraphQLCall<UdvResponse, List<UserDefinedData>> {
        val param = UdvParam(UdfTargetType.USER, userId)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<UdvResponse>>() {}) {
            it.result
        }
    }

    /**
     * 添加自定义数据
     */
    fun setUdv(userId: String, key: String, value: Any): GraphQLCall<SetUdvResponse, List<UserDefinedData>> {
        val json = Gson()
        val param = SetUdvParam(UdfTargetType.USER, userId, key, json.toJson(value))
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<SetUdvResponse>>() {}) {
            it.result
        }
    }

    /**
     * 移除自定义数据
     */
    fun removeUdv(userId: String, key: String): GraphQLCall<RemoveUdvResponse, List<UserDefinedData>> {
        val param = RemoveUdvParam(UdfTargetType.USER, userId, key)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<RemoveUdvResponse>>() {}) {
            it.result
        }
    }

    /**
     * 获取用户所在组织机构
     */
    fun listOrgs(userId: String): HttpCall<RestfulResponse<List<List<Org>>>, List<List<Org>>> {
        return client.createHttpGetCall(
            "${client.host}/api/v2/users/${userId}/orgs",
            object : TypeToken<RestfulResponse<List<List<Org>>>>() {}) {
            it.data
        }
    }

    /**
     * 获取用户被授权的所有资源
     */
    fun listAuthorizedResources(
        userId: String,
        namespace: String
    ): GraphQLCall<ListUserAuthorizedResourcesResponse, PaginatedAuthorizedResources> {
        val param = ListUserAuthorizedResourcesParam(userId, namespace)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<ListUserAuthorizedResourcesResponse>>() {}) {
            it.result.authorizedResources!!
        }
    }

    /**
     * 获取用户被授权的所有资源
     */
    fun listAuthorizedResources(
        param: ListUserAuthorizedResourcesParam
    ): GraphQLCall<ListUserAuthorizedResourcesResponse, PaginatedAuthorizedResources> {
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<ListUserAuthorizedResourcesResponse>>() {}) {
            it.result.authorizedResources!!
        }
    }

    /**
     * 获取某个用户的所有自定义数据
     *
     */
    fun getUdfValue(userId: String): HttpCall<RestfulResponse<List<UserDefinedData>>, Map<String, Any>> {

        return client.createHttpGetCall(
            "${client.host}/api/v2/udfs/values?targetId=${userId}&targetType=${UdfTargetType.USER}",
            object : TypeToken<RestfulResponse<List<UserDefinedData>>> () {}
        ) {
            convertUdvToKeyValuePair(it.data)
        }

    }

    /**
     * 批量获取多个用户的自定义数据
     *
     */
    fun getUdfValueBatch(userIds: List<String>): GraphQLCall<UdfValueBatchResponse, Map<String, Map<String, Any>>> {
        if (userIds.isEmpty()) {
            throw Exception("userIds can't be null")
        }

        val param = UdfValueBatchParam(UdfTargetType.USER, userIds)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<UdfValueBatchResponse>>() {}) {
            val hashtable = Hashtable<String, Map<String, Any>>()
            it.result.map { hashtable.put(it.targetId, convertUdvToKeyValuePair(it.data)) }
            hashtable
        }
    }

    /**
     * 设置某个用户的自定义数据
     */
    fun setUdfValue(
        userId: String,
        data: Map<String, String>
    ): HttpCall<RestfulResponse<List<UserDefinedData>>, List<UserDefinedData>> {
        val params = RestSetUdfValueParams(UdfTargetType.USER, userId, data)

        return client.createHttpPostCall(
            "${client.host}/api/v2/udvs",
            GsonBuilder().create().toJson(params),
            object : TypeToken<RestfulResponse<List<UserDefinedData>>> () {}
        ) {
            it.data
        }
    }

    /**
     * 批量设置自定义数据
     */
    fun setUdfValueBatch(input: List<SetUdfValueBatchInputItem>): GraphQLCall<SetUdvBatchResponse, List<UserDefinedData>> {
        if (input.isEmpty()) {
            throw Exception("empty input list")
        }

        val inputList = input.flatMap { item ->
            item.data.map { SetUdfValueBatchInput(item.userId, it.key, Gson().toJson(it.value)) }
        }

        println(input)
        val param = SetUdfValueBatchParam(UdfTargetType.USER, inputList)

        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<SetUdvBatchResponse>>() {})
        {
            it.result
        }
    }

    /**
     * 清除用户的自定义数据
     */
    fun removeUdfValue(userId: String, key: String): GraphQLCall<RemoveUdvResponse, List<UserDefinedData>> {
        return removeUdv(userId, key);
    }

    /**
     * 判断用户是否有某个角色
     */
    fun hasRole(
        option: IHasRoleParam
    ): Boolean {
        val (totalCount, list) = this.listRoles(option.userId, option.namespace).execute()

        if (totalCount < 1) return false

        var hasRole = false;

        list.forEach { item -> if (item.code == option.roleCode) hasRole = true }

        return hasRole
    }

    /**
     * 强制一批用户下线
     */
    fun kick(
        userIds: List<String>
    ): HttpCall<RestfulResponse<Boolean>, Boolean> {
        val url =
            "${client.host}/api/v2/users/kick"

        val body = "{ \"userIds\": ${GsonBuilder().create().toJson(userIds)} }"

        return this.client.createHttpPostCall(
            url,
            body,
            object : TypeToken<RestfulResponse<Boolean>>() {}) { it.code == 200 }
    }

    /**
     * 查看用户操作日志
     */
    @JvmOverloads
    fun listUserActions(
        options: ListUserActionsParams? = ListUserActionsParams()
    ): HttpCall<RestfulResponse<UserActions>, UserActions> {
        var url = "${client.host}/api/v2/analysis/user-action?page=${options?.page}&limit=${options?.limit}"

        url += if (options?.clientIp != null) "&clientip=${options.clientIp}" else ""
        url += if (options?.operationName != null) "&operation_name=${options.operationName}" else ""
        url += if (options?.operatoArn != null) "&operator_arn=${options.operatoArn}" else ""

        return client.createHttpGetCall(
            url,
            object : TypeToken<RestfulResponse<UserActions>>() {}
        ) { it.data }
    }

    /**
     * 获取用户所在部门
     */
    fun listDepartment(
        userId: String
    ): HttpCall<RestfulResponse<Pagination<UserDepartment>>, Pagination<UserDepartment>> {
        return client.createHttpGetCall(
            "${client.host}/api/v2/users/${userId}/departments",
            object : TypeToken<RestfulResponse<Pagination<UserDepartment>>> () {}
        ) {
            it.data
        }
    }

    /**
     * 检查用户登录状态
     */
    fun checkLoginStatus(
        param: CheckLoginStatusParams
    ): HttpCall<RestfulResponse<UserCheckLoginStatusResponse>, UserCheckLoginStatusResponse> {
        var url = "${client.host}/api/v2/users/login-status?userId=${param.userId}"

        url += if (param.appId != null) "&appId=${param.appId}" else ""
        url += if (param.deviceId != null) "&deviceId=${param.deviceId}" else ""

        return client.createHttpGetCall(
            url,
            object : TypeToken<RestfulResponse<UserCheckLoginStatusResponse>>() {}) { it.data }
    }

    /**
     * 用户池管理员手动将任意一个身份绑定到一个用户上
     */
    fun identityLink(options:IdentityLinkParam):HttpCall<CommonMessage, CommonMessage> {
        return client.createHttpPostCall(
            "${client.host}/api/v2/users/identity/link",
            GsonBuilder().create().toJson(options),
            object : TypeToken<CommonMessage>() {}
        ) {
            it
        }
    }

    /**
     *用户池管理员手动给用户解绑身份信息，例如解绑用户 A 的微信身份
     */
    fun unIdentityLink(options:UnIdentityLinkParam):HttpCall<CommonMessage, CommonMessage> {
        return client.createHttpPostCall(
            "${client.host}/api/v2/users/identity/unlink",
            GsonBuilder().create().toJson(options),
            object : TypeToken<CommonMessage>() {}
        ) {
            it
        }
    }

    /**
     *
    解除用户某个身份源下的所有身份（以用户身份调用）
     */
    fun unlinkByUser(options:UnlinkByUserParam):HttpCall<CommonMessage, CommonMessage> {
        return client.createHttpPostCall(
            "${client.host}/api/v2/users/identity/unlinkByUser",
            GsonBuilder().create().toJson(options),
            object : TypeToken<CommonMessage>() {}
        ) {
            it
        }
    }

    /**
     * 用户退出登录
     */
    fun logout(options: UserLogoutParams): HttpCall<RestfulResponse<Boolean>, Boolean> {

        var url = "${client.host}/logout?userId=${options.userId}"

        url += if (options.appId != null) "&appId=${options.appId}" else ""

        return client.createHttpGetCall(
            url,
            object : TypeToken<RestfulResponse<Boolean>> () {}
        ) {
            it.code == 200
        }
    }

    /**
     * 获取已归档用户列表
     */
    fun listArchivedUsers(page: Int, limit: Int): GraphQLCall<ArchivedUsersResponse, PaginatedUsers> {
        val param = ArchivedUsersParam(page, limit)
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<ArchivedUsersResponse>>() {}) {
            it.result
        }
    }

    /**
     * 发送首次登录验证邮件
     */
    fun sendFirstLoginVerifyEmail(appId: String, userId: String): GraphQLCall<SendFirstLoginVerifyEmailResponse, CommonMessage> {
        val param = SendFirstLoginVerifyEmailParam(appId, userId);
        return client.createGraphQLCall(
            param.createRequest(),
            object : TypeToken<GraphQLResponse<SendFirstLoginVerifyEmailResponse>>() {}) {
            it.sendFirstLoginVerifyEmail
        }
    }

}