package ie.tcd.scss.q_dj;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ed on 29/01/16.
 */
public class SpotifyHelper {

    String spotify_base_url = "https://api.spotify.com";


    public JSONObject query(String query, String type) throws IOException {
        HashMap<String, String> req = new HashMap<String, String>();
        req.put("type", type);
        req.put("q", query);
        String endpoint = "/v1/search";
        JSONObject  search_result = new HTTPRequest().get(spotify_base_url + endpoint, req);

        return search_result;
    }
}
