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
import android.widget.SeekBar;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sam on 24/2/16.
 */
public class HostActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //saved themes from changeColour
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    //icons
    final int idPlay = R.mipmap.ic_play_arrow_white_24dp;
    final int idPause = R.mipmap.ic_pause_white_24dp;

    //fabs
    com.getbase.floatingactionbutton.FloatingActionsMenu player;
    com.getbase.floatingactionbutton.FloatingActionButton play;
    com.getbase.floatingactionbutton.FloatingActionButton previous;
    com.getbase.floatingactionbutton.FloatingActionButton next;

    boolean playActive;
    String userMode, code, token;

    //initialse spotify player variable
    MusicPlayer musicPlayer;
    SeekBar seekBar;
    int SBCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        Intent i = getIntent();
        token = i.getStringExtra("token");
        Toast.makeText(HostActivity.this, "Log in successful", Toast.LENGTH_LONG).show();

        // Use the chosen theme
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if (useDarkTheme) {
            setTheme(R.style.AppTheme2);
        } else {
            setTheme(R.style.AppTheme);
        }

        Bundle received = getIntent().getExtras();
        userMode = received.getString("USERMODE");
        code = received.getString("PARTYID");

        player = (com.getbase.floatingactionbutton.FloatingActionsMenu) findViewById(R.id.multiple_actions);
        play = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.play);
        previous = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.skip_previous);
        next = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.skip_next);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        musicPlayer = new MusicPlayer(this);
        musicPlayer.addConfig(new Config(this, token, LoginScreen.CLIENT_ID));

        if (userMode.equals("guest")) {
            player.setVisibility(View.INVISIBLE);
            play.setVisibility(View.INVISIBLE);
            previous.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
            seekBar.setVisibility(View.INVISIBLE);
        }

        play.setIcon(idPlay);
        playActive = true;
        loadQueue();

        getInit();
        seekBar.setProgress(SBCounter);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("playing");
                if (playActive) {
                    play.setIcon(idPause);
                    musicPlayer.play();
                    playActive = false;
                    seekUpdate();
                } else {
                    play.setIcon(idPlay);
                    musicPlayer.play();
                    playActive = true;
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.prevSong();
                playActive = false;
                play.setIcon(idPause);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicPlayer.skipNext();
                playActive = false;
                play.setIcon(idPause);
            }
        });
    }

    protected void loadQueue() {
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
        if (userMode == "guest") {
            player.setVisibility(View.INVISIBLE);
            play.setVisibility(View.INVISIBLE);
            previous.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
            seekBar.setVisibility(View.INVISIBLE);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_leave_host:
                LeaveHost();
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

    private void LeaveHost() {
        Intent i = new Intent(this, JoinPlaylist.class);
        startActivity(i);
        finish();
    }

    public void add(String code) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("PARTYID", code);
        startActivity(intent);
    }

    public void refresh() {
        loadQueue();
    }


    //Temp Colour Change Activity
    private void ChangeColour() {
        Intent i = new Intent(HostActivity.this, ChangeColourActivity.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    public void getInit() {
        //seekBar = (SeekBar)findViewById(R.id.seekBar);
        //double dDuration = mPlayer.getSongDuration();
        //int duration = (int)dDuration;
        //seekBar.setMax(duration);
        //seekBar.setMax(50);

        // int percentageUpdate = (duration/100);
    }

    public void seekUpdate() {
        //SBCounter++;
        //seekBar.setProgress(SBCounter);
        final int delay = 1000; // delay for 5 sec.
        final int period = 1000; // repeat every sec.


        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (playActive) {
                    timer.cancel();
                } else {
                    SBCounter++;
                    seekBar.setProgress(SBCounter);
                }
            }
        }, delay, period);
    }
}
