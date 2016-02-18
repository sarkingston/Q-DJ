package ie.tcd.scss.q_dj;

import android.annotation.TargetApi;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ed on 29/01/16.
 */
public class ServerComms {

    public static final String base_server_url = "http://tuneq.2digital.ie";
    public static final String addSongPHP = "/addsong.php";
    public static final String deleteSongPHP = "/deletesong.php";
    public static final String createPartyPHP = "/createparty.php";
    public static final String joinPartyPHP = "/joinparty.php";
    public static final String getQueuePHP = "/getqueue.php";

    public ServerComms() {}

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static JSONArray getQueue(String partyID) throws IOException {
        HashMap<String, String> req = new HashMap<>();
        req.put("partyid", partyID);
        JSONObject jsonObject = new HTTPRequest().get(base_server_url + getQueuePHP, req);

        try {
            return new JSONArray(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addSong(String userID,
                        String partyID,
                        String songID,
                        String title,
                        String artist,
                        long duration) throws IOException {
        //Get Timestamp and Add it
        String timestamp = Long.toString(System.currentTimeMillis() / 1000L);

        HashMap<String, String> req = new HashMap<>();
        req.put("user_id", userID);
        req.put("partID", partyID);
        req.put("songID", songID);
        req.put("title", title);
        req.put("artist", artist);
        req.put("duration", Long.toString(duration));
        req.put("timestamp", timestamp);

        new HTTPRequest().get(base_server_url + addSongPHP, req);
    }

    public void deleteSong(String userID, String partyID, String songID) throws IOException {
        HashMap<String, String> req = new HashMap<>();
        req.put("user_id", userID);
        req.put("partyID", partyID);
        req.put("spotifyID", songID);

        new HTTPRequest().get(base_server_url + deleteSongPHP, req);
    }


    public void createParty(String userID, String partyID) throws IOException {
        HashMap<String, String> req = new HashMap<>();
        req.put("user_id", userID);
        req.put("partyID", partyID);

        new HTTPRequest().get(base_server_url + createPartyPHP, req);
    }

    public void joinParty(String userID, String partyID) throws IOException {
        HashMap<String, String> req = new HashMap<>();
        req.put("user_id", userID);
        req.put("partyID", partyID);

        new HTTPRequest().get(base_server_url + joinPartyPHP, req);
    }
}
