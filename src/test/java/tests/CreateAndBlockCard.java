package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class CreateAndBlockCard extends BaseTest{
    private final HashMap<String, String> getValue = new HashMap<>();

    @Test(priority = 1)
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

        getValue.put("space_id", response.jsonPath().getString("id"));
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 2)
    @Description("Создание первой доски")
    public void createBoard() {

        File testBody = new File("src/test/resources/jsonFiles/createBoard.json");
        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(testBody)
                .when()
                .post(constant.SPACES.concat(getValue.get("space_id").concat(constant.BOARDS)))
                .then()
                .log()
                .all()
                .extract().response();

        getValue.put("board_id", response.jsonPath().getString("id"));
        Assert.assertEquals(response.statusCode(), 200);
    }


    @Test(priority = 3)
    @Description("Создание карты")
    @Link("https://joyous-statistic-1d5.notion.site/Rest-Assured-Kaiten-9065dda6854e465d978f118cca4469ae#:~:text = \" +\n" +
            "            \"Написать минимум 5 end-to-end тест-кейсов в цепочке должно быть хотя бы 2 запроса и запрос " +
            "на удаление+всегда удаляем то что создали")
    public void createCard() {

        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"title\": \"1\",\n" +
                        "  \"board_id\": \"" + getValue.get("board_id") + "\"\n" +
                        "}")
                .when()
                .post(constant.CARDS)
                .then()
                .log()
                .all()
                .extract().response();

        getValue.put("card_id", response.jsonPath().getString("id"));
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 4)
    @Description("Задание со звездочкой - прикрепить файл к карте")
    @Link("https://joyous-statistic-1d5.notion.site/Rest-Assured-Kaiten-9065dda6854e465d978f118cca4469ae#:~:text" +
            " = Автоматизировать метод Attach file to card")
    public void attachFileToCard() {
        //https://rmznkubanov.kaiten.ru/api/latest/cards/{card_id}/files
        File fileToAttach = new File("C:\\Users\\user\\scoop\\apps\\allure\\2.32.2\\plugins\\behaviors-plugin\\static\\index.js");
        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.MULTIPART)
                .multiPart(fileToAttach)
                .when()
                .post(constant.CARDS.concat(getValue.get("card_id").concat("/files")))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }


    @Test(priority = 5)
    @Description("Получить список карт")
    public void getCards() {
        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .get(constant.CARDS)
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 6)
    @Description("Удаление карты")
    public void deleteCard() {
        String path = baseURI.concat(constant.CARDS.concat(getValue.get("card_id")));
        System.out.println(path);
        //https://rmznkubanov.kaiten.ru/api/latest/cards/{card_id}
        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .delete(constant.CARDS.concat(getValue.get("card_id")))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }
}
