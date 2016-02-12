package ie.tcd.scss.q_dj;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ed on 29/01/16.
 */
public class ServerComms {

    public static final String base_server_url = "http://217.78.0.111/~tuneq";
    public static final String addSongPHP = "/addSong.php";
    public static final String deleteSongPHP = "/deleteSong.php";
    public static final String createPartyPHP = "/createParty.php";
    public static final String joinPartyPHP = "/joinParty.php";
    public static final String getQueuePHP = "/getQueue.php";

    public ServerComms(){}

    public static JSONObject getQueue(String partyID) throws IOException {
        HashMap<String, String> req = new HashMap<>();
        req.put("partID", partyID);
        return new HTTPRequest().get(base_server_url + getQueuePHP, req);
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
        req.put("partID", partyID);
        req.put("songID", songID);

        new HTTPRequest().get(base_server_url + deleteSongPHP, req);
    }


    public void createParty(String userID, String partyID) throws IOException {
        HashMap<String, String> req = new HashMap<>();
        req.put("user_id", userID);
        req.put("partID", partyID);

        new HTTPRequest().get(base_server_url + createPartyPHP, req);
    }

    public void joinParty(String userID, String partyID) throws IOException {
        HashMap<String, String> req = new HashMap<>();
        req.put("user_id", userID);
        req.put("partID", partyID);

        new HTTPRequest().get(base_server_url + joinPartyPHP, req);
    }
}
