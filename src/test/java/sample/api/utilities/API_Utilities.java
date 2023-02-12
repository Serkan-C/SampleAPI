package sample.api.utilities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import sample.api.pojo.User;

import static io.restassured.RestAssured.given;
import static sample.api.utilities.RegresTestBase.responseSpec;

public class API_Utilities {

    public static void PostLoginMethod(String email, String password) {
        User userBody = new User();
        userBody.setEmail(email);
        userBody.setPassword(password);

        Response response = given().contentType(ContentType.JSON)
                .body(userBody)
                .when()
                .post("/api/login")
                .then()
                .spec(responseSpec)
                .extract().response();
        String tokenForUser = response.path("token");
        System.out.println(tokenForUser);


    }
    public static void PostCreateMethod(String name,String job) {
        User userBodyCreate = new User();
        userBodyCreate.setName(name);
        userBodyCreate.setJob(job);

        Response response = given().contentType(ContentType.JSON)
                .body(userBodyCreate)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract().response();
        String createdID = response.path("id");
        System.out.println("User with ID number: " + createdID + "  is  created");

    }
    public static Response GetSingleUser(int id) {
        Response response=given().accept(ContentType.JSON)
                .and().pathParam("id", id)
                .when()
                .get("/api/users/{id}");
        return response;
    }
}
