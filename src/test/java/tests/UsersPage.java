package tests;

import io.qameta.allure.Link;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class UsersPage extends BaseTest{
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
    @Description("Приглашение пользователя")
    public void inviteUserToSpace() {

        File testBody = new File("src/test/resources/jsonFiles/inviteUsers.json");
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(testBody)
                .when()
                .post(constant.SPACES.concat(getValue.get("id").concat(constant.USERS)))
                .then()
                .log()
                .all()
                .extract().response();
        getValue.put("user_id", response.jsonPath().getString("user.id"));
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 3)
    @Description("Показ пользователя")
    public void getUser() {

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get(constant.SPACES.concat(getValue.get("id").concat(constant.USERS).concat(getValue.get("user_id"))))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 4)
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
