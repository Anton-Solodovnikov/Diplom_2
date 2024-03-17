import Json.UserRequest;
import io.restassured.response.Response;

public class AuthApi extends BaseHttpClient {
    private final String apiPath = "/api/auth/";

    public Response register (UserRequest userRequest) {
        String secondPartOfApiPath = apiPath + "register";
        return doPostRequest(secondPartOfApiPath, userRequest);
    }
    public Response login (UserRequest userRequest) {
        String secondPartOfApiPath = apiPath + "login";
        return doPostRequest(secondPartOfApiPath, userRequest);
    }
    public Response logout (UserRequest userRequest) {
        String secondPartOfApiPath = apiPath + "logout";
        return doPostRequest(secondPartOfApiPath, userRequest);
    }
    public Response refreshToken (UserRequest userRequest) {
        String secondPartOfApiPath = apiPath + "token";
        return doPostRequest(secondPartOfApiPath, userRequest);
    }

}
