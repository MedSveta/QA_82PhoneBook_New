package utils;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public interface BaseApi {
    String BASE_URL = "https://contactapp-telran-backend.herokuapp.com";
    String REGISTRATION_URL = "/v1/user/registration/usernamepassword";
    String LOGIN_URL = "/v1/user/login/usernamepassword";
    String ADD_NEW_CONTACT_URL = "/v1/contacts";
    String GET_ALL_CONTACTS_URL = "/v1/contacts";
    String UPDATE_CONTACT_URL = "/v1/contacts";
    String DELETE_CONTACT_URL = "/v1/contacts/";

    MediaType JSON = MediaType.get("application/json");
    MediaType TEXT = MediaType.get("text/plain");
    OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    String AUTH = "Authorization";

    Gson GSON = new Gson();

}
