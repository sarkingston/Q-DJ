package ie.tcd.scss.q_dj;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ed on 29/01/16.
 */
public class ServerComms {

    public static final String base_server_url = "http://tuneq.2digital.ie";
    public static final String addSongPHP = "/addsong.php";
    public static final String deleteSongPHP = "/deletesong.php";
    public static final String createPartyPHP = "/createQ.php";
    public static final String joinPartyPHP = "/adduser.php";
    public static final String getQueuePHP = "/getqueue.php";

    public ServerComms() {
    }

    public static ArrayList<Song> getQueue(String partyID) throws IOException, JSONException {
        HashMap<String, String> req = new HashMap<>();
        req.put("partyid", partyID);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        JSONObject httpJson = new HTTPRequest().get(base_server_url + getQueuePHP, req);
        ArrayList<Song> songs_list = new ArrayList<>();

        String title, artist, id, timeSent, userID;
        Double duration;

        if(httpJson.get("status").equals("true")){
            JSONArray songs_obj = httpJson.getJSONArray("songs");
            for(int i =0; i< songs_obj.length(); i++){
                JSONObject song_x = songs_obj.getJSONObject(i);

                title = song_x.optString("songtitle");
                artist = song_x.optString("artist");
                id = song_x.optString("spotifyID");
                timeSent = song_x.optString("timesent");
                userID = song_x.optString("userID");
                duration = Double.parseDouble(song_x.getString("songlength"));

                System.out.println(title + " by " + artist + " with ID " + id +
                        " with length " + duration + " was sent by " + userID + " at " + timeSent);

                songs_list.add(new Song(title, artist, duration, id));
            }
            return songs_list;
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


    public boolean createParty(String userID, String partyID) throws IOException, JSONException {
        HashMap<String, String> req = new HashMap<>();
        req.put("userID", userID);
        req.put("partyid", partyID);

        JSONObject result =  new HTTPRequest().get(base_server_url + createPartyPHP, req);
        Log.d("SERVERCOMMS",result.getString("status").replace(" ","") );
        if(result.get("status").equals("true")){
            Log.d("SERVERCOMMS", "RETURNING TRUE");
            return  true;
        }

        return false;
    }

    public boolean joinParty(String userID, String partyID) throws IOException, JSONException {
        HashMap<String, String> req = new HashMap<>();
        req.put("userID", userID);
        req.put("partyid", partyID);



       JSONObject result =  new HTTPRequest().get(base_server_url + joinPartyPHP, req);
        Log.d("SERVERCOMMS",result.getString("status").replace(" ","") );
        if(result.get("status").equals("true")){
            Log.d("SERVERCOMMS", "RETURNING TRUE");
            return  true;
        }

        return false;
    }
}
