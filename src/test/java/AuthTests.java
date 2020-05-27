import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.AuthModel;
import pojo.EndPoints;

//TODO динамик дата сделать через okHttp3
//TODO добавить негативные сценарии
public class AuthTests {

    @Test(testName = "POSITIVE auth test on basic-auth/ with random data", description = "execute GET request on http://httpbin.org/basic-auth/{user}/{password}", suiteName = "auth")
    public void performBasicAuthRequest() {
        String userName = Utils.getRandomString(Utils.EN, 10) + Utils.rnd(3, 5);
        String password = Utils.rnd(3, 5) + Utils.getRandomString(Utils.EN, 10);
        String responseBody = RestAssured
                .given()
                .pathParam("user", userName)
                .pathParam("passwd", password)
                .auth()
                .basic(userName, password)
                .contentType(ContentType.JSON)
                .get(EndPoints.BASIC_AUTH)
                .then().log().status().log().body()
                .statusCode(HttpStatus.SC_OK)
                .extract().response().body()
                .asString();
        Assert.assertFalse(responseBody.isEmpty(), ErrorMessages.RESPONSE_BODY_IS_EMPTY);
        AuthModel authModel = new Gson().fromJson(responseBody, AuthModel.class);
        Assert.assertNotNull(responseBody, String.format(ErrorMessages.UNSUCCESSFUL_MAPPING, "AuthModel"));
        Assert.assertTrue(authModel.getAuthenticated());
        Assert.assertEquals(userName, authModel.getUser());
    }

    @Test(testName = "POSITIVE auth test on bearer/ with random data", description = "execute GET request on http://httpbin.org/bearer/")
    public void performBearerAuthRequest() {
        String generateBearer = Utils.getRandomString(Utils.EN, 10) + Utils.rnd(5000, 10000);
        AuthModel authModel = RestAssured
                .given()
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
}
