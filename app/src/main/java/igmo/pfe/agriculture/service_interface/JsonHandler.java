package igmo.pfe.agriculture.service_interface;

import java.util.List;

import igmo.pfe.agriculture.models.Actutors;
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

    @POST("/UpdateStateIns/{type}/{x}/{y}/{state}")
    Call<Actutors> updateActutor(@Header("Authorization") String token,@Path("type") int type,@Path("x") float x,@Path("y") float y
            ,@Path("state") float state);

    @GET("getLastActutor")
    Call<List<Actutors>>getLastActutor(@Header("Authorization") String token);

    @GET("getLastDataSensors")
    Call<Sensors>getLastSendors(@Header("Authorization") String token);

    @GET("getChartDataSensors")
    Call<List<Sensors>>getChartDataSensors(@Header("Authorization") String token);

    ///api/getChartDataSensors
}
