import Json.UserRequest;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class UserLoginTests {
    private UserSteps steps = new UserSteps();
    private Faker faker = new Faker();
    private String email = faker.random().hex(15) + "@praktikum.ru";
    private String password = faker.random().hex(15);
    private String name = faker.name().firstName();
    private UserRequest createBody = new UserRequest(email, password, name);
    private ArrayList<String> tokens = new ArrayList<>();

    @Before
    public void createUser() {
        steps.registerUser(createBody);
        steps.checkStatusCode(200);
        tokens.add(steps.getToken());
    }
    @After
    public void deleteUser() {
        if(tokens.isEmpty())
            return;
        for (String token: tokens) {
            steps.deleteUser(token, createBody);
        }
    }
    @Test
    @DisplayName("Успешный логин пользователя")
    public void successLoginTest() {
        steps.loginUser(email,password);
        steps.checkStatusCode(200);
        steps.checkResponseBody("success",true);
        steps.checkResponseBody("accessToken");
        steps.checkResponseBody("refreshToken");
        steps.checkResponseBody("user.email",email.toLowerCase());
        steps.checkResponseBody("user.name",name);
    }
    @Test
    @DisplayName("Логин без обязательных полей")
    public void loginWithNoRequiredFields() {
        steps.loginUser("","");
        steps.checkStatusCode(401);
        steps.checkResponseBody("success", false);
        steps.checkResponseBody("message","email or password are incorrect");
    }
    @Test
    @DisplayName("Логин без email")
    public void loginWithNoEmailField() {
        steps.loginUser("",password);
        steps.checkStatusCode(401);
        steps.checkResponseBody("success", false);
        steps.checkResponseBody("message","email or password are incorrect");
    }
    @Test
    @DisplayName("Логин без password")
    public void loginWithNoPasswordEmailField() {
        steps.loginUser(email,"");
        steps.checkStatusCode(401);
        steps.checkResponseBody("success", false);
        steps.checkResponseBody("message","email or password are incorrect");
    }

}
