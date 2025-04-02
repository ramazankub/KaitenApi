package regexExample;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

import static io.restassured.RestAssured.given;

public class RegexExample extends BaseTest {

    String pattern = "\\d"; //только одно положительное число от 1 до 9
    String pattern2 = "^\\d+$"; //только положительные числа от начала до конца
    String pattern3 = "\\*[2568]"; //одна из любых цифр

    @Test
    public void getSpaceInfo() {

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get(constant.SPACES.concat("562056"))
                .then()
                .log()
                .all()
                .extract().response();


        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertTrue(response.jsonPath().getString("external_id").matches(pattern));
        Assert.assertTrue(response.jsonPath().getString("path").matches(pattern2));
        Assert.assertTrue(response.jsonPath().getString("company_id").matches(pattern2));
        Assert.assertFalse(response.jsonPath().getString("id").matches(pattern3));
    }
}
