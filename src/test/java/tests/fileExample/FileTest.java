package tests.fileExample;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

import java.io.File;

import static io.restassured.RestAssured.given;

public class FileTest extends BaseTest {
    @Test
    public void createNewSpace() {
        File json = new File("src/test/resources/jsonFiles/createBoard.json");
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(json)
                .when()
                .post(constant.SPACES)
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "Test Json File Fields");
        Assert.assertFalse(response.jsonPath().getBoolean("archived"));
        Assert.assertEquals(response.jsonPath().getString("external_id"), "5");
    }
}
