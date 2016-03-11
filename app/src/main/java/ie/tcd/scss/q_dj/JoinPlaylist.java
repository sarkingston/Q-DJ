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

        dialog = new ProgressDialog(this);

        Button joinPartyBtn = (Button)findViewById(R.id.joinButton);
        joinPartyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText partyNameED = (EditText) findViewById(R.id.party_name);
                partyName = partyNameED.getText().toString();

                if(partyName.equals("")){
                    Snackbar.make(findViewById(android.R.id.content),
                        "Please input a party ID", Snackbar.LENGTH_LONG).show();
                } else {
                    new joinParty().execute();
                }
            }
        });
    }

    private class joinParty extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                ServerComms sc = new ServerComms();
                return sc.joinParty("0000", partyName);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();

            if (result == true) {
                Intent intent = new Intent(JoinPlaylist.this, HostActivity.class);
                intent.putExtra("PARTYID", partyName);
                startActivity(intent);
            } else {
                Snackbar.make(findViewById(android.R.id.content),
                        "Sorry! That Party either doesn't exist or you might not be connected",
                        Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) { }
    }
}
