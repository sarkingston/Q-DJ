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
    Button  hostBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_playlist);

        dialog = new ProgressDialog(this);


        hostBtn = (Button) findViewById(R.id.hostButton);

        /**Opens up QHost activity*/
        hostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText partyNameED = (EditText) findViewById(R.id.party_name);
                partyName = partyNameED.getText().toString();

                //Intent intent = new Intent(JoinPlaylist.this, HostActivity.class);

                if(partyName.equals("")){
                    Snackbar.make(findViewById(android.R.id.content),
                            "Please input a party ID", Snackbar.LENGTH_LONG).show();
                } else {
                    new createParty().execute();
                }



                finish();
                /*AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                        AuthenticationResponse.Type.TOKEN,
                        REDIRECT_URI);
                builder.setScopes(new String[]{"user-read-private", "streaming"});
                AuthenticationRequest request = builder.build();

                //brings up the login screen
                AuthenticationClient.openLoginActivity(LoginScreen.this, REQUEST_CODE, request);*/
            }
        });

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

    private class createParty extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                ServerComms sc = new ServerComms();
                return sc.createParty("0000", partyName);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();

            if (result) {
                Intent intent = new Intent(JoinPlaylist.this, HostActivity.class);
                intent.putExtra("PARTYID", partyName);
                intent.putExtra("USERMODE", "host");

                startActivity(intent);
            } else {
                Snackbar.make(findViewById(android.R.id.content),
                        "Sorry! That Party either already exists or you might not be connected",
                        Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) { }
    }

    private class joinParty extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                return ServerComms.joinParty("0000", partyName);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();

            if (result) {
                Intent intent = new Intent(JoinPlaylist.this, HostActivity.class);
                intent.putExtra("PARTYID", partyName);
                intent.putExtra("USERMODE", "guest");

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
