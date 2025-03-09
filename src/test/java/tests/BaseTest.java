package tests;

import constants.Constant;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import static io.restassured.RestAssured.baseURI;

public class BaseTest {

    public Constant constant = new Constant();
    public String token = "Bearer 48578141-5fad-41e3-a3f3-15424454e61d";

    @BeforeTest
public void setUp() {
    baseURI = "https://rmznkubanov.kaiten.ru/api/latest/";
}

    @AfterTest
    public void tearDown() {

    }
}
