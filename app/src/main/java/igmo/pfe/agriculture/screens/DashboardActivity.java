package igmo.pfe.agriculture.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import igmo.pfe.agriculture.R;

public class DashboardActivity extends AppCompatActivity {
     private CardView Environment,RemoteControll,StatisticsActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Environment=findViewById(R.id.Environment);
        RemoteControll=findViewById(R.id.Remote);
        StatisticsActivity=findViewById(R.id.StatisticsActivity);

        Environment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,EnvirementStatusActivity.class));
            }
        });
        RemoteControll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,RemoteControlActivity.class));
            }
        });
        StatisticsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,StatisicsAcivity.class));
            }
        });

    }
}