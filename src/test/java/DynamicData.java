import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.StreamModel;
import pojo.Uuid;

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
                .log().method().log().uri().log().headers()
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
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.BYTES);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        String responseBody = response.then().extract().body().asString();
        Assert.assertEquals(responseBody.length(), rnd);
        Assert.assertFalse(responseBody.isEmpty());
    }

    @Test(testName = "Execute DELETE delay/{delay}", suiteName = "dynamic-data")
    public void executeDeleteDelayRequest() {
        StopWatch stopWatch = new StopWatch();
        int rnd = DataGen.rnd(5, 10);
        stopWatch.start();
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("delay", rnd)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .when().delete(EndPoints.DELAY);
        int delay = (int) stopWatch.getTime(TimeUnit.SECONDS);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        Assert.assertTrue(delay >= rnd);
    }

    @Test(testName = "Execute GET delay/{delay}", suiteName = "dynamic-data")
    public void executeGetDelayRequest() {
        StopWatch stopWatch = new StopWatch();
        int rnd = DataGen.rnd(5, 10);
        stopWatch.start();
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("delay", rnd)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.DELAY);
        int delay = (int) stopWatch.getTime(TimeUnit.SECONDS);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        Assert.assertTrue(delay >= rnd);
    }

    @Test(testName = "Execute PATCH delay/{delay}", suiteName = "dynamic-data")
    public void executePatchDelayRequest() {
        StopWatch stopWatch = new StopWatch();
        int rnd = DataGen.rnd(5, 10);
        stopWatch.start();
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("delay", rnd)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .when().patch(EndPoints.DELAY);
        int delay = (int) stopWatch.getTime(TimeUnit.SECONDS);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        Assert.assertTrue(delay >= rnd);
    }

    @Test(testName = "Execute POST delay/{delay}", suiteName = "dynamic-data")
    public void executePostDelayRequest() {
        StopWatch stopWatch = new StopWatch();
        int rnd = DataGen.rnd(5, 10);
        stopWatch.start();
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("delay", rnd)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .when().post(EndPoints.DELAY);
        int delay = (int) stopWatch.getTime(TimeUnit.SECONDS);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        Assert.assertTrue(delay >= rnd);
    }

    @Test(testName = "Execute PUT delay/{delay}", suiteName = "dynamic-data")
    public void executePutDelayRequest() {
        StopWatch stopWatch = new StopWatch();
        int rnd = DataGen.rnd(5, 10);
        stopWatch.start();
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("delay", rnd)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .when().put(EndPoints.DELAY);
        int delay = (int) stopWatch.getTime(TimeUnit.SECONDS);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        Assert.assertTrue(delay >= rnd);
    }

    @Test(testName = "Execute GET drip", suiteName = "dynamic-data")
    public void executeGetDripRequest() {
        StopWatch stopWatch = new StopWatch();
        int statusCode = HttpStatus.SC_OK;
        int rnd = DataGen.rnd(3, 5);
        stopWatch.start();
        RestAssured.given().baseUri(EndPoints.BASE)
                .queryParam("duration", DataGen.rnd(1, 5))
                .queryParam("numbytes", DataGen.rnd(1, 5))
                .queryParam("code", statusCode)
                .queryParam("delay", rnd)
                .contentType(ContentType.BINARY)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.DRIP)
                .then().log().status().log().body()
                .assertThat().statusCode(statusCode);
        int delay = (int) stopWatch.getTime(TimeUnit.SECONDS);
        Assert.assertTrue(delay >= rnd);
    }

    @Test(testName = "Execute GET links/{n}/{offset}", suiteName = "dynamic-data")
    public void executeGetLinksRequest() {
        int n = DataGen.rnd(1, 3);
        int offset = DataGen.rnd(1, 5);
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("n", n)
                .pathParam("offset", offset)
                .contentType(ContentType.HTML)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.LINKS);
        response.then().log().status().log().body()
                .assertThat().statusCode(HttpStatus.SC_OK);
        String body = response.then().extract().body().asString();
        Assert.assertNotNull(body);
        Assert.assertFalse(body.isEmpty());
    }

    @Test(testName = "Execute GET range/{numbytes}", suiteName = "dynamic-data")
    public void executeGetRangeRequest() {
        int range = DataGen.rnd(50, 100);
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("numbytes", range)
                .contentType(ContentType.BINARY)
                .log().method().log().uri().log().headers()
                .get(EndPoints.RANGE);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        String body = response.then().extract().body().asString();
        Assert.assertEquals(body.length(), range);
    }

    @Test(testName = "Execute GET stream-bytes/{n}", suiteName = "dynamic-data")
    public void executeStreamBytesRangeRequest() {
        int n = DataGen.rnd(50, 100);
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("n", n)
                .contentType(ContentType.BINARY)
                .log().method().log().uri().log().headers()
                .get(EndPoints.STREAM_BYTES);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        String body = response.then().extract().body().asString();
        Assert.assertNotNull(body);
        Assert.assertFalse(body.isEmpty());
    }

    @Test(testName = "Execute GET stream/{n}", suiteName = "dynamic-data")
    public void executeStreamRangeRequest() {
        Response response = RestAssured.given().baseUri(EndPoints.BASE)
                .pathParam("n", 1)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.STREAM);
        response.then().log().status().log().body();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
        String json = response.then().extract().body().asString();
        StreamModel responseObjects = new Gson().fromJson(json, StreamModel.class);
        Assert.assertNotNull(responseObjects, ErrorMessages.UNSUCCESSFUL_MAPPING);
        Assert.assertEquals(responseObjects.getHeaders().getContentType(), "application/json; charset=UTF-8");
        Assert.assertEquals(responseObjects.getHeaders().getHost(), "httpbin.org");
    }

    @Test(testName = "Execute GET uuid", suiteName = "dynamic-data")
    public void executeUuidRequest() {
        Uuid uuid = RestAssured.given().baseUri(EndPoints.BASE)
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().headers()
                .when().get(EndPoints.UUID)
                .then().log().status().log().body()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Uuid.class);
        Assert.assertNotNull(uuid, ErrorMessages.UNSUCCESSFUL_MAPPING);
        Assert.assertFalse(uuid.getUuid().isEmpty());
    }
}
