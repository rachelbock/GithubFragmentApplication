package edu.lclark.githubfragmentapplication;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rage on 3/3/16.
 */
public class LoginAsyncTask extends AsyncTask<String, Integer, JSONObject> {

    public static final String TAG = LoginAsyncTask.class.getSimpleName();

    public LoginAsyncTask(gitHubLoginUserListener listener) {
        this.listener = listener;
    }

    public interface gitHubLoginUserListener {
        void onGitHubLoginUserRetrieved(@Nullable JSONObject jsonObject);
    }
    private final gitHubLoginUserListener listener;


    @Override
    protected JSONObject doInBackground(String... params) {

        StringBuilder builder = new StringBuilder();
        JSONObject json = null;

        if (params.length == 0) {
            return null;
        }

        String userName = params[0];

        try {
            URL url = new URL("https://api.github.com/users/" + userName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);

                if (isCancelled()) {
                    return null;
                }

            }

            json = new JSONObject(builder.toString());


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        if (jsonObject == null) {
            Log.d(TAG, "The json object is null");
            listener.onGitHubLoginUserRetrieved(null);
        }
        else {
            listener.onGitHubLoginUserRetrieved(jsonObject);
        }
    }
}
