import Json.OrderRequest;
import io.restassured.response.Response;

public class OrderApi extends BaseHttpClient {
    private final String apiPath = "/api/orders";

    public Response createOrder (OrderRequest orderRequest, String token) {
        return doPostRequest(apiPath, orderRequest, token);
    }
    public Response getUserOrder(String token) {
        return doGetRequest(apiPath, token);
    }
}
