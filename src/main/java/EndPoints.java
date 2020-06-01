public class EndPoints {

    public static final String BASE = "http://httpbin.org/";
    public static final String BASIC_AUTH = "basic-auth/{user}/{passwd}";
    public static final String BEARER = "bearer";
    public static final String DIGEST_AUTH = "digest-auth/{qop}/{user}/{passwd}";
    public static final String DIGEST_AUTH_WITH_ALGORITHM = "digest-auth/{qop}/{user}/{passwd}/{algorithm}";
    public static final String DIGEST_AUTH_WITH_ALGORITHM_AND_STALE_AFTER = "digest-auth/{qop}/{user}/{passwd}/{algorithm}/{stale_after}";
    public static final String HIDDEN_BASIC_AUTH = "hidden-basic-auth/{user}/{passwd}";

}
