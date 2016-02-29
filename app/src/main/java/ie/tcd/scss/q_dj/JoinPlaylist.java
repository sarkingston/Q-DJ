package ie.tcd.scss.q_dj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sam on 24/2/16.
 */
public class JoinPlaylist extends AppCompatActivity {

    String partyName;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_playlist);

         dialog = new ProgressDialog(this); // this = YourActivity

        Button btnM = (Button)findViewById(R.id.joinButton);
        /**Joins Party if It exists */

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(JoinPlaylist.this, GuestActivity.class));
                EditText partyNameED = (EditText) findViewById(R.id.party_name);
                partyName = (String) partyNameED.getText().toString();

                //Start the Async Activity
                new joinParty().execute();
            }
        });

    }


    private class joinParty extends AsyncTask<String, Void, Boolean> {

        protected void onPreExecute(String... params) {

            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Joining Party");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {

                ServerComms sc = new ServerComms();

                return  sc.joinParty("0000", partyName);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();
            if (result) {
                //Succesfully Joined the Queue... move on to the next screen or something..

                //Store the party name somehwere

                startActivity(new Intent(JoinPlaylist.this, GuestActivity.class));

            } else {
                //Show a Dialogue that it didn't work for some sort of reason... If guess
                Snackbar.make(findViewById(android.R.id.content),
                        "Sorry! That Party either doesn't exist or you might not be connected",
                        Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
