package ie.tcd.scss.q_dj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
            new createParty().execute(partyID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private class createParty extends AsyncTask<String, Void, Void> {

        protected void onPreExecute(String... params) {
            partyID = params[0];
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                new ServerComms().createParty("userID",partyID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Snackbar.make(findViewById(android.R.id.content),
                    "Sorry! That didn't work, that party might already exist. Try again",
                    Snackbar.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
