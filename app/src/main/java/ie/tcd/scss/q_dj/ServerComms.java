package ie.tcd.scss.q_dj;

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

    public static final String base_server_url = "http://217.78.0.111/~tuneq";
    public static final String addSongPHP = "/addSong.php";
    public static final String deleteSongPHP = "/deleteSong.php";
    public static final String createPartyPHP = "/createParty.php";
    public static final String joinPartyPHP = "/joinParty.php";
    public static final String getQueuePHP = "/getQueue.php";

    public ServerComms(){}

    public static ArrayList<Song> getQueue(String partyID) throws IOException, JSONException {
        HashMap<String, String> req = new HashMap<>();
        req.put("partID", partyID);
        JSONObject jsonObject = new HTTPRequest().get(base_server_url + getQueuePHP, req);
        System.out.println(jsonObject);
        ArrayList<Song> songs_list = new ArrayList<Song>();
        if(jsonObject.get("status")=="true"){
            JSONArray songs_obj = jsonObject.getJSONArray("songs");
            for(int i =0; i< songs_obj.length(); i++){
                JSONObject song_x = songs_obj.getJSONObject(i);

                songs_list.add(new Song(
                        song_x.getString("songtitle"),
                        song_x.getString("artist"),
                        Double.parseDouble(song_x.getString("songlength")),
                        song_x.getString("spotifyID")
                        ));
            }

            return  songs_list;
        }

        return  null;

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

    public boolean joinParty(String userID, String partyID) throws IOException, JSONException {
        HashMap<String, String> req = new HashMap<>();
        req.put("user_id", userID);
        req.put("partyID", partyID);


        //Temporairly Disable Joining - Cause Server isn't ready yet
       /*JSONObject result =  new HTTPRequest().get(base_server_url + joinPartyPHP, req);

        if(result.getString("status") == "true"){
            return  true;
        }
        else{
            return  false;
        }
        */
        return true;
    }
}
