package igmo.pfe.agriculture.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
    private Switch moteur,type2StateSwitch ;
    private RecyclerView rcv ;
    private ImageView backButton;
    private JsonHandler jsonHandler;
    // User DATA
    private SplashScreen inst = SplashScreen.getInst();
    private User UserData =inst.getUserData();
    private ArrayList<Actutors> helper;

    //
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);
        moteur = findViewById(R.id.moteurStateSwitch);
        type2StateSwitch = findViewById(R.id.type2StateSwitch);
        backButton = findViewById(R.id.backButton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("updating...");
        progressDialog.setCancelable(false);



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
                if(helper.get(1).getState()==0){
                    type2StateSwitch.setText("OFF");
                    type2StateSwitch.setTextColor(Color.RED);
                    type2StateSwitch.setChecked(false);
                }else{
                    type2StateSwitch.setText("ON");
                    type2StateSwitch.setTextColor(Color.GREEN);
                    type2StateSwitch.setChecked(true);
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Actutors>> call, @NonNull Throwable t) {
                Toast.makeText(RemoteControlActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure",t.getMessage());
            }
        });

        // ON Click Listener
        moteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(moteur.isChecked()){
                    // ILa Cha3alt l Moteur ( switch on )
                    moteur.setText("ON");
                    // Text YRodah GREEN
                    moteur.setTextColor(Color.GREEN);
                    // y Updatih l actutor f DATABASE
                    updateActutor(helper.get(0),1);

                }else {
                    // Ila tafit l Moter ( switch off )
                    moteur.setText("OFF");
                    // text yrodah RED
                    moteur.setTextColor(Color.RED);
                    // yupdati l actutor f DATABSE
                    updateActutor(helper.get(0),0);

                }
            }
        });

        type2StateSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type2StateSwitch.isChecked()){
                    // ILa Cha3alt l Moteur ( switch on )
                    type2StateSwitch.setText("ON");
                    // Text YRodah GREEN
                    type2StateSwitch.setTextColor(Color.GREEN);
                    // y Updatih l actutor f DATABASE
                    updateActutor(helper.get(1),1);

                }else {
                    // Ila tafit l Moter ( switch off )
                    type2StateSwitch.setText("OFF");
                    // text yrodah RED
                    type2StateSwitch.setTextColor(Color.RED);
                    // yupdati l actutor f DATABSE
                    updateActutor(helper.get(1),0);

                }
            }
        });
    }



    private void updateActutor(Actutors helper,int state){
            Call<Actutors> call = jsonHandler.updateActutor(UserData.getToken(),helper.getType(),helper.getX(),helper.getY(),state);
        progressDialog.show();
            call.enqueue(new Callback<Actutors>() {
                @Override
                public void onResponse(Call<Actutors> call, Response<Actutors> response) {
                    if(!response.isSuccessful()){
                        progressDialog.dismiss();
                        Log.e("NotSuccessful ",response.message());
                        Toast.makeText(RemoteControlActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progressDialog.dismiss();
                    Toast.makeText(RemoteControlActivity.this, "updated ! ", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Actutors> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(RemoteControlActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("onFailure",t.getMessage());
                }
            });

    }



}