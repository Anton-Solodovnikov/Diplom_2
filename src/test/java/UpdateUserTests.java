import Json.UserRequest;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class UpdateUserTests {
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
    @DisplayName("Успешное изменение данных пользователя")
    public void updateUser(){
        String newEmail = "new" + email;
        String newPassword = "new" + password;
        String newName = "new" + name;
        createBody.setEmail(newEmail);
        createBody.setPassword(newPassword);
        createBody.setName(newName);
        steps.loginUser(email, password);
        String token = steps.getToken();
        steps.updateUser(createBody,token);
        steps.checkStatusCode(200);
        steps.checkResponseBody("success",true);
        steps.checkResponseBody("user.email", newEmail.toLowerCase());
        steps.checkResponseBody("user.name", newName);
    }
    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void updateUserWithOutAuth(){
        String newEmail = "new" + email;
        String newPassword = "new" + password;
        String newName = "new" + name;
        UserRequest updateBody = new UserRequest(newEmail, newPassword, newName);
        steps.updateUser(updateBody,"");
        steps.checkStatusCode(401);
        steps.checkResponseBody("success",false);
        steps.checkResponseBody("message", "You should be authorised");
    }
}
