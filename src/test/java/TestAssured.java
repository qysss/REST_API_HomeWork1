import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;



public class TestAssured {

    @Test
    @Tags({@Tag("GetRequest"), @Tag("Positive")})
    void checkGetUsers() {
        given()
                .baseUri("https://reqres.in/")
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("data.size()", equalTo(6))
                .body("support.text", instanceOf(String.class))
                .body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));

    }

    @Test
    @Tags({@Tag("GetRequest"), @Tag("Negative")})
    void checkGet404() {
        given()
                .baseUri("https://reqres.in/")
                .when()
                .get("/api/unknown/23")
                .then()
                .statusCode(404)
                .body(is("{}"));
    }


    @Test
    @Tags({@Tag("PostRequest"), @Tag("Positive")})
    void checkPostRegister() {
        given()
                .baseUri("https://reqres.in/")
                .contentType(JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }")
                .when()
                .post("/api/register")
                .then()
                .statusCode(200)
                .body("id", instanceOf(Integer.class))
                .body("id", equalTo(4))
                .body("token", instanceOf(String.class))
                .body("token", notNullValue());

    }

    @Test
    @Tags({@Tag("PostRequest"), @Tag("Positive")})
    void checkPostRegis1ter() {
        given()
                .baseUri("https://reqres.in/")
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"leader\" }")
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"));

    }

    @Test
    @Tags({@Tag("DeleteRequest"), @Tag("Positive")})
    void checkDeleteUsers() {
        given()
                .baseUri("https://reqres.in/")
                .when()
                .delete("/api/users")
                .then()
                .statusCode(204);
    }


}

