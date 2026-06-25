package api_tests;

import dto.ContactsDto;
import dto.ErrorMessageDto;
import dto.TokenDto;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;

public class GetAllUserContacts implements
        BaseApi, ILogin {
    TokenDto tokenDto;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login() {
        tokenDto = loginGetToken();
    }

    @Test
    public void getAllUserContactsPositiveTest() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_URL)
                .addHeader(AUTH, tokenDto.getToken())
                .get()
                .build();
        try (Response response = OK_HTTP_CLIENT
                .newCall(request).execute()) {
            ContactsDto contactsDto = GSON.fromJson
                    (response.body().string(), ContactsDto.class);
            Assert.assertEquals(response.code(), 200);
            System.out.println(contactsDto.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("created exception");
        }
    }

    @Test
    public void getAllUserContactsWrongTokenNegativeTest() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_URL)
                .addHeader(AUTH, "tokenDto.getToken()")
                .get()
                .build();
        try (Response response = OK_HTTP_CLIENT
                .newCall(request).execute()) {
            System.out.println(response);
            ContactsDto contactsDto = GSON.fromJson
                    (response.body().string(), ContactsDto.class);
            softAssert.assertEquals(response.code(),
                    401, "validate status code");
            softAssert.assertTrue(response.message()
                    .contains("Unauthorized"),
                    "validate message");
            ErrorMessageDto errorMessageDto = GSON.fromJson
                    (response.body().string(), ErrorMessageDto.class);
           // System.out.println(errorMessageDto);
            softAssert.assertAll();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("created exception");
        }
    }
}
