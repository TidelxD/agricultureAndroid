package igmo.pfe.agriculture.models;

import com.google.gson.annotations.SerializedName;

public class Actutors {

    @SerializedName("id")
    private int id;
    @SerializedName("type")
    private int type;
    @SerializedName("x")
    private float x ;
    @SerializedName("y")
    private float y;
    @SerializedName("state")
    private float state;
    @SerializedName("success")
    private int success;
    @SerializedName("message")
    private String message;

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

    public Actutors() {
    }

    public Actutors(int id, int type, float x, float y, float state) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getState() {
        return state;
    }

    public void setState(float state) {
        this.state = state;
    }
}
