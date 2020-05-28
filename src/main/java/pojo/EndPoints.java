package pojo;

public class EndPoints {
    public static final String HTTP_BIN_MAIN_URL = "http://httpbin.org/";
    public static final String BASIC_AUTH = HTTP_BIN_MAIN_URL + "basic-auth/{user}/{passwd}";
    public static final String BEARER = HTTP_BIN_MAIN_URL + "bearer";
    public static final String DIGEST_AUTH = HTTP_BIN_MAIN_URL + "digest-auth/{qop}/{user}/{passwd}";
}
