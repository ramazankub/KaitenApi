package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class CreateSpaceBoardCard extends BaseTest {
    private final HashMap<String, String> dataMap = new HashMap<>();


    @Test(priority = 1)
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

        dataMap.put("id", response.jsonPath().getString("id"));
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "rest-assured");
        Assert.assertFalse(response.jsonPath().getBoolean("archived"));
        Assert.assertEquals(response.jsonPath().getString("external_id"), "1");
    }

    @Test(priority = 2)
    public void createBoard() {

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        " \"title\": \"Card\",\n" +
                        //" \"board_id\": {{boardId}}\n" +
                        "}")
                .when()
                //spaces/{space_id}/boards
                .post(constant.SPACES.concat(dataMap.get("id")).concat(constant.BOARDS))
                .then()
                .log()
                .all()
                .extract().response();
        dataMap.put("column_id", response.jsonPath().getString("column_id"));
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 3)
    public void createCard() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        " \"title\": 1,\n" +
                        " \"board_id\": " + dataMap.get("id") +
                        " \"asap\": false,\n" +
                        " \"due_date\": \"2005-08-09\",\n" +
                        " \"due_date_time_present\": false,\n" +
                        " \"sort_order\": 1,\n" +
                        " \"description\": 1,\n" +
                        " \"expires_later\": false,\n" +
                        " \"size_text\": 1,\n" +
                        " \"column_id\": " + dataMap.get("column_id") +
                        " \"lane_id\": 1,\n" +
                        " \"owner_id\": 1,\n" +
                        " \"responsible_id\": 1,\n" +
                        " \"owner_email\": \"test\",\n" +
                        " \"position\": 1,\n" +
                        " \"type_id\": 1,\n" +
                        " \"external_id\": 1,\n" +
                        " \"text_format_type_id\": 1,\n" +
                        " \"properties\": {}\n" +
                        "}")
                .when()
                .post(constant.CARDS)
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }
}
