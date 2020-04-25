package com.example.testinglistview;

import androidx.appcompat.app.AppCompatActivity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    protected static final String API_URL = "https://api.rootnet.in/covid19-in/stats/latest";
   private DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listview);


//
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get("https://api.rootnet.in/covid19-in/stats/latest", new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statuCode, Header[] headers, final JSONObject response) {
//
//                object = response;
//                Log.d("testing", "onSucess: " + object.toString());
//                try {
//                    String nee = object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc");
//                    arrayList.add(new States(object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc"), object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getInt("totalConfirmed")));
//                    arrayList.add(new States(object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc"), object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getInt("totalConfirmed")));
//                    arrayList.add(new States(object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc"), object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getInt("totalConfirmed")));
//                    arrayList.add(new States(object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc"), object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getInt("totalConfirmed")));
//                    arrayList.add(new States(object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc"), object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getInt("totalConfirmed")));
////            DataAdapter dataAdapter = new DataAdapter(this,arrayList);
////            lv.setAdapter(dataAdapter);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
//                //  called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                Log.d("testing", "Request fail! Status code: " + statusCode);
//                Log.d("testing", "Fail response: " + response);
//                Log.e("testing", e.toString());
//            }
//
//        });
//
////        try {
////            String nee = object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc");
////            arrayList.add(new States(object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc"), object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getInt("totalConfirmed")));
////            arrayList.add(new States(object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc"), object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getInt("totalConfirmed")));
////            arrayList.add(new States(object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc"), object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getInt("totalConfirmed")));
////            arrayList.add(new States(object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc"), object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getInt("totalConfirmed")));
////            arrayList.add(new States(object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getString("loc"), object.getJSONObject("data").getJSONArray("regional").getJSONObject(0).getInt("totalConfirmed")));
//////            DataAdapter dataAdapter = new DataAdapter(this,arrayList);
//////            lv.setAdapter(dataAdapter);
////        } catch (JSONException e) {
////            e.printStackTrace();
//
//            arrayList.add(new States("ajeet", 8));
//        arrayList.add(new States("ajeet",6 ));
//
//        arrayList.add(new States("ajeet",6 ));
//        arrayList.add(new States("ajeet",6 ));
////          }
////     arrayList.add(new States("ajeet",6 ));
        ArrayList<States> arrayList = new ArrayList<States>();
         dataAdapter = new DataAdapter(this, arrayList);
        lv.setAdapter(dataAdapter);
        StatesAsyncTask Task = new StatesAsyncTask();
        Task.execute(API_URL);
    }

    private class StatesAsyncTask extends AsyncTask<String, Void, ArrayList<States>>
    {

        @Override
        protected ArrayList<States> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            ArrayList<States> result = fetchStateData(urls[0]);
            return result;

        }
        protected void onPostExecute(ArrayList<States> data) {
            // Clear the adapter of previous earthquake data
            dataAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                dataAdapter.addAll(data);
            }
        }
    }




    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("testing", "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("testing", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("testing", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<States> extractFeatureFromJson(String response) {
        ArrayList<States> list = new ArrayList<States>();
        try {
            JSONObject jsonresponse = new JSONObject(response);
            JSONObject jsonObjectdata = jsonresponse.getJSONObject("data");
            JSONArray jsonArray = jsonObjectdata.getJSONArray("regional");
            final int max = jsonArray.length();

            for (int i = 0; i < max; i++) {
                JSONObject currentStatesResponse = jsonArray.getJSONObject(i);
                String loca = currentStatesResponse.getString("loc");
                int totalCase = currentStatesResponse.getInt("totalConfirmed");
                States states = new States(loca, totalCase);
                list.add(states);


            }


        } catch (JSONException e) {
            Log.e("testing", "extractFeatureFromJson: ", e);
        }

        return list;
    }

    public static ArrayList<States> fetchStateData(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("testing", "Problem making the HTTP request.", e);
        }
        ArrayList<States> statess = extractFeatureFromJson(jsonResponse);
        return statess;
    }
}


