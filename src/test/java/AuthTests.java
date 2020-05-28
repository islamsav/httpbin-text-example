import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.AuthModel;
import pojo.EndPoints;

public class AuthTests {

    @Test(testName = "POSITIVE auth test on basic-auth/ with random data", description = "execute GET request on http://httpbin.org/basic-auth/{user}/{password}", suiteName = "auth")
    public void positivePerformBasicAuthRequest() {
        String userName = Utils.getRandomString(Utils.EN, 10);
        String password = Utils.getRandomString(Utils.RAND, 10);
        AuthModel authModel = RestAssured.given()
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .auth()
                .basic(userName, password)
                .contentType(ContentType.JSON)
                .get(EndPoints.BASIC_AUTH)
                .then().log().status().log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().body()
                .as(AuthModel.class);
        Assert.assertNotNull(authModel, String.format(ErrorMessages.UNSUCCESSFUL_MAPPING, "AuthModel"));
        Assert.assertTrue(authModel.getAuthenticated());
        Assert.assertEquals(userName, authModel.getUser());
    }

    @Test(testName = "NEGATIVE auth test on basic-auth/ with random data", description = "execute GET request on http://httpbin.org/basic-auth/{user}/{password}", suiteName = "auth")
    public void negativePerformBasicAuthRequest() {
        String userName = Utils.getRandomString(Utils.EN, 10);
        String password = Utils.getRandomString(Utils.RAND, 10);
        int code = RestAssured.given()
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .auth()
                .basic("!@#$%^&*()", "!@#$%^&*()")
                .contentType(ContentType.JSON)
                .get(EndPoints.BASIC_AUTH)
                .then().log().status().log().body()
                .extract().statusCode();
        Assert.assertEquals(401, code, ErrorMessages.STATUS_CODE_NOT_MATCH);
    }

    @Test(testName = "POSITIVE auth test on bearer/ with random data", description = "execute GET request on http://httpbin.org/bearer/", suiteName = "bearer")
    public void performBearerAuthRequest() {
        String generateBearer = Utils.getRandomString(Utils.EN, 10) + Utils.getRandomString(Utils.DIGIT, 30);
        AuthModel authModel = RestAssured.given()
                .header("Authorization", "Bearer " + generateBearer)
                .contentType(ContentType.JSON)
                .get(EndPoints.BEARER)
                .then().log().status().log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().body()
                .as(AuthModel.class);
        Assert.assertNotNull(authModel, String.format(ErrorMessages.UNSUCCESSFUL_MAPPING, "AuthModel"));
        Assert.assertTrue(authModel.getAuthenticated());
        Assert.assertEquals(generateBearer, authModel.getToken());
    }

    @Test(testName = "NEGATIVE auth test on bearer/ without header", description = "execute GET request on http://httpbin.org/bearer/", suiteName = "bearer")
    public void negativeBearerAuthRequest() {
        // Тут не передаем обязательный хидер для данного запроса и получаем статус код 401
        int code = RestAssured.given()
                .contentType(ContentType.JSON)
                .get(EndPoints.BEARER)
                .then().log().status().log().body()
                .extract().statusCode();
        Assert.assertEquals(401, code, ErrorMessages.STATUS_CODE_NOT_MATCH);
    }
}
