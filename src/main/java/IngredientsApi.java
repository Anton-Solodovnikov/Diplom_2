import io.restassured.response.Response;

public class IngredientsApi extends BaseHttpClient {
    private final String apiPath = "/api/ingredients";

    public Response getIngredients () {
        return doGetRequest(apiPath);
    }
}
