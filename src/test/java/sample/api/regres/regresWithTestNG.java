package sample.api.regres;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class regresWithTestNG {

@BeforeClass
public void setUpClass(){

    RestAssured.baseURI="https://reqres.in";
}



    @DisplayName("Get Single User with pathParam")
    @Test
    public void singleUserWithParamTest() {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id",2)
                .when()
                .get("/api/users/{id}");

        assertEquals( response.statusCode(),200);


    }
}
