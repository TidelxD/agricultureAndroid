package igmo.pfe.agriculture.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import igmo.pfe.agriculture.R;

public class DashboardActivity extends AppCompatActivity {

    // Components USED
     private CardView Environment,RemoteControll,StatisticsActivity,HistoryCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialization
        Environment=findViewById(R.id.Environment);
        RemoteControll=findViewById(R.id.Remote);
        HistoryCard=findViewById(R.id.HistoryCard);
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

        HistoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,HistoryActivity.class));
            }
        });

    }
}