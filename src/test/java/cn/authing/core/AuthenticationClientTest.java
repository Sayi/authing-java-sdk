package cn.authing.core;

import cn.authing.core.auth.AuthenticationClient;
import cn.authing.core.graphql.GraphQLException;
import cn.authing.core.graphql.GraphQLResponse;
import cn.authing.core.http.Callback;
import cn.authing.core.types.*;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AuthenticationClientTest {
    private AuthenticationClient authenticationClient;

    private String randomString() {
        return Integer.toString(new Random().nextInt());
    }

    @Before
    public void before() {
        authenticationClient = new AuthenticationClient("5f8d2827feaa6e31598fda94");
        authenticationClient.setHost("https://core.authing.cn");
    }

    @Test
    public void registerByEmail() throws IOException, GraphQLException {
        String email = randomString() + "@gmail.com";
        String password = "123456";
        User user = authenticationClient.registerByEmail(new RegisterByEmailInput(email, password)).execute();
        Assert.assertEquals(user.getEmail(), email);
    }

    @Test
    public void registerByUsername() throws IOException, GraphQLException {
        String username = randomString();
        String password = "123456";
        User user = authenticationClient.registerByUsername(new RegisterByUsernameInput(username, password)).execute();
        Assert.assertEquals(user.getUsername(), username);
    }

    @Test
    public void registerByPhonePhoneCode() throws IOException, GraphQLException {
        String phone = "18311302182";
        String code = "123456";
        User user = authenticationClient.registerByPhoneCode(new RegisterByPhoneCodeInput(phone, code)).execute();
        Assert.assertEquals(user.getPhone(), phone);
    }

    @Test
    public void sendSmsCode() throws IOException, ExecutionException, InterruptedException {
//        String phone = "18311302182";
//        try{
//            authenticationClient.sendSmsCode("111111").execute();
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }

        CompletableFuture<CommonMessage> future = new CompletableFuture<>();
        authenticationClient.sendSmsCode("111111").enqueue(new Callback<CommonMessage>() {
            @Override
            public void onSuccess(CommonMessage result) {
                future.complete(result);
                System.out.print(result);
            }

            @Override
            public void onFailure(@Nullable GraphQLResponse.ErrorInfo error) {
                System.out.print(error);
                future.complete(null);
            }
        });
        Assert.assertNotNull(future.get());
    }

    @Test
    public void loginByEmail() throws IOException, GraphQLException {
        String email = "test@test.com";
        String password = "123456";
        User user = authenticationClient.loginByEmail(new LoginByEmailInput(email, password)).execute();
        Assert.assertEquals(user.getEmail(), email);
    }

    @Test
    public void loginByWechat() throws IOException, GraphQLException {
        String code = "021I11Ga1hP9Uz0GJbGa1SVgxo4I11G7";
        User user = authenticationClient.loginByWechat(code).execute();
        Assert.assertNotNull(user);
    }

    @Test
    public void loginByUsername() throws IOException, GraphQLException, ExecutionException, InterruptedException {
        String username = "test";
        String password = "andy123456";
        CompletableFuture<User> future = new CompletableFuture<>();
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).enqueue(new Callback<User>() {
            @Override
            public void onSuccess(User result) {
                future.complete(result);
                System.out.print(result);
            }

            @Override
            public void onFailure(@Nullable GraphQLResponse.ErrorInfo error) {
                System.out.print(error);
                future.complete(null);
            }
        });
        Assert.assertNotNull(future.get());
    }

    @Test
    public void loginByPhoneCode() throws IOException, GraphQLException {
        String phone = "18311302182";
        String code = "2190";
        User user = authenticationClient.loginByPhoneCode(new LoginByPhoneCodeInput(phone, code)).execute();
        Assert.assertEquals(user.getPhone(), phone);
    }

    @Test
    public void loginByPhonePassword() throws IOException, GraphQLException {
        String phone = "18311302182";
        String password = "123456";
        User user = authenticationClient.loginByPhonePassword(new LoginByPhonePasswordInput(phone, password)).execute();
        Assert.assertEquals(user.getPhone(), phone);
    }

    @Test
    public void checkLoginStatus() throws IOException, GraphQLException {
        String username = "test";
        String password = "123456";
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).execute();

        JwtTokenStatus status = authenticationClient.checkLoginStatus().execute();
        Assert.assertEquals(status.getCode().intValue(), 200);
    }

    @Test
    public void sendEmail() throws IOException, GraphQLException {
        authenticationClient.sendEmail("1498881550@qq.com", EmailScene.RESET_PASSWORD).execute();
    }

    @Test
    public void resetPasswordByPhoneCode() throws IOException, GraphQLException {
        authenticationClient.resetPasswordByPhoneCode("18311302182", "1234", "123456").execute();
    }

    @Test
    public void resetPasswordByEmailCode() throws IOException, GraphQLException {
        authenticationClient.resetPasswordByEmailCode("1498881550@qq.com", "1234", "123456").execute();
    }

    @Test
    public void updateProfile() throws IOException, GraphQLException {
        String username = "test";
        String password = "123456";
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).execute();

        User user = authenticationClient.updateProfile(new UpdateUserInput().withNickname("nickname")).execute();
        Assert.assertEquals(user.getNickname(), "nickname");
    }

    @Test
    public void updatePassword() throws IOException, GraphQLException {
        String username = "test";
        String password = "123456";
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).execute();

        User user = authenticationClient.updatePassword("111111", "123456").execute();
        Assert.assertNotNull(user);
    }

    @Test
    public void updatePhone() throws IOException, GraphQLException {
        String username = "test";
        String password = "123456";
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).execute();

        User user = authenticationClient.updatePhone("18311302182", "1234").execute();
        Assert.assertNotNull(user);
    }

    @Test
    public void updateEmail() throws IOException, GraphQLException {
        String username = "test";
        String password = "123456";
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).execute();

        User user = authenticationClient.updateEmail("1498881550@qq.com", "1234").execute();
        Assert.assertNotNull(user);
    }

    @Test
    public void refreshToken() throws IOException, GraphQLException {
        String username = "test";
        String password = "123456";
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).execute();

        RefreshToken token = authenticationClient.refreshToken().execute();
        Assert.assertNotNull(token.getToken());
    }

    @Test
    public void bindPhone() throws IOException, GraphQLException {
        String username = "test";
        String password = "123456";
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).execute();

        User user = authenticationClient.bindPhone("18311302182", "1234").execute();
        Assert.assertEquals(user.getPhone(), "18311302182");
    }

    @Test
    public void unbindPhone() throws IOException, GraphQLException {
        String username = "test";
        String password = "123456";
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).execute();

        User user = authenticationClient.unbindPhone().execute();
        Assert.assertNotNull(user);
    }

    @Test
    public void Logout() throws IOException, GraphQLException {
        String username = "test";
        String password = "123456";
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).execute();

        authenticationClient.logout().execute();
    }

    @Test
    public void listUdv() throws IOException, GraphQLException {
        String username = "test";
        String password = "123456";
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).execute();

        List<UserDefinedData> udv = authenticationClient.listUdv().execute();
        Assert.assertEquals(0, udv.size());
    }

    @Test
    public void listOrgs() throws IOException, GraphQLException {
        String username = "test";
        String password = "test";
        authenticationClient.loginByUsername(new LoginByUsernameInput(username, password)).execute();

        List<List<Org>> orgs = authenticationClient.listOrgs().execute();
        Assert.assertEquals(0, orgs.size());
    }
}
