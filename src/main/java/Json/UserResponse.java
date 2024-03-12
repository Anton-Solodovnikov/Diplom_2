package Json;

public class UserResponse {
    private String success;
    private UserRequest userRequest;
    private String accessToken;
    private String refreshToken;
    private String message;

    public UserResponse(String success, UserRequest userRequest, String accessToken, String refreshToken) {
        this.success = success;
        this.userRequest = userRequest;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
