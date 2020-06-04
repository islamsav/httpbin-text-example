package pojo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class StreamModel {

    @SerializedName("args")
    private Args args;

    @SerializedName("headers")
    private Headers headers;

    @SerializedName("origin")
    private String origin;

    @SerializedName("id")
    private Integer id;

    @SerializedName("url")
    private String url;

    public Args getArgs() {
        return args;
    }

    public Headers getHeaders() {
        return headers;
    }

    public String getOrigin() {
        return origin;
    }

    public Integer getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public static class Args {

    }

    public static class Headers {

        @SerializedName("Accept")
        private String accept;

        @SerializedName("User-Agent")
        private String userAgent;

        @SerializedName("Host")
        private String host;

        @SerializedName("Accept-Encoding")
        private String acceptEncoding;

        @SerializedName("X-Amzn-Trace-Id")
        private String xAmznTraceId;

        @SerializedName("Content-Type")
        private String contentType;

        public String getAccept() {
            return accept;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public String getHost() {
            return host;
        }

        public String getAcceptEncoding() {
            return acceptEncoding;
        }

        public String getXAmznTraceId() {
            return xAmznTraceId;
        }

        public String getContentType() {
            return contentType;
        }
    }

    @Override
    public String toString() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this);
    }
}