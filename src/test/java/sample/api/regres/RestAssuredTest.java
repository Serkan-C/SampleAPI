package sample.api.regres;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.api.Utilities.ConfigurationReader;
import sample.api.Utilities.RegresTestBase;
import sample.api.pojo.User;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class RestAssuredTest extends RegresTestBase {
    @DisplayName("Get Single User")
    @Test
    public void singleUserTest() {

        when()
                .get("/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2)
                        , "data.email", equalTo("janet.weaver@reqres.in")
                        , "data.first_name", is("Janet")
                        , "data.last_name", is("Weaver"));


    }

    String createdID; // newly created ID to use it later. ex: for delete

    @DisplayName("Post Create ")
    @Test
    public void PostCreate() {
        User userBodyCreate = new User();
        userBodyCreate.setName("Serkan");
        userBodyCreate.setJob("SDET");

        Response response = given().contentType(ContentType.JSON)
                .body(userBodyCreate)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract().response();
        createdID = response.path("id");
        System.out.println("User with" + createdID + "created");


    }

    @DisplayName("Delete recently created user")
    @Test
    public void DeleteUser() {

        when().delete("/api/users/" + createdID)
                .then().statusCode(204);
        System.out.println("user with " + createdID + " is deleted");


    }

    @DisplayName("Post Register ")
    @Test
    public void PostRegister() {
        String userBody = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        given().contentType(ContentType.JSON)
                .body(userBody)
                .when()
                .post("/api/register")
                .then()
                .spec(responseSpec);

    }

    String tokenForUser; // save it to use it maybe later
    @DisplayName("Post Login using POJO")
    @Test
    public void PostLogin() {
        User userBody = new User();
        userBody.setEmail("eve.holt@reqres.in");
        userBody.setPassword("cityslicka");


       Response response= given().contentType(ContentType.JSON)
                .body(userBody)
                .when()
                .post("/api/login")
                .then()
                .spec(responseSpec)
               .extract().response();
       tokenForUser=response.path("token");

    }

}
