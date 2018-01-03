package tk.dinud11.worddb;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.DatabaseHelper;
import model.Word;
import tk.dinud11.worddb.MainActivity;

/**
 * Created by dinud11 on 12/26/17.
 */

public class WordRequesst {

    private static JSONObject mresponse;

    public static JSONObject makeGETRequest(final DatabaseHelper databaseHelper) {
        String url = "http://dinud11.tk/cuvinte/api/word/";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mresponse = response;
                        parseJSON(mresponse, databaseHelper);
                        Log.v("ayy", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("reqerror", error.toString());
            }
        });

        MainActivity.queue.add(getRequest);
        return mresponse;
    }

    public static void parseJSON(JSONObject response, DatabaseHelper databaseHelper) {
        try {
            // Get the array containing all the word objects
            JSONArray objects = response.getJSONArray("objects");
            //JSONObject meta = response.getJSONObject("meta");

            for (int i = 0; i < objects.length(); i++) {
                // for each word object get it's properties and save them
                Word word = new Word();
                JSONObject wordObj = objects.getJSONObject(i);
                word.setWord(wordObj.getString("word"));
                word.setDefinition(wordObj.getString("definition"));
                word.setCategory(wordObj.getString("category"));
                word.setType(wordObj.getString("word_type"));
                word.setId(wordObj.getInt("id"));

                if(!databaseHelper.checkIfExists(word)) {
                    databaseHelper.addWord(word);
                } else
                    Log.v("word", "word "+word.getWord()+" already exists");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void makePOSTRequest(JSONObject req) {
        String url = "http://dinud11.tk/cuvinte/api/word/";
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, req,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MainActivity.queue.add(postRequest);
    }

    static void makeDELETERequest(JSONObject req, final DatabaseHelper databaseHelper, final Word word) {
        String url = "http://dinud11.tk/cuvinte/api/word/";
        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.DELETE, url, req,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                databaseHelper.deleteWord(word);
            }
        });
        MainActivity.queue.add(deleteRequest);
    }

    static void makePUTRequest(JSONObject req, final Word word, final DatabaseHelper databaseHelper) {
        String url = "http://dinud11.tk/cuvinte/api/word/"+word.getId()+"/";
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, req,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("put", word.getWord());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                databaseHelper.updateWord(word);
            }
        });
        MainActivity.queue.add(putRequest);
    }
}
