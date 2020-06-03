import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class DynamicData {

    @Test(testName = "Execute GET base64/{value}", suiteName = "dynamic-data")
    public void executeGetBase64Request() {
        String str = "Hello Test!!!";
        String encodeStr = new String(Base64.getEncoder().encode(str.getBytes()));
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("value", encodeStr)
                .contentType(ContentType.HTML)
                .when().get(EndPoints.BASE_64);
        response.then().log().status().log().body();
        String responseBody = response.then().extract().response().body().htmlPath().get().children().get(0).value();
        Assert.assertEquals(response.statusCode(), HttpStatus.SC_OK, ErrorMessages.STATUS_CODE_NOT_MATCH);
        Assert.assertEquals(responseBody, str);
    }

    @Test(testName = "Execute GET bytes/{n}", suiteName = "dynamic-data")
    public void executeGetBytesRequest() {
        int rnd = DataGen.rnd(5, 10);
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("n", rnd)
                .contentType(ContentType.BINARY)
                .when().get(EndPoints.BYTES);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        String responseBody = response.then().extract().body().asString();
        Assert.assertEquals(responseBody.length(), rnd);
        Assert.assertFalse(responseBody.isEmpty());
    }

    @Test(testName = "Execute DELETE delay/{delay}", suiteName = "dynamic-data")
    public void executeDeleteDelayRequest() {
        int rnd = DataGen.rnd(2, 5);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("delay", rnd)
                .contentType(ContentType.JSON)
                .when().delete(EndPoints.DELAY);
        int delay = (int) stopWatch.getTime(TimeUnit.SECONDS);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
    }
}
