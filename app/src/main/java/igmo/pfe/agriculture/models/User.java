package igmo.pfe.agriculture.models;

import com.google.gson.annotations.SerializedName;

public class User {

    // FOR POST
    @SerializedName("id")
    private int id;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    // FOR GET
    @SerializedName("success")
    private int success;
    @SerializedName("message")
    private String message;
    @SerializedName("token")
    private String token;



    public User() {
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public User(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
