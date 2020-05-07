package com.example.testinglistview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;


import android.content.Context;
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
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    ListView lv;
    protected static final String API_URL = "https://api.rootnet.in/covid19-in/stats/latest";
    private DataAdapter dataAdapter;
    private MyViewModel myViewModel;
    ArrayList<States> info;
    ArrayList<States> info2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("ajeet", "onCreate: called");
        lv = (ListView) findViewById(R.id.listview);
//        Repository repo = new Repository();
//        repo.asyn();
//        info = new ArrayList<States>();
//        dataAdapter = new DataAdapter(this, info);
//        lv.setAdapter(dataAdapter);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
//        myViewModel.init();
//        info = (ArrayList<States>) myViewModel.getStates().getValue();
//        info = new ArrayList<States>();
//        dataAdapter = new DataAdapter(this, info);
//        lv.setAdapter(dataAdapter);
        myViewModel.getStates().observe(this, new Observer<List<States>>() {
            @Override
            public void onChanged(List<States> states) {
                // update UI
                dataAdapter = new DataAdapter(MainActivity.this, (ArrayList<States>) states);

//                info.clear();
                lv.setAdapter(dataAdapter);
//                info.addAll(states);
                ;
//                dataAdapter.addAll(states);
//                dataAdapter.notifyDataSetChanged();

            }
        });
//        info = new ArrayList<States>();
//        dataAdapter = new DataAdapter(this, info);
//        lv.setAdapter(dataAdapter);
//          info = (ArrayList<States>) myViewModel.getStates().getValue();
//        Log.d("ajeet", "onCreate: "+info);
//        for (int i =0;i<=info.size();i++)
//        {
//            Log.i("ajeet", "onCreate: " + info.get(i));
//        }
//        dataAdapter = new DataAdapter(this, info);
//        lv.setAdapter(dataAdapter);
//        StatesAsyncTask task = new StatesAsyncTask();
//        task.execute(API_URL);
//        // now loaders have been deprecreted sine the release of android jetpack so we can use view models class instead of loaders
//    }
    }
}
//        class StatesAsyncTask extends AsyncTask<String, Void, ArrayList<States>> {
//
//            @Override
//            protected ArrayList<States> doInBackground(String... urls) {
//                if (urls.length < 1 || urls[0] == null) {
//                    return null;
//                }
//
//                ArrayList<States> result = fetchStateData(urls[0]);
//                return result;
//
//            }
//
//            protected void onPostExecute(ArrayList<States> data) {
//                // Clear the adapter of previous earthquake data
//                dataAdapter.clear();
//
//                // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//                // data set. This will trigger the ListView to update.
//                if (data != null && !data.isEmpty()) {
//                    dataAdapter.addAll(data);
//                }
//            }
//        }
//
//
//        private static URL createUrl (String stringUrl){
//            URL url = null;
//            try {
//                url = new URL(stringUrl);
//            } catch (MalformedURLException e) {
//                Log.e("testing", "Problem building the URL ", e);
//            }
//            return url;
//        }
//
//        private static String makeHttpRequest (URL url) throws IOException {
//            String jsonResponse = "";
//
//            // If the URL is null, then return early.
//            if (url == null) {
//                return jsonResponse;
//            }
//
//            HttpURLConnection urlConnection = null;
//            InputStream inputStream = null;
//            try {
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setReadTimeout(10000 /* milliseconds */);
//                urlConnection.setConnectTimeout(15000 /* milliseconds */);
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                // If the request was successful (response code 200),
//                // then read the input stream and parse the response.
//                if (urlConnection.getResponseCode() == 200) {
//                    inputStream = urlConnection.getInputStream();
//                    jsonResponse = readFromStream(inputStream);
//                } else {
//                    Log.e("testing", "Error response code: " + urlConnection.getResponseCode());
//                }
//            } catch (IOException e) {
//                Log.e("testing", "Problem retrieving the earthquake JSON results.", e);
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (inputStream != null) {
//                    // Closing the input stream could throw an IOException, which is why
//                    // the makeHttpRequest(URL url) method signature specifies than an IOException
//                    // could be thrown.
//                    inputStream.close();
//                }
//            }
//            return jsonResponse;
//        }
//
//        private static String readFromStream (InputStream inputStream) throws IOException {
//            StringBuilder output = new StringBuilder();
//            if (inputStream != null) {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
//                BufferedReader reader = new BufferedReader(inputStreamReader);
//                String line = reader.readLine();
//                while (line != null) {
//                    output.append(line);
//                    line = reader.readLine();
//                }
//            }
//            return output.toString();
//        }
//
//        private static ArrayList<States> extractFeatureFromJson (String response){
//            ArrayList<States> list = new ArrayList<States>();
//            try {
//                JSONObject jsonresponse = new JSONObject(response);
//                JSONObject jsonObjectdata = jsonresponse.getJSONObject("data");
//                JSONArray jsonArray = jsonObjectdata.getJSONArray("regional");
//                final int max = jsonArray.length();
//
//                for (int i = 0; i < max; i++) {
//                    JSONObject currentStatesResponse = jsonArray.getJSONObject(i);
//                    String loca = currentStatesResponse.getString("loc");
//                    int totalCase = currentStatesResponse.getInt("totalConfirmed");
//                    States states = new States(loca, totalCase);
//                    list.add(states);
//
//
//                }
//
////
//            } catch (JSONException e) {
//                Log.e("testing", "extractFeatureFromJson: ", e);
//            }
//
//            return list;
//        }
//
//        public static ArrayList<States> fetchStateData (String requestUrl){
//
//            URL url = createUrl(requestUrl);
//            String jsonResponse = null;
//            try {
//                jsonResponse = makeHttpRequest(url);
//            } catch (IOException e) {
//                Log.e("testing", "Problem making the HTTP request.", e);
//            }
//            ArrayList<States> statess = extractFeatureFromJson(jsonResponse);
//            return statess;
//////        }
//        }
//    }



