package igmo.pfe.agriculture.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class StatisicsAcivity extends AppCompatActivity {


    private LineChart lineChart;
    private LineDataSet line_data_set = new LineDataSet(null,null);
    private ArrayList<ILineDataSet> LDSAL = new ArrayList<>();
    LineData lineData;

    // API Handler
    private JsonHandler jsonHandler;
    // User DATA
    private SplashScreen inst = SplashScreen.getInst();
    private User UserData =inst.getUserData();
    private Sensors helper ;
    ArrayList<String> xAxies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisics_acivity);

        lineChart=findViewById(R.id.lineChart);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constans.NODEJS_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        jsonHandler = retrofit.create(JsonHandler.class);

        Call<Sensors> call = jsonHandler.getLastSendors("Bearer "+UserData.getToken());

        call.enqueue(new Callback<Sensors>() {
            @Override
            public void onResponse(@NonNull Call<Sensors> call, @NonNull Response<Sensors> response) {
                if(!response.isSuccessful()){
                    Log.e("NotSuccessful ",response.message());
                    Toast.makeText(StatisicsAcivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Entry> dataVals = new ArrayList<>();
                helper = response.body();
                assert helper != null;



                Date date = new Date((long) helper.getTimestamp());
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                String dates = sdf.format(date);
                Log.e("dates","dates: "+dates);

                dataVals.add(new Entry(123456,20));
                xAxies.add(dates);
                dataVals.add(new Entry(123459,helper.getTempurature()));
                xAxies.add(dates);
                dataVals.add(new Entry(123462,50));
                xAxies.add(dates);
//                @SuppressLint("SimpleDateFormat") final Timestamp timestamp2 =
//                        Timestamp.valueOf(
//                                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
//                                        .format(new Date()) // get the current date as String
//                                        .concat("04-09-2022 13:51:00"));        // and append the time
//                long TMS2 = Long.parseLong(timestamp2.toString());
//                Log.e("TMS2","TMS2: "+TMS2);
                dataVals.add(new Entry(123465,19));
                xAxies.add(dates);
                dataVals.add(new Entry(123468,60));
                xAxies.add(dates);

                showChart(dataVals);
            }

            @Override
            public void onFailure(@NonNull Call<Sensors> call, @NonNull Throwable t) {
                Toast.makeText(StatisicsAcivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onFailure",t.getMessage());
            }
        });


    }


    private void showChart(ArrayList<Entry> dataVals){
        line_data_set.setValues(dataVals);
        line_data_set.setLabel("TemperatureSet");
        LDSAL.clear();
        LDSAL.add(line_data_set);
        lineData= new LineData(LDSAL);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        //String setter in x-Axis
        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxies));
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(xAxies.size());
        xAxis.setGranularityEnabled(true);

        lineChart.clear();
        lineChart.setBorderWidth(4);
        lineChart.setData(lineData);
        lineChart.invalidate();

    }
}