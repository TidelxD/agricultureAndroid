package igmo.pfe.agriculture.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import igmo.pfe.agriculture.Constans;
import igmo.pfe.agriculture.MainActivity;
import igmo.pfe.agriculture.R;
import igmo.pfe.agriculture.models.Sensors;
import igmo.pfe.agriculture.models.User;
import igmo.pfe.agriculture.service_interface.JsonHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnvirementStatusActivity extends AppCompatActivity {

    private JsonHandler jsonHandler;
    // User DATA
    private SplashScreen inst = SplashScreen.getInst();
    private User UserData =inst.getUserData();

    private TextView tempValue,humiValue,WindValue,soilValue,timesValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envirement_status);

        init ();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constans.NODEJS_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        jsonHandler = retrofit.create(JsonHandler.class);
        Log.e("token", UserData.getToken());
        Call<Sensors> call = jsonHandler.getLastData("Bearer "+UserData.getToken());

        call.enqueue(new Callback<Sensors>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Sensors> call, @NonNull Response<Sensors> response) {
                if(!response.isSuccessful()){
                    Log.e("NotSuccessful ",response.message());
                    Toast.makeText(EnvirementStatusActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("response", String.valueOf(response.body()));
                 Sensors helper = response.body();
                Log.e("Humidity", response.body().getHumidity()+"");
                tempValue.setText(response.body().getTempurature()+"°");
                humiValue.setText(response.body().getHumidity()+"%");
                WindValue.setText(response.body().getWindSpeed()+"km/h");
                soilValue.setText(response.body().getSoilmoaster()+"%");
                timesValue.setText(response.body().getTimestamp()+"");


            }

            @Override
            public void onFailure(@NonNull Call<Sensors> call, @NonNull Throwable t) {
                Toast.makeText(EnvirementStatusActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure",t.getMessage());
            }
        });

    }



    private void init (){
        tempValue=findViewById(R.id.tempValue);
        humiValue=findViewById(R.id.humiValue);
        WindValue=findViewById(R.id.WindValue);
        soilValue=findViewById(R.id.soilValue);
        timesValue=findViewById(R.id.timesValue);

    }
}