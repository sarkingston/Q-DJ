package ie.tcd.scss.q_dj;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;

import java.io.IOException;
//import android.R;


public class LoginScreen extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    //These are details you recieve when you resgister an app
    //on the spotify developers site
    private static final String CLIENT_ID = "92780422e43e48acb1c590c38bacb70f";
    private static final String REDIRECT_URI = "q-dj-login://callback";

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1337;

    //checks user credentials
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login_screen);

        Button b1 = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        Button btnM = (Button)findViewById(R.id.MockButton);
        /**Opens up QHost activity*/
        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, Main2Activity.class));
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                        AuthenticationResponse.Type.TOKEN,
                        REDIRECT_URI);
                builder.setScopes(new String[]{"user-read-private", "streaming"});
                AuthenticationRequest request = builder.build();

                //brings up the login screen
                AuthenticationClient.openLoginActivity(LoginScreen.this, REQUEST_CODE, request);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            //grants access token
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {

                SharedPreferences token = this.getSharedPreferences(
                        "ie.tcd.scss.q_dj", Context.MODE_PRIVATE
                );

                token.edit().putString("authToken", response.getAccessToken()).apply();

                Toast.makeText(LoginScreen.this,
                        "Log in successful", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(LoginScreen.this,
                        "Log in not successful. Proceeding to Main Screen for the banter anyway", Toast.LENGTH_LONG).show();
            }
        }

        Intent i = new Intent(LoginScreen.this, Main2Activity.class);
        startActivity(i);
        finish();

    }



    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("MainActivity", "Playback event received: " + eventType.name());
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
    }

    @Override
    protected void onDestroy() {
        //Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}