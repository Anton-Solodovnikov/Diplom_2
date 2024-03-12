import Json.UserRequest;
import Json.UserResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.equalTo;

public class UserSteps {
    private AuthApi authApi = new AuthApi();
    private UserApi userApi = new UserApi();
    private ValidatableResponse response;
    @Step("Регистрация пользователя")
    public void registerUser(UserRequest userRequest) {
        response = authApi.register(userRequest).then();
    }
    @Step("Проверка кода ответа")
    public void checkStatusCode(int expectedCode) {
        response.statusCode(equalTo(expectedCode));
    }
    @Step("Проверка тела ответа")
    public void checkResponseBody(String jsonField, Object expectedMessage) {
        response.assertThat()
                .body(jsonField,equalTo(expectedMessage));
    }
    @Step("Удаление пользователя")
    public void deleteUser(String token, UserRequest userRequest) {
        Response deleteResponse = authApi.login(userRequest);
        userApi.deleteUser(deleteResponse.body().as(UserResponse.class).getAccessToken());
    }
    @Step("Взять токен из ответа")
    public String getToken() {
        String token = response.extract().as(UserResponse.class).getAccessToken().split(" ")[1];
        return token;
    }
}
