package sample.api.regres;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class regresWithTestNG {

    @BeforeClass
    public void setUpClass() {

        RestAssured.baseURI = "https://reqres.in";
    }
/*
{
    "data": {
        "id": 2,
        "email": "janet.weaver@reqres.in",
        "first_name": "Janet",
        "last_name": "Weaver",
        "avatar": "https://reqres.in/img/faces/2-image.jpg"
    },
    "support": {
        "url": "https://reqres.in/#support-heading",
        "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
    }
}
 */


    @Test
    public void singleUserWithParamTest() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 2)
                .when()
                .get("/api/users/{id}");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        assertTrue(response.body().asString().contains("Janet"));
        response.prettyPrint();


    }

    @Test
    public void negativeTest() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 500)
                .when()
                .get("/api/users/{id}");
        assertEquals(response.statusCode(), 404);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        response.prettyPrint();
    }

    @Test
    public void QueryParam() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("first_nameContains", "L")
                .when().get("/api/users?page=2");
        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
        response.prettyPrint();

    }

    @Test
    public void PathMethod() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 2)
                .when()
                .get("/api/users/{id}");

        assertEquals(response.statusCode(), 200);
        assertEquals(response.contentType(), "application/json; charset=utf-8");
//response.prettyPrint();
        int id = response.body().path("data.id");
        String email = response.body().path("data.email");
        String firtname = response.body().path("data.first_name");
        String lastname = response.body().path("data.last_name");
        String avatar = response.body().path("data.avatar");
        assertEquals(id, 2);
        assertEquals(email, "janet.weaver@reqres.in");
        assertEquals(firtname, "Janet");
        assertEquals(lastname, "Weaver");
        assertEquals(avatar, "https://reqres.in/img/faces/2-image.jpg");
    }

    @Test
    public void PathMethodMultipleJsonAndUsingList() {

        Response response = given().get("/api/users?page=2");
        response.prettyPrint();
        int id = response.body().path("data[2].id");
        String email = response.body().path("data[2].email");
        String firtname = response.body().path("data[2].first_name");
        String lastname = response.body().path("data[2].last_name");
        String avatar = response.body().path("data[2].avatar");
        System.out.println(id + email + firtname + lastname + avatar);
        assertEquals(id, 9);
        assertEquals(email, "tobias.funke@reqres.in");
        assertEquals(firtname, "Tobias");
        assertEquals(lastname, "Funke");
        assertEquals(avatar, "https://reqres.in/img/faces/9-image.jpg");

        List<String> ids = response.path("data.id");
        List<String> firtnames = response.path("data.first_name");

        System.out.println(ids);
        System.out.println(firtnames);


    }


    @Test
    public void TestWithJsonPath() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 2)
                .when()
                .get("/api/users/{id}");

        JsonPath jsonPath=response.jsonPath();
        int id = jsonPath.getInt("data.id");
        String email = jsonPath.getString("data.email");
        String firtname = jsonPath.getString("data.first_name");
        String lastname = jsonPath.getString("data.last_name");
        String avatar = jsonPath.getString("data.avatar");
   //     System.out.println(id + email + firtname + lastname + avatar);
        assertEquals(id, 2);
        assertEquals(email, "janet.weaver@reqres.in");
        assertEquals(firtname, "Janet");
        assertEquals(lastname, "Weaver");
        assertEquals(avatar, "https://reqres.in/img/faces/2-image.jpg");



    }
    @Test
    public void TestWithUsingMap() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 2)
                .when()
                .get("/api/users/{id}");
        Map<String,Object> userMap= response.body().as(Map.class);
        System.out.println("userMap = " + userMap);
        response.prettyPrint();
        System.out.println("userMap.get(\"data\").toString() = " + userMap.get("data").toString());


    }
}