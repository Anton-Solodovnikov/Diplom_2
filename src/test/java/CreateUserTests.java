import Json.UserRequest;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

public class CreateUserTests {
    private UserSteps steps = new UserSteps();
    private Faker faker = new Faker();
    private String email = faker.random().hex(15) + "@praktikum.ru";
    private String password = faker.random().hex(15);
    private String name = faker.name().firstName();
    private UserRequest createBody = new UserRequest(email, password, name);
    private ArrayList<String> tokens = new ArrayList<>();

    @After
    public void deleteUser() {
        if(tokens.isEmpty())
            return;
        for (String token: tokens) {
            steps.deleteUser(token, createBody);
        }
    }
    @Test
    @DisplayName("Успешное создание нового пользователя")
    public void createUserTest() {
        steps.registerUser(createBody);
        steps.checkStatusCode(200);
        steps.checkResponseBody("success", true);
        steps.checkResponseBody("user.email", email.toLowerCase());
        steps.checkResponseBody("user.name", name);
        steps.checkResponseBody("accessToken");
        steps.checkResponseBody("refreshToken");
        tokens.add(steps.getToken());
    }
    @Test
    @DisplayName("Создание пользователя, который уже существует")
    public void createExistUserTest() {
        steps.registerUser(createBody);
        steps.checkStatusCode(200);
        steps.registerUser(createBody);
        steps.checkStatusCode(403);
        steps.checkResponseBody("success", false);
        steps.checkResponseBody("message", "User already exists");
    }
    @Test
    @DisplayName("Создание пользователя без всех обязательных полей")
    public void createUserWithNoRequiredFields() {
        steps.registerUser(new UserRequest("","",""));
        steps.checkStatusCode(403);
        steps.checkResponseBody("success", false);
        steps.checkResponseBody("message", "Email, password and name are required fields");
    }
    @Test
    @DisplayName("Создание пользователя без email")
    public void createUserWithNoEmailField() {
        steps.registerUser(new UserRequest("",password,name));
        steps.checkStatusCode(403);
        steps.checkResponseBody("success", false);
        steps.checkResponseBody("message", "Email, password and name are required fields");
    }
    @Test
    @DisplayName("Создание пользователя без password")
    public void createUserWithNoPasswordField() {
        steps.registerUser(new UserRequest(email,"",name));
        steps.checkStatusCode(403);
        steps.checkResponseBody("success", false);
        steps.checkResponseBody("message", "Email, password and name are required fields");
    }
    @Test
    @DisplayName("Создание пользователя без name")
    public void createUserWithNoNameField() {
        steps.registerUser(new UserRequest(email,password,""));
        steps.checkStatusCode(403);
        steps.checkResponseBody("success", false);
        steps.checkResponseBody("message", "Email, password and name are required fields");
    }

}
