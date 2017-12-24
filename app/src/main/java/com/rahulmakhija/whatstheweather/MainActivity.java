    package com.rahulmakhija.whatstheweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

    public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);

        DownloadWebsite downloadWebsite = new DownloadWebsite();
        try {
            String result = downloadWebsite.execute("http://api.openweathermap.org/data/2.5/weather?q=Bangalore&appid=b629d61c3883de621e8fb5a8ff111b30&units=metric").get();
            Log.i("Result", ""+result);

            JSONObject jsonObject = new JSONObject(result);
            JSONArray weather = jsonObject.getJSONArray("weather");
            JSONObject firstObject = weather.getJSONObject(0);
            String description = firstObject.getString("description");
            Log.i("Description", description);

            JSONObject main = jsonObject.getJSONObject("main");
            int temperature = main.getInt("temp");
            Log.i("Temperature", "" + temperature);

            textView.setText("Bangalore's Temperature = " + temperature + " and the weather is " + description);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Result", "Failure");
        }
    }

    public class DownloadWebsite extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {

            URL url;
            String result="";
            HttpURLConnection urlConnection;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();
                while (data != -1){
                    char c = (char) data;
                    result += c;
                    data = reader.read();

                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
