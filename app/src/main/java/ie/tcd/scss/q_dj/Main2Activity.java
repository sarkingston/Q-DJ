package ie.tcd.scss.q_dj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;


public class Main2Activity extends AppCompatActivity {


    private Toolbar toolbar;
    private RecyclerView recyclerView;

    //sample array and names to simulate graph data
    int[] points = new int[]{2,6,7,4,};
    public final static String POINTS = "ie.tcd.scss.q_dj.POINTS";
    public final static String NAMES = "ie.tcd.scss.counter.NAMES";

    //saved themes from changeColour
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    //private MyTask task;
    private static final String URL = "http://Alex-was-here";
    private URL url;
    private HttpURLConnection urlConnection;
    private List<ServerComms> customList;
    private String title, image, album;

    ArrayList<ServerComms> songList = new ArrayList<ServerComms>();

    private ProgressDialog progressDialog;
    private CardViewHelper adapter;

    com.getbase.floatingactionbutton.FloatingActionButton skip_previous;
    com.getbase.floatingactionbutton.FloatingActionButton play;
    com.getbase.floatingactionbutton.FloatingActionButton skip_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Use the chosen theme
        /*SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        if(useDarkTheme){
            setTheme(R.style.AppTheme2);
            //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
            //setSupportActionBar(toolbar);
        } else{
            setTheme(R.style.AppTheme);
            //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("My Q"));
        tabLayout.addTab(tabLayout.newTab().setText("X's Q"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        skip_previous = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.skip_previous);
        play = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.play);
        skip_next = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.skip_next);

        /**Google Card Lst View Implementation
         * of recycler view for dynamic list
         * entries and place swapping
         * */
        recyclerView = (RecyclerView)findViewById(R.id.recyclerList);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(true);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Main2Activity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnectedOrConnecting();

        /**Internet availability implementation*/
       /* if (connected){
            accessWebService();
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    accessWebService();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }*/
        /*else {
            com.nispok.snackbar.Snackbar.with(Intro.this).text("No Internet Connection").actionLabel("Retry").actionListener(new ActionClickListener() {
                @Override
                public void onActionClicked(com.nispok.snackbar.Snackbar snackbar) {
                    accessWebService();
                    snackbar.dismiss();
                }
            }).show(Main2Activity.this);
        }*/



        /**ViewPager Implementation*/
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
/*
        final Button activate = (Button) findViewById(R.id.activate);
        final Button deactivate = (Button) findViewById(R.id.deactivate);
        activate.setVisibility(View.GONE);
        deactivate.setVisibility(View.VISIBLE);
        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                activate.setVisibility(View.GONE);
                deactivate.setVisibility(View.VISIBLE);

            }
        });

        deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when play is clicked show stop button and hide play button
                activate.setVisibility(View.VISIBLE);
                deactivate.setVisibility(View.GONE);

            }
        });
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                Settings();
                return true;
            case R.id.action_graph:
                graph();
                return true;
            case R.id.action_changeColour:
                ChangeColour();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void add(View v) {
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);

    }
    private void Settings(){
        Intent i = new Intent(Main2Activity.this, SettingsActivity.class);
        startActivity(i);
    }

    //Temp Colour Change Activity
    private void ChangeColour(){
        Intent i = new Intent(Main2Activity.this, ChangeColourActivity.class);
        startActivity(i);
    }

    public void graph(){
        Intent intent = new Intent(this,GraphActivity.class);
        intent.putExtra(POINTS, points);
        //intent.putExtra(LABELS, metrics);
        startActivity(intent);
    }

    //ensures that after changing colour, the activity starts
    //fresh so to override previous colour
    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        finish();
        Intent i = new Intent(Main2Activity.this, Main2Activity.class);
        startActivity(i);
    }



}
