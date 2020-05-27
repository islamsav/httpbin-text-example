package pojo;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AuthModel {

    @SerializedName("authenticated")
    private Boolean authenticated;

    @SerializedName("user")
    private String user;

    @SerializedName("token")
    private String token;

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public String getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthModel authModel = (AuthModel) o;
        return Objects.equals(authenticated, authModel.authenticated) &&
                Objects.equals(user, authModel.user) &&
                Objects.equals(token, authModel.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authenticated, user, token);
    }

    @Override
    public String toString() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(this);
    }
}
