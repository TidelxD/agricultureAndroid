package igmo.pfe.agriculture.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import igmo.pfe.agriculture.MainActivity;
import igmo.pfe.agriculture.R;
import igmo.pfe.agriculture.models.User;

public class SplashScreen extends AppCompatActivity {

    public static SplashScreen inst ;

    public SplashScreen (){
        inst=this;
    }
     private User userData ;

    public static SplashScreen getInst(){
        return inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }, 3000);

    }


    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }
}