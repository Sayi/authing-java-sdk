package cn.authing.sdk.java.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.authing.sdk.java.dto.SendResetPasswordNotificationDto;

public class UpdateUserOptionsDto {
    /**
     * 用户 ID 类型，默认值为 `user_id`，可选值为：
     * - `user_id`: Authing 用户 ID，如 `6319a1504f3xxxxf214dd5b7`
     * - `phone`: 用户手机号
     * - `email`: 用户邮箱
     * - `username`: 用户名
     * - `external_id`: 用户在外部系统的 ID，对应 Authing 用户信息的 `externalId` 字段
     * - `identity`: 用户的外部身份源信息，格式为 `<extIdpId>:<userIdInIdp>`，其中 `<extIdpId>` 为 Authing 身份源的 ID，`<userIdInIdp>` 为用户在外部身份源的 ID。
     * 示例值：`62f20932716fbcc10d966ee5:ou_8bae746eac07cd2564654140d2a9ac61`。
     *
     */
    @JsonProperty("userIdType")
    private UserIdType userIdType;
    /**
     * 设置用户首次登录要求重置密码
     */
    @JsonProperty("resetPasswordOnFirstLogin")
    private Boolean resetPasswordOnFirstLogin;
    /**
     * 下次登录要求重置密码
     */
    @JsonProperty("resetPasswordOnNextLogin")
    private Boolean resetPasswordOnNextLogin;
    /**
     * 密码加密类型，支持使用 RSA256 和国密 SM2 算法进行加密。默认为 `none` 不加密。
     * - `none`: 不对密码进行加密，使用明文进行传输。
     * - `rsa`: 使用 RSA256 算法对密码进行加密，需要使用 Authing 服务的 RSA 公钥进行加密，请阅读**介绍**部分了解如何获取 Authing 服务的 RSA256 公钥。
     * - `sm2`: 使用 [国密 SM2 算法](https://baike.baidu.com/item/SM2/15081831) 对密码进行加密，需要使用 Authing 服务的 SM2 公钥进行加密，请阅读**介绍**部分了解如何获取 Authing 服务的 SM2 公钥。
     *
     */
    @JsonProperty("passwordEncryptType")
    private PasswordEncryptType passwordEncryptType;
    /**
     * 是否自动生成密码
     */
    @JsonProperty("autoGeneratePassword")
    private Boolean autoGeneratePassword;
    /**
     * 重置密码发送邮件和手机号选项
     */
    @JsonProperty("sendPasswordResetedNotification")
    private SendResetPasswordNotificationDto sendPasswordResetedNotification;

    public UserIdType getUserIdType() {
        return userIdType;
    }
    public void setUserIdType(UserIdType userIdType) {
        this.userIdType = userIdType;
    }

    public Boolean getResetPasswordOnFirstLogin() {
        return resetPasswordOnFirstLogin;
    }
    public void setResetPasswordOnFirstLogin(Boolean resetPasswordOnFirstLogin) {
        this.resetPasswordOnFirstLogin = resetPasswordOnFirstLogin;
    }

    public Boolean getResetPasswordOnNextLogin() {
        return resetPasswordOnNextLogin;
    }
    public void setResetPasswordOnNextLogin(Boolean resetPasswordOnNextLogin) {
        this.resetPasswordOnNextLogin = resetPasswordOnNextLogin;
    }

    public PasswordEncryptType getPasswordEncryptType() {
        return passwordEncryptType;
    }
    public void setPasswordEncryptType(PasswordEncryptType passwordEncryptType) {
        this.passwordEncryptType = passwordEncryptType;
    }

    public Boolean getAutoGeneratePassword() {
        return autoGeneratePassword;
    }
    public void setAutoGeneratePassword(Boolean autoGeneratePassword) {
        this.autoGeneratePassword = autoGeneratePassword;
    }

    public SendResetPasswordNotificationDto getSendPasswordResetedNotification() {
        return sendPasswordResetedNotification;
    }
    public void setSendPasswordResetedNotification(SendResetPasswordNotificationDto sendPasswordResetedNotification) {
        this.sendPasswordResetedNotification = sendPasswordResetedNotification;
    }


    /**
     * 用户 ID 类型，默认值为 `user_id`，可选值为：
     * - `user_id`: Authing 用户 ID，如 `6319a1504f3xxxxf214dd5b7`
     * - `phone`: 用户手机号
     * - `email`: 用户邮箱
     * - `username`: 用户名
     * - `external_id`: 用户在外部系统的 ID，对应 Authing 用户信息的 `externalId` 字段
     * - `identity`: 用户的外部身份源信息，格式为 `<extIdpId>:<userIdInIdp>`，其中 `<extIdpId>` 为 Authing 身份源的 ID，`<userIdInIdp>` 为用户在外部身份源的 ID。
     * 示例值：`62f20932716fbcc10d966ee5:ou_8bae746eac07cd2564654140d2a9ac61`。
     *
     */
    public static enum UserIdType {

        @JsonProperty("user_id")
        USER_ID("user_id"),

        @JsonProperty("external_id")
        EXTERNAL_ID("external_id"),

        @JsonProperty("phone")
        PHONE("phone"),

        @JsonProperty("email")
        EMAIL("email"),

        @JsonProperty("username")
        USERNAME("username"),

        @JsonProperty("identity")
        IDENTITY("identity"),
        ;

        private String value;

        UserIdType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 密码加密类型，支持使用 RSA256 和国密 SM2 算法进行加密。默认为 `none` 不加密。
     * - `none`: 不对密码进行加密，使用明文进行传输。
     * - `rsa`: 使用 RSA256 算法对密码进行加密，需要使用 Authing 服务的 RSA 公钥进行加密，请阅读**介绍**部分了解如何获取 Authing 服务的 RSA256 公钥。
     * - `sm2`: 使用 [国密 SM2 算法](https://baike.baidu.com/item/SM2/15081831) 对密码进行加密，需要使用 Authing 服务的 SM2 公钥进行加密，请阅读**介绍**部分了解如何获取 Authing 服务的 SM2 公钥。
     *
     */
    public static enum PasswordEncryptType {

        @JsonProperty("sm2")
        SM2("sm2"),

        @JsonProperty("rsa")
        RSA("rsa"),

        @JsonProperty("none")
        NONE("none"),
        ;

        private String value;

        PasswordEncryptType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


}