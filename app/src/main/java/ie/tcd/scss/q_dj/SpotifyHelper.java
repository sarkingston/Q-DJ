package ie.tcd.scss.q_dj;

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
public class SpotifyHelper {

    String spotify_base_url = "https://api.spotify.com";
    String spotify_client_id = "5929ca64d00d4649a44f21dc487dd094";
    String spotify_client_secret = "aaa0ba3984ad455d8988044b29212221";

    public void SpotifyHelper(){
        //Do Nothing..
    }

    public ArrayList<Song> query(String q, String type) throws IOException, JSONException {
        HashMap<String, String> req = new HashMap<String, String>();
        req.put("type", type);
        req.put("q", q.replace(" ", "+"));
        String endpoint = "/v1/search";
        JSONObject  search_result = new HTTPRequest().get(spotify_base_url + endpoint, req);

        JSONArray tracks = search_result.getJSONObject("tracks").getJSONArray("items");

        ArrayList<Song> songs = new ArrayList<Song>();

        for(int i =0; i<tracks.length(); i++ ){
            JSONObject t = tracks.getJSONObject(i);
            Song track = new Song(t.getString("name"),
                    t.getJSONArray("artists").getJSONObject(0).getString("name"),
                    Double.parseDouble(t.getString("duration_ms"))/1000,
                    t.getString("id")
                    );
            songs.add(track);
            //Log.e("SONGS", track.title + " " + track.artist + " " + track.duration);
        }

        return songs;
    }
}
