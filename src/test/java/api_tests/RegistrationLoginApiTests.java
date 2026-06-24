package api_tests;

import dto.User;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseApi;
import utils.PropertiesReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.UserFactory.*;

public class RegistrationLoginApiTests implements BaseApi {

    @Test
    public void registrationApiPositiveTest() {
        User user = positiveUser();
        //System.out.println(user);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(response.code());
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void registrationApiNegativeWrongPasswordTest() {
        User user = positiveUser();
        user.setPassword("qwerty123!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(response.code());
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public void registrationApiNegativeWrongFormatTest() {
        User user = positiveUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), TEXT);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(response.code());
        Assert.assertEquals(response.code(), 500);
    }

    @Test
    public void registrationApiNegativeWrongKeyEmailTest() {
        User user = positiveUser();
        Map<String,String> wrongJson = new HashMap<>();
        wrongJson.put("email", user.getUsername());
        wrongJson.put("password", user.getPassword());
        RequestBody requestBody = RequestBody.create(GSON.toJson(wrongJson), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(response.code());
        Assert.assertEquals(response.code(), 500);
    }

    @Test
    public void loginApiPositiveTest() {
        User user = User.builder()
                .username(PropertiesReader.getProperty
                        ("base.properties", "email"))
                .password(PropertiesReader.getProperty
                        ("base.properties", "password"))
                .build();
        //System.out.println(user);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(response.code());
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void loginApiNegativeWrongPasswordTest() {
        User user = User.builder()
                .username(PropertiesReader.getProperty
                        ("base.properties", "email"))
                .password("Password12!")
                .build();
        //System.out.println(user);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(response.code());
        Assert.assertEquals(response.code(), 401);
    }
}
