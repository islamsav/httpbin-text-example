import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.AuthModel;

import java.util.Base64;

import static org.hamcrest.Matchers.equalTo;

public class AuthTests {
    private final static Logger LOGGER = LoggerFactory.getLogger(AuthTests.class);

    @Test(testName = "POSITIVE auth test on /basic-auth/{user}/{password} with random data", suiteName = "auth")
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

    @Test(testName = "NEGATIVE auth test on /basic-auth/{user}/{password} with random data", suiteName = "auth")
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

    @Test(testName = "POSITIVE auth test on bearer/ with random data", suiteName = "auth")
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

    @Test(testName = "NEGATIVE auth test on bearer/ without header", suiteName = "auth")
    public void negativeBearerAuthRequest() {
        // Тут не передаем обязательный хидер для данного запроса и получаем статус код 401
        int code = RestAssured.given()
                .contentType(ContentType.JSON)
                .get(EndPoints.BEARER)
                .then().log().status().log().body()
                .extract().statusCode();
        Assert.assertEquals(401, code, ErrorMessages.STATUS_CODE_NOT_MATCH);
    }

    @Test(testName = "POSITIVE /digest-auth/{qop}/{user}/{passwd}", suiteName = "auth")
    public void positiveDigestAuthRequest() {
        String userName = Utils.getRandomString(Utils.EN, 10);
        String password = Utils.getRandomString(Utils.RAND, 10);
        AuthModel authModel = RestAssured.given()
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("qop", "auth")
                .contentType(ContentType.JSON)
                .auth().digest(userName, password)
                .when().get(EndPoints.DIGEST_AUTH)
                .then().log().status().log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(AuthModel.class);
        Assert.assertNotNull(authModel, String.format(ErrorMessages.UNSUCCESSFUL_MAPPING, "AuthModel"));
        Assert.assertTrue(authModel.getAuthenticated());
        Assert.assertEquals(userName, authModel.getUser());
    }

    @Test(testName = "NEGATIVE /digest-auth/{qop}/{user}/{passwd}", suiteName = "auth")
    public void negativeDigestAuthRequest() {
        String userName = Utils.getRandomString(Utils.EN, 10);
        String password = Utils.getRandomString(Utils.RAND, 10);
        int code = RestAssured.given()
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("qop", "auth")
                .contentType(ContentType.JSON)
                .auth().digest(userName, userName)
                .when().get(EndPoints.DIGEST_AUTH)
                .then().log().status().log().body()
                .extract().statusCode();
        Assert.assertEquals(401, code, ErrorMessages.STATUS_CODE_NOT_MATCH);
    }

    @Test(testName = "POSITIVE /digest-auth/{qop}/{user}/{passwd}/{algorithm}", suiteName = "auth")
    public void positiveDigestAuthWithAlgorithmRequest() {
        String userName = Utils.getRandomString(Utils.EN, 10);
        String password = Utils.getRandomString(Utils.RAND, 10);
        AuthModel authModel = RestAssured.given()
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("qop", "auth")
                .pathParam("algorithm", "MD5")
                .contentType(ContentType.JSON)
                .auth().digest(userName, password)
                .when().get(EndPoints.DIGEST_AUTH_WITH_ALGORITHM)
                .then().log().status().log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(AuthModel.class);
        Assert.assertNotNull(authModel, String.format(ErrorMessages.UNSUCCESSFUL_MAPPING, "AuthModel"));
        Assert.assertTrue(authModel.getAuthenticated());
        Assert.assertEquals(userName, authModel.getUser());
    }

    @Test(testName = "NEGATIVE /digest-auth/{qop}/{user}/{passwd}/{algorithm}", suiteName = "auth")
    public void negativeDigestAuthWithAlgorithmRequest() {
        String userName = Utils.getRandomString(Utils.EN, 10);
        String password = Utils.getRandomString(Utils.RAND, 10);
        int code = RestAssured.given()
                .pathParam("qop", "auth")
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("algorithm", "MD5")
                .contentType(ContentType.JSON)
                .auth().digest(userName, userName)
                .when().get(EndPoints.DIGEST_AUTH_WITH_ALGORITHM)
                .then().log().status().log().body()
                .extract().statusCode();
        Assert.assertEquals(401, code, ErrorMessages.STATUS_CODE_NOT_MATCH);
    }

    @Test(testName = "POSITIVE digest-auth/{qop}/{user}/{passwd}/{algorithm}/{stale_after}", suiteName = "auth")
    public void positiveDigestAuthWithStaleAfter() {
        String userName = Utils.getRandomString(Utils.EN, 10);
        String password = Utils.getRandomString(Utils.RAND, 10);
        Response response = RestAssured.given()
                .pathParam("qop", "auth")
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("algorithm", "MD5")
                .pathParam("stale_after", "never")
                .contentType(ContentType.JSON).auth().digest(userName, password)
                .when().get(EndPoints.DIGEST_AUTH_WITH_ALGORITHM_AND_STALE_AFTER);
        response.then().log().status().log().body();
        int code = response.statusCode();
        Assert.assertEquals(200, code, ErrorMessages.STATUS_CODE_NOT_MATCH);
        response.then().body("authenticated", equalTo(true), "user", equalTo(userName));
    }

    @Test(testName = "NEGATIVE digest-auth/{qop}/{user}/{passwd}/{algorithm}/{stale_after}", suiteName = "auth")
    public void negativeDigestAuthWithStaleAfter() {
        String userName = Utils.getRandomString(Utils.EN, 10);
        String password = Utils.getRandomString(Utils.RAND, 10);
        RestAssured.given()
                .pathParam("qop", "auth")
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("algorithm", "MD5")
                .pathParam("stale_after", "never")
                .contentType(ContentType.JSON)
                .auth().digest("!@#$%^&*()", "!@#$%^&*()")
                .when().get(EndPoints.DIGEST_AUTH_WITH_ALGORITHM_AND_STALE_AFTER)
                .then().log().status().log().body()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test(testName = "POSITIVE hidden-basic-auth/{user}/{passwd}", suiteName = "auth")
    public void positiveHiddenBasicAuth() {
        String userName = Utils.getRandomString(Utils.EN, 7);
        LOGGER.info("user: {}", userName);
        String password = Utils.getRandomString(Utils.RAND, 15);
        LOGGER.info("password: {}", password);
        String auth = Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
        LOGGER.info("auth basic code: {}", auth);
        RestAssured.given()
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .header("Authorization", "Basic " + auth)
                .contentType(ContentType.JSON)
                .when().get(EndPoints.HIDDEN_BASIC_AUTH)
                .then().log().status().log().body()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .and().body("authenticated", equalTo(true), "user", equalTo(userName));
        LOGGER.info("Test: {} is passed", Utils.getRunningMethodName(2));
    }
}
