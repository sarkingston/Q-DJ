package ie.tcd.scss.q_dj;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.PlayerStateCallback;
import com.spotify.sdk.android.player.Spotify;

import java.util.ArrayList;



/**
 * Created by john on 17/02/16.
 */
public class MusicPlayer extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    Song song1 = new Song("", "", 50.0, "2zMoMsf7KtqCQvlNfdFKtW", "");
    Song song2 = new Song("", "", 0.0, "5DBQWDGt7WVlyMgMgvGko9", "");
    Song song3 = new Song("", "", 0.0, "5blEjbK0DUQBxggguyKsEP", "");
    Song[] songList  = {song1, song2, song3}; //Comms can put the list of song id's in here
    ArrayList<String> URIs = new ArrayList();

    int songNumber = 0;
    int playing = 0;

    private Player mPlayer;



    public void initialise(Config playerConfig) {
        Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
            @Override
            public void onInitialized(Player player) {
                System.out.println("INITIALISNG");
                mPlayer = player;
                mPlayer.addConnectionStateCallback(MusicPlayer.this);
                mPlayer.addPlayerNotificationCallback(MusicPlayer.this);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });
    }

    public void replace(ArrayList<Song> List){
        if (mPlayer != null){
            Log.d("MainActivity", "CLEARING QUEUE");
            mPlayer.clearQueue();

            ArrayList<String> temp = new ArrayList();


            for (int i = URIs.size(); i < List.size(); i++) {
                URIs.add(i, "spotify:track:"+ List.get(i).getSpotifyID());
                mPlayer.queue(URIs.get(i));
                System.out.println("TRACK URI:" + List.get(i).getSpotifyID());
            }

        }
    }




    public void play() {
        if (playing == 0)
        {
            System.out.println("PLAYING: " + URIs);
            mPlayer.play(URIs);
            playing = 2;
        }
        else if (playing == 1)
        {
            mPlayer.resume();
            playing = 2;
        }
        else if (playing == 2)
        {
            mPlayer.pause();
            playing = 1;
        };
    }

    public void skipNext() {
        mPlayer.skipToNext();
            //mPlayer.pause();
            //mPlayer.resume()
            //mPlayer.playing();

    }

    public void prevSong() {
        mPlayer.skipToPrevious();
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

    public double getSongDuration() {
        //double dur =  song1.getDuration();
        double dur = songList[songNumber].getDuration();
        return dur;
    }


}