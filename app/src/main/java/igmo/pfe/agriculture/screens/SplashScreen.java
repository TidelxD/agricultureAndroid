package igmo.pfe.agriculture.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import igmo.pfe.agriculture.MainActivity;
import igmo.pfe.agriculture.R;
import igmo.pfe.agriculture.models.User;

public class SplashScreen extends AppCompatActivity {


    // Instace Static ychad ga3 Data nta3 l USER f kamel lAPP
    public static SplashScreen inst ;
    public SplashScreen (){ inst=this; }
     private User userData ;
    public static SplashScreen getInst(){
        return inst;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        // Post Handler  Method Predifinie  f android t'executer l code ba3d certain seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        }, 2000);


    }


    // Setter w Getter nta3 l USER DATA
    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }
}