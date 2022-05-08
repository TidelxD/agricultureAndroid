package igmo.pfe.agriculture;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import igmo.pfe.agriculture.models.User;
import igmo.pfe.agriculture.screens.DashboardActivity;
import igmo.pfe.agriculture.screens.SplashScreen;
import igmo.pfe.agriculture.service_interface.JsonHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText emailEdit, passEdit;
    private Button loginButton;
    private ProgressDialog progressDialog;
    private JsonHandler jsonHandler;
    // User DATA
    private SplashScreen inst = SplashScreen.getInst();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constans.NODEJS_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        jsonHandler = retrofit.create(JsonHandler.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFromNodeJS();
            }
        });
    }

    // initialize
    private void init(){
        emailEdit=findViewById(R.id.emailEdit);
        passEdit = findViewById(R.id.passEdit);
        loginButton = findViewById(R.id.loginButton);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
    }

    // Get Data and log in

    private void loginFromNodeJS(){
        progressDialog.show();
        String email = emailEdit.getText().toString();
        String password = passEdit.getText().toString();

        if(email.isEmpty()){
            progressDialog.hide();
            emailEdit.setError("Required ! ");
            return;
        }

        if(password.isEmpty()){
            progressDialog.hide();
            passEdit.setError("Required ! ");
            return;
        }
        Call<User> call = jsonHandler.loginUser(email, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Log.i("! isSuccessful ",response.message());
                    progressDialog.hide();
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.hide();
                assert response.body() != null;
                int UserResponse  = response.body().getSuccess();
                if(UserResponse==0){
                    Log.i("UserResponse : 0",response.body().getMessage());
                    progressDialog.hide();
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                User helper = new User(response.body().getUsername(),response.body().getToken());
                inst.setUserData(helper);
                Toast.makeText(MainActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("onFailure",t.getMessage());
            }
        });


    }
}