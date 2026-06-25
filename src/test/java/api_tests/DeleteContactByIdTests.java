package api_tests;

import dto.ContactsDto;
import dto.TokenDto;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;

public class DeleteContactByIdTests implements
        BaseApi, ILogin {
    TokenDto tokenDto;
    String idContact;

    @BeforeClass
    public void login() {
        tokenDto = loginGetToken();
    }

    @BeforeMethod
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
            idContact = contactsDto.getContacts().get(0).getId();
            System.out.println(idContact);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("created exception");
        }
    }

    @Test
    public void deleteFirstContact() {
        Request request = new Request.Builder()
                .url(BASE_URL + DELETE_CONTACT_URL + idContact)
                .addHeader(AUTH, tokenDto.getToken())
                .delete()
                .build();
        try(Response response = OK_HTTP_CLIENT
                .newCall(request).execute()){
            Assert.assertEquals(response.code(), 200);
        }catch (IOException e){
            e.printStackTrace();
            Assert.fail("created exception");
        }
    }
}
