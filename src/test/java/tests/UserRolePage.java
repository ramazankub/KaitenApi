package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class UserRolePage extends BaseTest {
    private final HashMap<String, String> getValue = new HashMap<>();

    //https://rmznkubanov.kaiten.ru/api/latest/user-roles
    @Test(priority = 1)
    @Description("Создание роли пользователя")
    @Link("https://joyous-statistic-1d5.notion.site/Rest-Assured-Kaiten-9065dda6854e465d978f118cca4469ae#:~:text = \" +\n" +
            "            \"Написать минимум 5 end-to-end тест-кейсов в цепочке должно быть хотя бы 2 запроса и запрос " +
            "на удаление+всегда удаляем то что создали")
    public void createUserRole() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        " \"name\": \"testRole12\"\n" +
                        "}")
                .when()
                .post(constant.USER_ROLES)
                .then()
                .log()
                .all()
                .extract().response();

        getValue.put("role_id", response.jsonPath().getString("id"));
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 2)
    @Description("Обновление роли пользователя")
    public void updateUserRole() {

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        " \"name\": \"updatedRoleName\"\n" +
                        "}")
                .when()

                .patch(constant.USER_ROLES.concat(getValue.get("role_id")))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 3)
    @Description("Получение списка ролей пользователя")
    public void userRolesList() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get(constant.USER_ROLES)
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(priority = 4)
    @Description("Удаление")
    public void deleteUserRole() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        " \"replace_role_id\":" + getValue.get("role_id") +
                        "}")
                .when()
                .delete(constant.USER_ROLES.concat(getValue.get("role_id")))
                .then()
                .log()
                .all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200);
    }
}
