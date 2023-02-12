package sample.api.regres;
import sample.api.utilities.API_Utilities;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.api.utilities.ConfigurationReader;
import sample.api.utilities.RegresTestBase;
import sample.api.pojo.ListUser;
import sample.api.pojo.User;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class RestAssuredTestWithJunit extends RegresTestBase {
    @DisplayName("Get Single User with Hamcrest Matchers")
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

    @DisplayName("Get Single User with pathParam and with Hamcrest Matchers ")
    @Test
    public void singleUserWithParamTest() {
        given().accept(ContentType.JSON)
                .and().pathParam("id", 2)
                .when()
                .get("/api/users/{id}")
                .then().statusCode(200)
                .body("data.id", is(2)
                        , "data.email", equalTo("janet.weaver@reqres.in")
                        , "data.first_name", is("Janet")
                        , "data.last_name", is("Weaver"));


    }

    @DisplayName("Get Single User with API_Utilities Method and with Hamcrest Matchers ")
    @Test
    public void GetwithAPIMethodTest() {
     Response response=API_Utilities.GetSingleUser(2);

                response.then().statusCode(200)
                .body("data.id", is(2)
                        , "data.email", equalTo("janet.weaver@reqres.in")
                        , "data.first_name", is("Janet")
                        , "data.last_name", is("Weaver"));


    }

    String createdID; // newly created ID to use it later. ex: for delete

    @DisplayName("Post Create using POJO")
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
    @DisplayName("Post Create using API method")
    @Test
    public void PostCreateWithAPIMethod() {
        API_Utilities.PostCreateMethod("serkan","SDET");


    }


        @DisplayName("Delete recently created user")
    @Test
    public void DeleteUser() {

        when().delete("/api/users/" + createdID)
                .then().statusCode(204);
        System.out.println("user with " + createdID + " is deleted");


    }

    @DisplayName("Post Register using simply String")
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
    @DisplayName("Post Register using Configuration.Properties")
    @Test
    public void PostRegisterWithConfig() {

        String userBody = "{\n" +
                "    \"email\": \""+ConfigurationReader.getProperty("email")+"\",\n" +
                "    \"password\": \""+ConfigurationReader.getProperty("email")+"\"\n" +
                "}";

        given().contentType(ContentType.JSON)
                .body(userBody)
                .when()
                .post("/api/register")
                .then()
                .spec(responseSpec);
    }

        String tokenForUser; // save it to use it maybe later

        @DisplayName("Post Login using POJO get token and save it to use it later")
        @Test
        public void PostLogin () {
            User userBody = new User();
            userBody.setEmail("eve.holt@reqres.in");
            userBody.setPassword("cityslicka");


            Response response = given().contentType(ContentType.JSON)
                    .body(userBody)
                    .when()
                    .post("/api/login")
                    .then()
                    .spec(responseSpec)
                    .extract().response();
            tokenForUser = response.path("token");

        }
    @DisplayName("Post Login using API_Utilities get token and save it to use it later")
    @Test
    public void PostLoginWithAPI_Method () {

            API_Utilities.PostLoginMethod("eve.holt@reqres.in","cityslicka");

    }

        @DisplayName(" List All User ")
        @Test
        public void GetListUser () {

            Response response = given().accept(ContentType.JSON)
                    .when().get("/api/users?page=2");
            response.prettyPrint();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(jsonPath);

        }

}