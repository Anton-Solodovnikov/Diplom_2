import Json.Ingredients.IngredientsResponse;
import Json.Ingredients.IngredientsData;
import Json.OrderRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderSteps {
    private IngredientsApi ingredientsApi = new IngredientsApi();
    private OrderApi orderApi = new OrderApi();
    private ValidatableResponse response;

    @Step("Создание заказа")
    public void createOrder(OrderRequest orderRequest, String token) {
        response = orderApi.createOrder(orderRequest, token).then();
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
    @Step("Получение заказа конкретного пользователя")
    public void getUserOrder(String token){
        response = orderApi.getUserOrder(token).then();
    }
    @Step("Получить ингредиенты")
    public Response getIngredients() {
        return ingredientsApi.getIngredients();
    }
}
