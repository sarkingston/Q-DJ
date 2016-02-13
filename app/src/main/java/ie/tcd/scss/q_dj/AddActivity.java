package ie.tcd.scss.q_dj;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    String query_type;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Add to the Queue - or in other words, search...spotify


    }

    public void search(){
        EditText et = (EditText) findViewById(R.id.search_q);
        String q = et.getText().toString();

        new searchSpotify().execute("track", q);

    }

    private class searchSpotify extends AsyncTask<String, Void, ArrayList<Song>> {

        protected void onPreExecute(String... params) {
            query_type = params[0];
            query = params[1];
        }

        @Override
        protected ArrayList doInBackground(String... params) {
            try {
                return new SpotifyHelper().query(query, query_type);
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
}
