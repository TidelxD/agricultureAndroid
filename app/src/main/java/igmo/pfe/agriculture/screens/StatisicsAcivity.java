package igmo.pfe.agriculture.screens;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import igmo.pfe.agriculture.Constans;
import igmo.pfe.agriculture.R;
import igmo.pfe.agriculture.models.Sensors;
import igmo.pfe.agriculture.models.User;
import igmo.pfe.agriculture.service_interface.JsonHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatisicsAcivity extends AppCompatActivity {


    private LineChart lineChart;

    private LineDataSet line_data_set = new LineDataSet(null, null);
    private ArrayList<ILineDataSet> LDSAL = new ArrayList<>();
    private ImageView backButton;

    LineData lineData;

    // API Handler
    private JsonHandler jsonHandler;
    // User DATA
    private SplashScreen inst = SplashScreen.getInst();
    private User UserData = inst.getUserData();
    private ArrayList<Sensors> helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisics_acivity);

        lineChart = findViewById(R.id.lineChart);
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

        Call<List<Sensors>> call = jsonHandler.getChartDataSensors("Bearer " + UserData.getToken());

        call.enqueue(new Callback<List<Sensors>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NonNull Call<List<Sensors>> call, @NonNull Response<List<Sensors>> response) {
                if (!response.isSuccessful()) {
                    Log.e("NotSuccessful ", response.message());
                    Toast.makeText(StatisicsAcivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Entry> dataVals = new ArrayList<>();
                helper = (ArrayList<Sensors>) response.body();
                int x = helper.size();
                Log.e("helper", "size: " + helper.size());
                for (int i = 0; i < (helper != null ? helper.size() : 0); i++) {

                    float Xnew = helper.get(i).getTimestamp() - helper.get(0).getTimestamp();
                    Log.d("timesSpat", ": " + helper.get(i).getId());
                    Log.d("timesSpat", ": " + helper.get(i).getTimestamp());
                    dataVals.add(0, new Entry(Xnew, helper.get(i).getTempurature()));
                    Log.d("dataVals", ": " + dataVals.get(i).getX() + "");


                }
                showChart(dataVals);

            }

            @Override
            public void onFailure(@NonNull Call<List<Sensors>> call, @NonNull Throwable t) {
                Toast.makeText(StatisicsAcivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure", t.getMessage());
            }
        });


    }


    private void showChart(ArrayList<Entry> dataVals) {
        line_data_set.setValues(dataVals);
        line_data_set.setLabel("TemperatureSet");
        LDSAL.clear();

        line_data_set.setValueFormatter(new MyValueFormatter());

        XAxis XAxs = lineChart.getXAxis();
        XAxs.setValueFormatter(new MyAxiesFormatter());
        XAxs.setPosition(XAxis.XAxisPosition.BOTTOM);
        XAxs.setDrawGridLines(true);
        XAxs.setLabelRotationAngle(90);

        LDSAL.add(line_data_set);
        lineData = new LineData(LDSAL);

        // Cubic lines
        line_data_set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        line_data_set.setDrawFilled(true);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fil_tempurature_gradiant);
        line_data_set.setFillDrawable(drawable);

        lineChart.clear();
        lineChart.setNoDataTextColor(Color.GREEN);
        lineChart.setBorderWidth(2);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);

        Description description = new Description();
        description.setText("Temperature");
        description.setTextSize(10);
        description.setTextColor(Color.BLUE);
        lineChart.setDescription(description);
        lineChart.setData(lineData);
        lineChart.invalidate();

    }


    // Creating Class For Formatting Data Sets
    public class MyValueFormatter extends ValueFormatter {


        @Override
        public String getFormattedValue(float value) {
            return value + "°C";
        }


    }

    public class MyAxiesFormatter extends ValueFormatter {


        @Override
        public String getFormattedValue(float value) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.FRANCE);
            String Sdate = dateFormat.format(new Date((long) value));

            return Sdate;


        }


    }

    /*
     *

     *  */
}