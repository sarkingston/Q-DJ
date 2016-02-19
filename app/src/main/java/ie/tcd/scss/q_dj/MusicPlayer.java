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

    String[] songList  = {"2zMoMsf7KtqCQvlNfdFKtW", "5DBQWDGt7WVlyMgMgvGko9", "5blEjbK0DUQBxggguyKsEP"}; //Comms can put the list of song id's in here

    int songNumber = 0;

    private Player mPlayer;
    Config playerConfig;

    public void addConfig(Config _playerConfig) {
        playerConfig = _playerConfig;
    }

    public void play() {
        Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
            @Override
            public void onInitialized(Player player) {
                mPlayer = player;
                mPlayer.addConnectionStateCallback(MusicPlayer.this);
                mPlayer.addPlayerNotificationCallback(MusicPlayer.this);
                mPlayer.play("spotify:track:" + songList[songNumber]);
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
            play();
        }
    }

    public void prevSong() {
        if (songNumber > 0) {
            songNumber--;
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