package otk.test;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Andrew on 12/3/2015.
 */
public class DeleteEventTask extends AsyncTask<String, Void, Boolean> {
    // http://findme-env.elasticbeanstalk.com/deleteevent.php

    String id = "";

    public DeleteEventTask(String id) {
        this.id = id;
    }

    @Override
    protected Boolean doInBackground(String... url) {
        try {
            URL urlname = new URL(url[0]);
            HttpURLConnection conn = (HttpURLConnection) urlname.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //conn.setRequestProperty("charset", "utf-8");
            //conn.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            //conn.setUseCaches(false);
            conn.setChunkedStreamingMode(0);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id", id);

            wr.writeBytes(jsonParam.toString());

            wr.flush();
            wr.close();

            conn.disconnect();

        } catch (MalformedURLException e) {
            Log.e("MalformedURL", e.getMessage());
            return false;
        } catch (IOException e) {
            Log.e("IOException", e.getMessage());
            return false;
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
            return false;
        }

        return true;
    }

    protected void onPostExecute(Boolean success) {
        if (success) {
            Log.e("DeleteEvent","Success");
        }
        else {
            Log.e("DeleteEvent","Failure");
        }
    }
}
