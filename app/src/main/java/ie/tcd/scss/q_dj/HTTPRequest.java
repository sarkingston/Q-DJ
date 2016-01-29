package ie.tcd.scss.q_dj;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Darragh on 29/01/2016.
 */
public class HTTPRequest {


    static InputStream is = null;
    static JSONObject jObj = null;
    static JSONArray jArr = null;
    static String json = "";

    /*
        HashMap<String, String> p = new HashMap<String, String>();
        p.put("key_1", "value_1");
        p.put("key_2", "value_2");

        url is the base urls without any paramters that is to be sent


     */
    public JSONObject get(String url,HashMap<String, String> params ) throws IOException {

        //HashMap<String, String> h1 = new HashMap<String, String>();

        HttpURLConnection c = null;
        try {

            //Structuring the URL
            int i = 0;
            Iterator it = params.entrySet().iterator();{
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    //System.out.println(pair.getKey() + " = " + pair.getValue());
                    if(i==0){
                        url+= "?" + pair.getKey() + "=" + pair.getValue();
                    }
                    else{
                        url+= "&" + pair.getKey() + "=" + pair.getValue();
                    }
                    i++;
                    it.remove(); // avoids a ConcurrentModificationException
                }
            }
            URL url_obj = new URL(url );
            c = (HttpURLConnection) url_obj.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.connect();
            int status = c.getResponseCode();

            Writer writer = new StringWriter();

            switch (status) {
                case 200:
                case 201:
                    char[] buffer = new char[1024];
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    int n;
                    while ((n = br.read(buffer)) != -1) {
                        //sb.append(n);
                        writer.write(buffer, 0, n);
                    }
                    br.close();
                    Log.d("Response", writer.toString());
                    JSONObject j = new JSONObject(writer.toString());
                    return j;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        Log.d("JSONParse", jObj.toString());
        // return JSON String
        return jObj;
    }
}
