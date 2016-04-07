package ie.tcd.scss.q_dj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.spotify.sdk.android.player.Config;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Sam on 24/2/16.
 */
public class JoinPlaylist extends AppCompatActivity {

    //saved themes from changeColour
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    public static final String USER_ID = "1995";

    String partyName, token;
    ProgressDialog dialog;
    Button  hostBtn;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        token = i.getStringExtra("token");

        // Use the chosen theme
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme){
            setTheme(R.style.AppTheme2);
        } else{
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_join_playlist);

        dialog = new ProgressDialog(this);
        hostBtn = (Button) findViewById(R.id.hostButton);

        /**Opens up QHost activity*/
        hostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /*Random r = new Random();
                int k = r.nextInt(2);
                if (mInterstitialAd.isLoaded()) {
                    if (k < 1) {
                        mInterstitialAd.show();
                    }
                }*/
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
                return ServerComms.createParty(USER_ID, partyName);
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
                intent.putExtra("TOKEN", token);

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
                return ServerComms.joinParty(USER_ID, partyName);
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
                intent.putExtra("TOKEN", token);

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

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
