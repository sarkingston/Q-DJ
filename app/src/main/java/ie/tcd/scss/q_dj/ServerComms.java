package ie.tcd.scss.q_dj;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by ed on 29/01/16.
 */
public class ServerComms {

    static String base_server_url = "base_server_url_goes_here";       //This will change when we know

    public ServerComms(){


    }
    public static JSONArray getQueue(String partyID) throws IOException {

        HashMap<String, String> req = new HashMap<String, String>();
        req.put("partid", partyID);
        String endpoint = "/getQueue.php";            //This will have to Change when we get told how it's done
        JSONObject queue_obj = new HTTPRequest().get(base_server_url + endpoint, req);

        return null;
    }
    public boolean addSong(String userID,
                        String partyID,
                        String songID,
                        String title,
                        String artist,
                        long duration) throws IOException {
        //Get Timestamp and Add it
        String timestamp =  Long.toString(System.currentTimeMillis() / 1000L);

        HashMap<String, String> req = new HashMap<String, String>();
        req.put("userID", userID);
        req.put("partyid", partyID);
        req.put("spotifyID", songID);
        req.put("songtitle", title);
        req.put("artist", artist);
        req.put("songlength", Long.toString(duration));
        req.put("timesent", timestamp);
        String endpoint = "/addSong.php";            //This will have to Change when we get told how it's done
        JSONObject result = new HTTPRequest().get(base_server_url + endpoint, req);

        if(result != null)
            return true;
        else
            return false;

    }
    public boolean deleteSong(String userID, String partyID, String songID) throws IOException {
        HashMap<String, String> req = new HashMap<String, String>();
        req.put("user_id", userID);
        req.put("partID", partyID);
        req.put("songID", songID);

        String endpoint = "/deleteSong.php";            //This will have to Change when we get told how it's done
        JSONObject result = new HTTPRequest().get(base_server_url + endpoint, req);

        if(result != null)
            return true;
        else
            return false;
    }


    public boolean createParty(String userID, String partyID)throws IOException {

        HashMap<String, String> req = new HashMap<String, String>();
        req.put("user_id", userID);
        req.put("partID", partyID);

        String endpoint = "/createParty.php";            //This will have to Change when we get told how it's done
        JSONObject result = new HTTPRequest().get(base_server_url + endpoint, req);

        if(result != null)
            return true;
        else
            return false;
    }

    public boolean joinParty(String userID, String partyID)throws IOException {

        HashMap<String, String> req = new HashMap<String, String>();
        req.put("user_id", userID);
        req.put("partID", partyID);

        String endpoint = "/joinParty.php";            //This will have to Change when we get told how it's done
        JSONObject result = new HTTPRequest().get(base_server_url + endpoint, req);

        if(result != null)
            return true;
        else
            return false;
    }
}
