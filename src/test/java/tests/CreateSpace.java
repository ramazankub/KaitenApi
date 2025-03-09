package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateSpace extends BaseTest {

    @Test
    public void createNewSpace() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        " \"title\": \"rest-assured\",\n" +
                        " \"external_id\": 1\n" +
                        "}")
                .when()
                .post(constant.SPACES)
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "rest-assured");
        Assert.assertFalse(response.jsonPath().getBoolean("archived"));
        Assert.assertEquals(response.jsonPath().getString("external_id"), "1");
    }
}
