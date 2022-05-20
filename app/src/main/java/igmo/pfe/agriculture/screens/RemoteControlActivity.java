package igmo.pfe.agriculture.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import igmo.pfe.agriculture.Constans;
import igmo.pfe.agriculture.R;
import igmo.pfe.agriculture.models.Actutors;
import igmo.pfe.agriculture.models.Sensors;
import igmo.pfe.agriculture.models.User;
import igmo.pfe.agriculture.service_interface.JsonHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteControlActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch moteur ;
    private RecyclerView rcv ;
    private ImageView backButton;
    private JsonHandler jsonHandler;
    // User DATA
    private SplashScreen inst = SplashScreen.getInst();
    private User UserData =inst.getUserData();
    private ArrayList<Actutors> helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        moteur = findViewById(R.id.moteurStateSwitch);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constans.NODEJS_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        jsonHandler = retrofit.create(JsonHandler.class);
        Log.e("token", UserData.getToken());
        Call<List<Actutors>> call = jsonHandler.getLastActutor("Bearer "+UserData.getToken());

        call.enqueue(new Callback<List<Actutors>>() {
            @Override
            public void onResponse(@NonNull Call<List<Actutors>> call, @NonNull Response<List<Actutors>> response) {
                if(!response.isSuccessful()){
                    Log.e("NotSuccessful ",response.message());
                    Toast.makeText(RemoteControlActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                helper = (ArrayList<Actutors>) response.body();
                if(helper.get(0).getState()==0){
                    moteur.setText("OFF");
                    moteur.setTextColor(Color.RED);
                    moteur.setChecked(false);
                }else{
                    moteur.setText("ON");
                    moteur.setTextColor(Color.GREEN);
                    moteur.setChecked(true);
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Actutors>> call, @NonNull Throwable t) {
                Toast.makeText(RemoteControlActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure",t.getMessage());
            }
        });

        moteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moteur.isChecked()){
                    moteur.setText("ON");
                    moteur.setTextColor(Color.GREEN);
                    updateActutor();

                }else {
                    moteur.setText("OFF");
                    moteur.setTextColor(Color.RED);
                    updateActutor();

                }
            }
        });
    }

    private void updateActutor(){


    }



}