package ie.tcd.scss.q_dj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Sam on 24/2/16.
 */
public class HostActivity extends AppCompatActivity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    //sample array and names to simulate graph data
    int[] points = new int[]{2,6,7,4,};
    public final static String POINTS = "ie.tcd.scss.q_dj.POINTS";
    public final static String NAMES = "ie.tcd.scss.counter.NAMES";
    //saved themes from changeColour
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    com.getbase.floatingactionbutton.FloatingActionsMenu player;
    com.getbase.floatingactionbutton.FloatingActionButton play;
    com.getbase.floatingactionbutton.FloatingActionButton previous;
    com.getbase.floatingactionbutton.FloatingActionButton next;
    boolean playActive;
    String userMode;
    String code;


    //********************************************//
    //These are details you recieve when you resgister an app
    //on the spotify developers site
    private static final String CLIENT_ID = "92780422e43e48acb1c590c38bacb70f";
    private static final String REDIRECT_URI = "q-dj-login://callback";

    //initialse spotify player variable
    MusicPlayer mPlayer = new MusicPlayer();
    Config playerConfig;
    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1337;
    int count = 0;

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
        setContentView(R.layout.activity_host);
        setupActionBar();

        Bundle received = getIntent().getExtras();
        userMode = received.getString("USERMODE");
        code = received.getString("PARTYID");

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        //brings up the login screen
        AuthenticationClient.openLoginActivity(HostActivity.this, REQUEST_CODE, request);

        player = (com.getbase.floatingactionbutton.FloatingActionsMenu) findViewById(R.id.multiple_actions);
        play = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.play);
        previous = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.skip_previous);
        next = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.skip_next);

        if(userMode.equals("guest")) {
            player.setVisibility(View.INVISIBLE);
            play.setVisibility(View.INVISIBLE);
            previous.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
        }

        final int idPlay = R.mipmap.ic_play_arrow_white_24dp;
        final int idPause = R.mipmap.ic_pause_white_24dp;
        play.setIcon(idPlay);
        playActive = true;
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playActive) {
                    play.setIcon(idPause);
                    playActive = false;
                } else {
                    play.setIcon(idPlay);
                    playActive = true;
                }
            }
        });
       loadQueue();
    }

    protected void loadQueue(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            mAdapter = new CardViewAdapter(ServerComms.getQueue(code), code);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            //grants access token
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                mPlayer.addConfig(playerConfig);
                Toast.makeText(HostActivity.this,
                        "Log in successful", Toast.LENGTH_LONG).show();

                final int idPlay = R.mipmap.ic_play_arrow_white_24dp;
                final int idPause = R.mipmap.ic_pause_white_24dp;

                //accesses spotify player and plays a song

                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (playActive) {
                            play.setIcon(idPause);
                            mPlayer.play();
                            playActive = false;
                        }
                        else {
                            play.setIcon(idPlay);
                            mPlayer.play();
                            playActive = true;
                        }
                    }
                });

                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.prevSong();
                        playActive = false;
                        play.setIcon(idPause);
                    }
                });
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.skipNext();
                        playActive = false;
                        play.setIcon(idPause);
                    }
                });
            }
            else{
                Toast.makeText(HostActivity.this,
                        "Log in not successful. Proceeding to Main Screen for the banter anyway", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            ServerComms.getQueue(code);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            loadQueue();
        }

        Bundle received = getIntent().getExtras();
        userMode = received.getString("USERMODE");
        if(userMode == "guest") {
            player.setVisibility(View.INVISIBLE);
            play.setVisibility(View.INVISIBLE);
            previous.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
        }

        ((CardViewAdapter) mAdapter).setOnItemClickListener(new CardViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem Qcode = menu.findItem(R.id.Q_code);
        Qcode.setTitle(String.valueOf(code));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.action_leave_host:
                LeaveHost();
                return true;
            case R.id.action_graph:
                graph();
                return true;
            case R.id.action_changeColour:
                ChangeColour();
                return true;
            case R.id.action_add:
                add(code);
                return true;
            case R.id.action_refresh:
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            //actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void LeaveHost(){

        Intent i = new Intent(this, JoinPlaylist.class);
        startActivity(i);
        finish();
    }

    public void add(String code) {
        Intent intent = new Intent(this,AddActivity.class);
        intent.putExtra("PARTYID", code);
        startActivity(intent);
    }

    public void refresh() {
        loadQueue();
    }


    //Temp Colour Change Activity
    private void ChangeColour(){
        Intent i = new Intent(HostActivity.this, ChangeColourActivity.class);
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
    /*@Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        finish();
        Intent i = new Intent(HostActivity.this, HostActivity.class);
        startActivity(i);
    }*/

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
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }


}
