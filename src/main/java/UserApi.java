import Json.UserRequest;
import io.restassured.response.Response;

public class UserApi extends BaseHttpClient {
    private final String apiPath = "/api/auth/user";

    public Response getUser (String token) {
        return doGetRequest(apiPath, token);
    }
    public Response patchUser (UserRequest userRequest, String token) {
        return doPatchRequest(apiPath, userRequest, token);
    }
    public Response deleteUser (String token) {
        return doDeleteRequest(apiPath, token);
    }
}
