package sample.api.Utilities;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class RegresTestBase {

    public static ResponseSpecification responseSpec;
    public static RequestSpecification userSpec;
    @BeforeAll
    public static void init() {
        baseURI = "https://reqres.in";


        userSpec =given()
                .accept(ContentType.JSON);

        responseSpec = expect().statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }
}
