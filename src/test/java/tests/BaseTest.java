package tests;

import constants.Constant;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import readers.ConfigReader;

import static io.restassured.RestAssured.baseURI;

public class BaseTest {

    public Constant constant = new Constant();
    public String token = ConfigReader.getToken();

    @BeforeTest
    public void setUp() {
        baseURI = ConfigReader.getBaseURI();
    }


    @AfterTest
    public void tearDown() {
        System.out.println("Тесты окончены");
    }
}
