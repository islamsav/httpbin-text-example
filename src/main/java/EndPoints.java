public class EndPoints {

    /**
     * Base url
     */
    public static final String BASE = "http://httpbin.org/";

    /**
     * Auth url
     */
    public static final String BASIC_AUTH = "basic-auth/{user}/{passwd}";
    public static final String BEARER = "bearer";
    public static final String DIGEST_AUTH = "digest-auth/{qop}/{user}/{passwd}";
    public static final String DIGEST_AUTH_WITH_ALGORITHM = "digest-auth/{qop}/{user}/{passwd}/{algorithm}";
    public static final String DIGEST_AUTH_WITH_ALGORITHM_AND_STALE_AFTER = "digest-auth/{qop}/{user}/{passwd}/{algorithm}/{stale_after}";
    public static final String HIDDEN_BASIC_AUTH = "hidden-basic-auth/{user}/{passwd}";

    /**
     * Dynamic data url
     */
    public static final String BASE_64 = "base64/{value}";
    public static final String BYTES = "bytes/{n}";
    public static final String DELAY = "delay/{delay}";
    public static final String DRIP = "drip";
    public static final String LINKS = "links/{n}/{offset}";
    public static final String RANGE = "range/{numbytes}";
    public static final String STREAM_BYTES = "stream-bytes/{n}";
    public static final String STREAM = "stream/{n}";
    public static final String UUID = "/uuid";
}
