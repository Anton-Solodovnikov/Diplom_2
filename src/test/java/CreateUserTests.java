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
        tokens.add(steps.getToken());
    }
}
