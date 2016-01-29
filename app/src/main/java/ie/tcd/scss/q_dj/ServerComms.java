package ie.tcd.scss.q_dj;

import org.json.JSONArray;

/**
 * Created by ed on 29/01/16.
 */
public class ServerComms {

    public ServerComms(){}
    public static JSONArray getQueue(String partyID){return null;}
    public void addSong(String ID, String partyID,
                               String songID, String title, String artist,
                               long duration, long timestamp){}
    public void deleteSong(String userID, String partyID, String songID){}
}
