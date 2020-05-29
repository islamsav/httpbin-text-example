public class EndPoints {

    public static final String BASE = "http://httpbin.org/";
    public static final String BASIC_AUTH = BASE + "basic-auth/{user}/{passwd}";
    public static final String BEARER = BASE + "bearer";
    public static final String DIGEST_AUTH = BASE + "digest-auth/{qop}/{user}/{passwd}";
    public static final String DIGEST_AUTH_WITH_ALGORITHM = BASE + "digest-auth/{qop}/{user}/{passwd}/{algorithm}";
}
