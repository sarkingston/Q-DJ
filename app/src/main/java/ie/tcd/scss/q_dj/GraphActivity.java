package ie.tcd.scss.q_dj;

/**
 * Created by conor on 12/02/2016.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    int[] points;

    //saved themes from changeColour
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";




    @Override
    protected void onCreate(Bundle savedInstanceState) {// Use the chosen theme
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme){
            setTheme(R.style.AppTheme2);
        } else{
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        setupActionBar();

        Intent intent = getIntent();
        points = intent.getIntArrayExtra(HostActivity.POINTS);

        BarChart chart = (BarChart) findViewById(R.id.chart);

        /*ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(10, 0));//
        entries.add(new BarEntry(2, 1));
        entries.add(new BarEntry(6, 2));
        entries.add(new BarEntry(7, 3));
        entries.add(new BarEntry(3, 4));

        BarDataSet dataSet = new BarDataSet(entries, "Points");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Sid");
        labels.add("Conor");
        labels.add("Darragh");
        labels.add("Sarah");
        labels.add("Dave");
        labels.add("John");*/



        String[] names;

        names = intent.getStringArrayExtra(HostActivity.NAMES);

        //new ArrayList<Element>(Arrays.asList(array))
        ArrayList<String> xvals = new ArrayList<String>();
        //for(int i = 0; i< 4; i++)
        //{
        //xvals.add(names[i]);
        xvals.add("John");
        xvals.add("April");
        xvals.add("Mark");
        xvals.add("Sarah");

        //}

        ArrayList<BarEntry> yvals = new ArrayList<BarEntry>();
        for(int i = 0; i< 4; i++)
        {
            yvals.add(new BarEntry(points[i], i));
        }

        BarDataSet set1 = new BarDataSet(yvals, "Points");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);


        BarData data = new BarData(xvals, dataSets);

        chart.setData(data);



    }
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}