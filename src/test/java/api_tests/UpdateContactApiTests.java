package api_tests;

import dto.Contact;
import dto.ResponseMessageDto;
import dto.TokenDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;

import static utils.ContactFactory.positiveContact;

public class UpdateContactApiTests implements BaseApi, ILogin {
    TokenDto tokenDto;
    String idContact;

    @BeforeClass
    public void login(){
        tokenDto = loginGetToken();
    }

    @BeforeMethod
    public void createContact(){
            Contact contact = positiveContact();
            RequestBody requestBody = RequestBody.create
                    (GSON.toJson(contact), JSON);
            Request request = new Request.Builder()
                    .url(BASE_URL + ADD_NEW_CONTACT_URL)
                    .addHeader(AUTH, tokenDto.getToken())
                    .post(requestBody)
                    .build();
            try {
                Response response = OK_HTTP_CLIENT
                        .newCall(request).execute();
                if(response.code() == 200){
                ResponseMessageDto responseMessageDto = GSON.fromJson
                        (response.body().string(),
                                ResponseMessageDto.class);
                System.out.println(responseMessageDto);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("created exception");
            }
        }
    }

