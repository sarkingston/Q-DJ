package ie.tcd.scss.q_dj;

/**
 * Created by conor on 12/02/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    int[] points;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Intent intent = getIntent();
        points = intent.getIntArrayExtra(Main2Activity.POINTS);

        BarChart chart = (BarChart) findViewById(R.id.chart);

        /*ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(10, 0));
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

        names = intent.getStringArrayExtra(Main2Activity.NAMES);

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

}


