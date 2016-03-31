package ie.tcd.scss.q_dj;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.PlayerStateCallback;
import com.spotify.sdk.android.player.Spotify;

/**
 * Created by john on 17/02/16.
 */
public class MusicPlayer extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    Song song1 = new Song("", "", 0.0, "2zMoMsf7KtqCQvlNfdFKtW");
    Song song2 = new Song("", "", 0.0, "5DBQWDGt7WVlyMgMgvGko9");
    Song song3 = new Song("", "", 0.0, "5blEjbK0DUQBxggguyKsEP");
    Song[] songList  = {song1, song2, song3}; //Comms can put the list of song id's in here

    int songNumber = 0;
    int playing = 0;

    private Player mPlayer;
    Config playerConfig;

    public void addConfig(Config _playerConfig) {
        playerConfig = _playerConfig;
    }

    public void replace(Song[] list)
    {
        songList = list;
    };

    public void play() {
        Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
            @Override
            public void onInitialized(Player player) {
                mPlayer = player;
                mPlayer.addConnectionStateCallback(MusicPlayer.this);
                mPlayer.addPlayerNotificationCallback(MusicPlayer.this);
                if (playing == 0)
                {
                    mPlayer.play("spotify:track:" + songList[songNumber].getSpotifyID());
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
                }

            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });
    }

    public void skipNext() {
        if (songNumber < songList.length) {
            songNumber++;
            playing = 0;
            play();
            //mPlayer.pause();
            //mPlayer.resume()
            //mPlayer.playing();
        }
    }

    public void prevSong() {
        if (songNumber > 0) {
            songNumber--;
            playing = 0;
            play();
        }
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
}