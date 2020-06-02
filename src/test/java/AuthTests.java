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
        String generateUserName = DataGen.GEN_USER_NAME;
        LOGGER.info("user: {}", generateUserName);
        String generatePassword = DataGen.GEN_PASSWORD;
        LOGGER.info("password: {}", generatePassword);
        AuthModel authModel = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("user", generateUserName)
                .pathParam("passwd", generatePassword)
                .auth()
                .basic(generateUserName, generatePassword)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .get(EndPoints.BASIC_AUTH)
                .then().log().status().log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().body()
                .as(AuthModel.class);
        Assert.assertNotNull(authModel, String.format(ErrorMessages.UNSUCCESSFUL_MAPPING, "AuthModel"));
        Assert.assertTrue(authModel.getAuthenticated());
        Assert.assertEquals(generateUserName, authModel.getUser());
        LOGGER.info("Test [{}] PASSED", Utils.getRunningMethodName(2));
    }

    @Test(testName = "NEGATIVE auth test on /basic-auth/{user}/{password} with random data", suiteName = "auth")
    public void negativePerformBasicAuthRequest() {
        String userName = DataGen.GEN_USER_NAME;
        LOGGER.info("user: {}", userName);
        String password = DataGen.GEN_PASSWORD;
        LOGGER.info("password: {}", password);
        RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .auth()
                .basic("!@#$%^&*()", "!@#$%^&*()")
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .get(EndPoints.BASIC_AUTH)
                .then().log().status()
                .assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED);
        LOGGER.info("Test [{}] PASSED", Utils.getRunningMethodName(2));
    }

    @Test(testName = "POSITIVE auth test on bearer/ with random data", suiteName = "auth")
    public void performBearerAuthRequest() {
        String generateBearer = DataGen.getRandomString(DataGen.RAND_CHARS, DataGen.rnd(30, 100));
        LOGGER.info("Bearer {}", generateBearer);
        AuthModel authModel = RestAssured.given().baseUri(EndPoints.BASE)
                .header("Authorization", "Bearer " + generateBearer)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .get(EndPoints.BEARER)
                .then().log().status().log().body()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().response().body()
                .as(AuthModel.class);
        Assert.assertNotNull(authModel, String.format(ErrorMessages.UNSUCCESSFUL_MAPPING, "AuthModel"));
        Assert.assertTrue(authModel.getAuthenticated());
        Assert.assertEquals(generateBearer, authModel.getToken());
        LOGGER.info("Test [{}] PASSED", Utils.getRunningMethodName(2));
    }

    @Test(testName = "NEGATIVE auth test on bearer/ without header", suiteName = "auth")
    public void negativeBearerAuthRequest() {
        // Тут не передаем обязательный хидер для данного запроса и получаем статус код 401
        int code = RestAssured.given().baseUri(EndPoints.BASE)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .get(EndPoints.BEARER)
                .then().log().status().log().body()
                .extract().statusCode();
        Assert.assertEquals(HttpStatus.SC_UNAUTHORIZED, code, ErrorMessages.STATUS_CODE_NOT_MATCH);
        LOGGER.info("Request status code = {}", code);
        LOGGER.info("Test [{}] PASSED", Utils.getRunningMethodName(2));
    }

    @Test(testName = "POSITIVE /digest-auth/{qop}/{user}/{passwd}", suiteName = "auth")
    public void positiveDigestAuthRequest() {
        String userName = DataGen.GEN_USER_NAME;
        LOGGER.info("user: {}", userName);
        String password = DataGen.GEN_PASSWORD;
        LOGGER.info("password: {}", password);
        AuthModel authModel = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("qop", "auth")
                .contentType(ContentType.JSON)
                .auth().digest(userName, password)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.DIGEST_AUTH)
                .then().log().status().log().body()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(AuthModel.class);
        Assert.assertNotNull(authModel, String.format(ErrorMessages.UNSUCCESSFUL_MAPPING, "AuthModel"));
        Assert.assertTrue(authModel.getAuthenticated());
        Assert.assertEquals(userName, authModel.getUser());
        LOGGER.info("Test [{}] PASSED", Utils.getRunningMethodName(2));
    }

    @Test(testName = "NEGATIVE /digest-auth/{qop}/{user}/{passwd}", suiteName = "auth")
    public void negativeDigestAuthRequest() {
        String userName = DataGen.GEN_USER_NAME;
        LOGGER.info("user: {}", userName);
        String password = DataGen.GEN_PASSWORD;
        LOGGER.info("password: {}", password);
        int code = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("qop", "auth")
                .contentType(ContentType.JSON)
                .auth().digest(userName, userName)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.DIGEST_AUTH)
                .then().log().status().log().body()
                .extract().statusCode();
        Assert.assertEquals(HttpStatus.SC_UNAUTHORIZED, code, ErrorMessages.STATUS_CODE_NOT_MATCH);
        LOGGER.info("Request status code = {}", code);
        LOGGER.info("Test [{}] PASSED", Utils.getRunningMethodName(2));
    }

    @Test(testName = "POSITIVE /digest-auth/{qop}/{user}/{passwd}/{algorithm}", suiteName = "auth")
    public void positiveDigestAuthWithAlgorithmRequest() {
        String userName = DataGen.GEN_USER_NAME;
        LOGGER.info("user: {}", userName);
        String password = DataGen.GEN_PASSWORD;
        LOGGER.info("password: {}", password);
        AuthModel authModel = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("qop", "auth")
                .pathParam("algorithm", "MD5")
                .contentType(ContentType.JSON)
                .auth().digest(userName, password)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.DIGEST_AUTH_WITH_ALGORITHM)
                .then().log().status().log().body()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(AuthModel.class);
        Assert.assertNotNull(authModel, String.format(ErrorMessages.UNSUCCESSFUL_MAPPING, "AuthModel"));
        Assert.assertTrue(authModel.getAuthenticated());
        Assert.assertEquals(userName, authModel.getUser());
        LOGGER.info("Test [{}] PASSED", Utils.getRunningMethodName(2));
    }

    @Test(testName = "NEGATIVE /digest-auth/{qop}/{user}/{passwd}/{algorithm}", suiteName = "auth")
    public void negativeDigestAuthWithAlgorithmRequest() {
        String userName = DataGen.GEN_USER_NAME;
        LOGGER.info("user: {}", userName);
        String password = DataGen.GEN_PASSWORD;
        LOGGER.info("password: {}", password);
        int code = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("qop", "auth")
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("algorithm", "MD5")
                .contentType(ContentType.JSON)
                .auth().digest(userName, userName)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.DIGEST_AUTH_WITH_ALGORITHM)
                .then().log().status().log().body()
                .extract().statusCode();
        Assert.assertEquals(HttpStatus.SC_UNAUTHORIZED, code, ErrorMessages.STATUS_CODE_NOT_MATCH);
        LOGGER.info("Request status code = {}", code);
        LOGGER.info("Test [{}] PASSED", Utils.getRunningMethodName(2));
    }

    @Test(testName = "POSITIVE digest-auth/{qop}/{user}/{passwd}/{algorithm}/{stale_after}", suiteName = "auth")
    public void positiveDigestAuthWithStaleAfter() {
        String userName = DataGen.GEN_USER_NAME;
        LOGGER.info("user: {}", userName);
        String password = DataGen.GEN_PASSWORD;
        LOGGER.info("password: {}", password);
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("qop", "auth")
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("algorithm", "MD5")
                .pathParam("stale_after", "never")
                .contentType(ContentType.JSON).auth().digest(userName, password)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.DIGEST_AUTH_WITH_ALGORITHM_AND_STALE_AFTER);
        response.then().log().status().log().body();
        int code = response.statusCode();
        Assert.assertEquals(HttpStatus.SC_OK, code, ErrorMessages.STATUS_CODE_NOT_MATCH);
        response.then().body("authenticated", equalTo(true), "user", equalTo(userName));
        LOGGER.info("Request status code = {}", code);
        LOGGER.info("Test [{}] PASSED", Utils.getRunningMethodName(2));
    }

    @Test(testName = "NEGATIVE digest-auth/{qop}/{user}/{passwd}/{algorithm}/{stale_after}", suiteName = "auth")
    public void negativeDigestAuthWithStaleAfter() {
        String userName = DataGen.GEN_USER_NAME;
        LOGGER.info("user: {}", userName);
        String password = DataGen.GEN_PASSWORD;
        LOGGER.info("password: {}", password);
        RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("qop", "auth")
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .pathParam("algorithm", "MD5")
                .pathParam("stale_after", "never")
                .contentType(ContentType.JSON)
                .auth().digest("!@#$%^&*()", "!@#$%^&*()")
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.DIGEST_AUTH_WITH_ALGORITHM_AND_STALE_AFTER)
                .then().log().status().log().body()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
        LOGGER.info("Test [{}] PASSED", Utils.getRunningMethodName(2));
    }

    @Test(testName = "POSITIVE hidden-basic-auth/{user}/{passwd}", suiteName = "auth")
    public void positiveHiddenBasicAuth() {
        String userName = DataGen.GEN_USER_NAME;
        LOGGER.info("user: {}", userName);
        String password = DataGen.GEN_PASSWORD;
        LOGGER.info("password: {}", password);
        String auth = Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
        LOGGER.info("auth basic code: {}", auth);
        RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .header("Authorization", "Basic " + auth)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.HIDDEN_BASIC_AUTH)
                .then().log().status().log().body()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .and().body("authenticated", equalTo(true), "user", equalTo(userName));
        LOGGER.info("Test: {} is passed", Utils.getRunningMethodName(2));
    }
}
