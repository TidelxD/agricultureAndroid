package igmo.pfe.agriculture.service_interface;

import igmo.pfe.agriculture.models.Sensors;
import igmo.pfe.agriculture.models.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonHandler {

    @Headers({"Accept: application/json"})

    @GET("login/'{username}'/{password}")
    Call<User> loginUser(@Path("username") String username, @Path("password") String password);

    @GET("getLastDataSensors")
    Call<Sensors> getLastData(@Header("Authorization") String token);
}