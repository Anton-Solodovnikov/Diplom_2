import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseHttpClient {

    private RequestSpecification baseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://stellarburgers.nomoreparties.site")
                .addHeader("Content-Type","application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }
    private RequestSpecification baseRequestSpec(String token) {
        return new RequestSpecBuilder()
                .setBaseUri("https://stellarburgers.nomoreparties.site")
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization", token)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }

    protected Response doGetRequest(String path) {
        return given()
                .spec(baseRequestSpec())
                .get(path)
                .thenReturn();
    }

    protected Response doGetRequest(String path, String token) {
        return given()
                .spec(baseRequestSpec(token))
                .get(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, Object body) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .post(path)
                .thenReturn();
    }

    protected Response doDeleteRequest(String path, String token) {
        return given()
                .spec(baseRequestSpec(token))
                .delete(path)
                .thenReturn();
    }

    protected Response doPutRequest(String path, Object body) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .put(path)
                .thenReturn();
    }

    protected Response doPatchRequest(String path, Object body, String token) {
        return given()
                .spec(baseRequestSpec(token))
                .body(body)
                .patch(path)
                .thenReturn();
    }
}
