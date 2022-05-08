package igmo.pfe.agriculture.models;

import com.google.gson.annotations.SerializedName;

public class Sensors {

    @SerializedName("id")
    private int id;
    @SerializedName("temperature")
    private float tempurature ;
    @SerializedName("humidity")
    private float humidity;
    @SerializedName("soilmoaster")

    private float soilmoaster ;
    @SerializedName("windSpeed")
    private  float windSpeed;
    @SerializedName("x")
    private float x ;
    @SerializedName("y")
    private float y ;
    @SerializedName("timestamp")
    private String timestamp ;

    public Sensors() {
    }

    public Sensors(int id, float tempurature, float humidity, float soilmoaster, float windSpeed, float x, float y, String timestamp) {

        this.id = id;
        this.tempurature = tempurature;
        this.humidity = humidity;
        this.soilmoaster = soilmoaster;
        this.windSpeed = windSpeed;
        this.x = x;
        this.y = y;
        this.timestamp = timestamp;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTempurature() {
        return tempurature;
    }

    public void setTempurature(float tempurature) {
        this.tempurature = tempurature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getSoilmoaster() {
        return soilmoaster;
    }

    public void setSoilmoaster(float soilmoaster) {
        this.soilmoaster = soilmoaster;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
