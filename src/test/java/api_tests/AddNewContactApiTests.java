package api_tests;

import dto.*;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;
import utils.PropertiesReader;

import static utils.ContactFactory.*;

import java.io.IOException;

public class AddNewContactApiTests
        implements BaseApi, ILogin {
    TokenDto tokenDto;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login() {
        tokenDto = loginGetToken();
    }

    @Test
    public void addNewContactApiPositiveTest() {
        Contact contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            System.out.println(response.message());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 200);
    }

    @Test
    public void addNewContactApiPositiveTestWithSoftAssert() {
        Contact contact = positiveContact();
        System.out.println(contact);
        System.out.println(tokenDto.getToken());
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        try {Response response = OK_HTTP_CLIENT
                    .newCall(request).execute();
            softAssert.assertEquals(response.code(), 200,
                    "validate status code");
            ResponseMessageDto responseMessageDto = GSON.fromJson
                    (response.body().string(), ResponseMessageDto.class);
            System.out.println(responseMessageDto);
            softAssert.assertTrue(responseMessageDto.
                    getMessage().contains("Contact was added!"),
                    "validate message");
            softAssert.assertAll();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("created exception");
        }
    }

    @Test
    public void addNewContactApiNegativeTest() {
        Contact contact = positiveContact();
        contact.setPhone("3452621");
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader(AUTH, tokenDto.getToken())
                .post(requestBody)
                .build();
        try {Response response = OK_HTTP_CLIENT
                .newCall(request).execute();
            softAssert.assertEquals(response.code(), 400,
                    "validate status code");
            System.out.println(response.message());
            softAssert.assertTrue(response.message()
                    .contains("Bad Request"),
                    "validate message");
            ErrorMessageDto errorMessageDto = GSON.fromJson
                    (response.body().string(), ErrorMessageDto.class);
            System.out.println(errorMessageDto);
            softAssert.assertTrue(errorMessageDto.getError()
                            .contains("Bad Request"),
                    "validate errorName");
            softAssert.assertEquals(errorMessageDto.getMessage().toString()
                            ,"{phone=Phone number must contain only digits! And length min 10, max 15!}",
                    "validate errorMessage");
            softAssert.assertAll();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("created exception");
        }
    }

    @Test
    public void addNewContactApiNegativeWOAuthorizationTest() {
        Contact contact = positiveContact();
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .post(requestBody)
                .build();
        try {Response response = OK_HTTP_CLIENT
                .newCall(request).execute();
            softAssert.assertEquals(response.code(), 403,
                    "validate status code");
            System.out.println(response.message());
            softAssert.assertTrue(response.message()
                            .contains("Forbidden"),
                    "validate message");
            softAssert.assertAll();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("created exception");
        }
    }

    @Test
    public void addNewContactApiNegativeWrongTokenTest() {
        Contact contact = positiveContact();
        RequestBody requestBody = RequestBody.create
                (GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader(AUTH, "vgthyu")
                .post(requestBody)
                .build();
        try {Response response = OK_HTTP_CLIENT
                .newCall(request).execute();
            softAssert.assertEquals(response.code(), 401,
                    "validate status code");
            System.out.println(response.message());
            softAssert.assertTrue(response.message()
                            .contains("Unauthorized"),
                    "validate message");
            softAssert.assertAll();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("created exception");
        }
    }
}
