import Json.Ingredients.IngredientsData;
import Json.Ingredients.IngredientsResponse;
import Json.OrderRequest;
import Json.UserRequest;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OrdersTests {
    private UserSteps userSteps = new UserSteps();
    private OrderSteps orderSteps = new OrderSteps();
    private List<IngredientsData> ingredients = new ArrayList<>();
    private Faker faker = new Faker();
    private String email = faker.random().hex(15) + "@praktikum.ru";
    private String password = faker.random().hex(15);
    private String name = faker.name().firstName();
    private UserRequest createBody = new UserRequest(email, password, name);
    private ArrayList<String> tokens = new ArrayList<>();

    @Before
    public void createTestUser() {
        userSteps.registerUser(createBody);
        userSteps.checkStatusCode(200);
        tokens.add(userSteps.getToken());
        ingredients = orderSteps.getIngredients().body().as(IngredientsResponse.class).getDataList();
    }
    @After
    public void deleteTestUser() {
        if(tokens.isEmpty())
            return;
        for (String token: tokens) {
            userSteps.deleteUser(token, createBody);
        }
    }
    @Test
    @DisplayName("Успешное создание заказа")
    public void createOrderWithIngredientsTest(){
        String token = userSteps.getToken();
        String firstIngredient = ingredients.get(0).get_id();
        String secondIngredient = ingredients.get(ingredients.size() - 1).get_id();
        OrderRequest orderRequest = new OrderRequest(List.of(firstIngredient,secondIngredient));
        orderSteps.createOrder(orderRequest, token);
        orderSteps.checkStatusCode(200);
        orderSteps.checkResponseBody("success", true);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithOutIngredientsTest(){
        String token = userSteps.getToken();
        OrderRequest orderRequest = new OrderRequest(List.of());
        orderSteps.createOrder(orderRequest, token);
        orderSteps.checkStatusCode(400);
        orderSteps.checkResponseBody("success", false);
        orderSteps.checkResponseBody("message","Ingredient ids must be provided");
    }
    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithOutAuthTest(){
        String firstIngredient = ingredients.get(0).get_id();
        String secondIngredient = ingredients.get(ingredients.size() - 1).get_id();
        OrderRequest orderRequest = new OrderRequest(List.of(firstIngredient,secondIngredient));
        orderSteps.createOrder(orderRequest, "");
        orderSteps.checkStatusCode(200);
        orderSteps.checkResponseBody("success", true);
    }
    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithWrongIngredientsTest(){
        String token = userSteps.getToken();
        String firstIngredient = "Wrong" + ingredients.get(0).get_id();
        String secondIngredient = "Wrong" + ingredients.get(ingredients.size() - 1).get_id();
        OrderRequest orderRequest = new OrderRequest(List.of(firstIngredient,secondIngredient));
        orderSteps.createOrder(orderRequest, token);
        orderSteps.checkStatusCode(500);
    }
}
