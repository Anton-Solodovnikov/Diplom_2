import Json.UserRequest;
import Json.UserResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

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
    @Step("Проверка тела ответа")
    public void checkResponseBody(String jsonField) {
        response.assertThat()
                .body(jsonField,notNullValue());
    }
    @Step("Удаление пользователя")
    public void deleteUser(String token, UserRequest userRequest) {
        Response deleteResponse = authApi.login(userRequest);
        userApi.deleteUser(deleteResponse.body().as(UserResponse.class).getAccessToken().split(" ")[1]);
    }
    @Step("Взять токен из ответа")
    public String getToken() {
        String token = response.extract().as(UserResponse.class).getAccessToken().split(" ")[1];
        return token;
    }
    @Step("Логин пользователя")
    public void loginUser(String email, String password) {
       response = authApi.login(new UserRequest(email, password)).then();
    }
    @Step("Изменений данных пользователя")
    public void updateUser(UserRequest userRequest, String token){
        response = userApi.patchUser(userRequest,token).then();
    }
    @Step("Получить данные пользователя")
    public void getUser(String token) {
        response = userApi.getUser(token).then();
    }

}
