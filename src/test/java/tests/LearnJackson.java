package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Link;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.fileExample.pojo.forBoard.BoardExample;
import tests.fileExample.pojo.forBoard.Column;
import tests.fileExample.pojo.forBoard.Lane;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class LearnJackson
        extends BaseTest{
    private static final ObjectMapper mapper = new ObjectMapper();
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
    public void createFirstBoard() throws JsonProcessingException {

        Column column1 = new Column("To do", 1);
        Column column2 = new Column("In progress", 2);
        Column column3 = new Column("Done", 3);

        List<Column> columns = Arrays.asList(column1, column2, column3);

        Lane lane = new Lane("Test", 1);
        List<Lane> lanes = List.of(lane);

        BoardExample boardExample = new BoardExample("Test board", columns, lanes, 1, 1);

        String jsonBody = mapper.writeValueAsString(boardExample);

        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(jsonBody)
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

    public void createSecondBoard() throws JsonProcessingException {

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
