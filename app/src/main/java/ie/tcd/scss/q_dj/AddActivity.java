package ie.tcd.scss.q_dj;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    //saved themes from changeColour
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    String query_type;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Use the chosen theme
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme){
            setTheme(R.style.AppTheme2);
        } else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setupActionBar();

        //Add to the Queue - or in other words, search...spotify


    }

    public void search(View v){
        EditText et = (EditText) findViewById(R.id.party_name);
        String q = et.getText().toString();
        new searchSpotify().execute("track", q);

    }

    private class searchSpotify extends AsyncTask<String, Void, ArrayList<Song>> {

        protected void onPreExecute(String... params) {
        }

        @Override
        protected ArrayList doInBackground(String... params) {
            try {

                SpotifyHelper sh = new SpotifyHelper();
                return  sh.query(params[1], params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Song> result) {
            if (result != null) {
                //Parse the Queue
                //Put Each Song in the Listview or whatever it is...
                //We have an ARRAYLIST of 'Song's so it should be easy enough to put them into some sort of list

            } else {
                //Show a Dialogue that it didn't work for some sort of reason... If guess
                Snackbar.make(findViewById(android.R.id.content),
                        "Sorry! That didn't work, are you sure you're connected?",
                        Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
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
