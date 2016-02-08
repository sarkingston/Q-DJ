package ie.tcd.scss.q_dj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by UI Team on 29/01/16.
 */

public class QHost extends AppCompatActivity {

    String partyID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qhost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //On Click of Submit Button
            //Get The Text and send it to the 'ServerComms' class in an ASYNNCtask
            //new createParty().execute(partyID);
            new getQueue().execute(partyID);


    }

    private class getQueue extends AsyncTask<String, Void, JSONArray> {



        protected void onPreExecute(String... params) {
            partyID = params[0];
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray queue = null;
            try {
                queue = new ServerComms().getQueue(partyID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return queue;             //Returns True if it was joined succesffuly
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            if(result != null){
                //Parse the Queue

                //Put it into the Swipe List
            }else{
                //Show a Dialogue that it didn't work for some sort of reason... If guess
                Snackbar.make(findViewById(android.R.id.content), "Sorry! That didn't work, are you sure you're connected?", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class createParty extends AsyncTask<String, Void, Boolean> {



        protected void onPreExecute(String... params) {
            partyID = params[0];
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean success = false;
            try {
                success = new ServerComms().createParty("userID",partyID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return success;             //Returns True if it was joined succesffuly
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                //Save the partyID
                    //Send to the browser screen or something...
            }else{
                //Show a Dialogue that it didn't work for some sort of reason... If guess
                Snackbar.make(findViewById(android.R.id.content), "Sorry! That didn't work, that party might already exist. Try again", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
