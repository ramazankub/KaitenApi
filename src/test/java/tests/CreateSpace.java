package tests;

import io.qameta.allure.Link;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class CreateSpace extends BaseTest {

    private final List<String> getValue = new ArrayList<>();

    @Test(priority = 1)
    @Description("Создание нового пространства")
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

        getValue.add(response.jsonPath().getString("id"));
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("title"), "test");
        Assert.assertFalse(response.jsonPath().getBoolean("archived"));
        Assert.assertEquals(response.jsonPath().getString("external_id"), "1");
    }

    @Test(priority = 2)
    @Description("Обновление пространства")
    public void updateSpace() {
        String URI = baseURI.concat(constant.SPACES).concat(getValue.getFirst());
        System.out.println(URI);
        //https://rmznkubanov.kaiten.ru/api/latest/spaces/{space_id}
        File testBody = new File("src/test/resources/jsonFiles/updateSpace.json");
        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .body(testBody)
                .when()
                .patch(constant.SPACES.concat(getValue.getFirst()))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 3)
    @Description("Создание новых пространств")
    public void createNewSpaces() {
        for (int i = 1; i < 3; i++) {
            createNewSpace();
        }
    }

    @Test(priority = 4)
    @Description("Лист пространств")
    public void retrieveSpacesList() {
        Response response = given()
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .when()
                .get(constant.SPACES)
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 5)
    @Description("Удаление пространств")
    public void deleteSpace() {
        Iterator<String> iterator = getValue.iterator();
        while (iterator.hasNext()) {
            String id = iterator.next();

                Response response = given()
                        .header("Authorization", token)
                        .contentType(ContentType.JSON)
                        .when()
                        .delete(constant.SPACES.concat(id))
                        .then()
                        .log()
                        .all()
                        .extract().response();

                Assert.assertEquals(response.statusCode(), 200);

           iterator.remove();
        }
    }
}


