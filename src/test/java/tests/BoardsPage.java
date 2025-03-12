package tests;

import io.qameta.allure.Link;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class BoardsPage extends BaseTest{
    private final HashMap<String, String> getValue = new HashMap<>();

    @Test(priority = 1)
    @Description("Создание пространства")
    @Link("https://joyous-statistic-1d5.notion.site/Rest-Assured-Kaiten-9065dda6854e465d978f118cca4469ae#:~:text = " +
            "Написать минимум 5 end-to-end тест-кейсов в цепочке должно быть хотя бы 2 запроса и запрос на удаление, " +
            "всегда удаляем то что создали")
    public void createNewSpace() {

        File testBody = new File("src/test/resources/jsonFiles/createSpace.json");
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(testBody)
                .when()
                .post(constant.SPACES)
                .then()
                .log()
                .all()
                .extract().response();

        getValue.put("id", response.jsonPath().getString("id"));
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 2)
    @Description("Создание первой доски")
    public void createFirstBoard() {

        File testBody = new File("src/test/resources/jsonFiles/createBoard.json");
        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(testBody)
                .when()
                .post(constant.SPACES.concat(getValue.get("id").concat(constant.BOARDS)))
                .then()
                .log()
                .all()
                .extract().response();

        getValue.put("board_id", response.jsonPath().getString("id"));
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 3)
    @Description("Создание второй доски")

    public void createSecondBoard() {

        for (int i = 0; i < 1; i++) {
            createFirstBoard();
        }
    }

    @Test(priority = 4)
    @Description("Просмотр обновленной доски")
    public void getUpdatedBoard() {

        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .get(constant.BOARDS.concat(getValue.get("board_id")))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 5)
    @Description("Удаление пространства с его содержимым")
    public void deleteTestSpace() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .delete(constant.SPACES.concat(getValue.get("id")))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }
}
