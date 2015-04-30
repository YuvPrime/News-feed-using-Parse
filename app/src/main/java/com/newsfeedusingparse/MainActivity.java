package com.newsfeedusingparse;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SendCallback;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    List<ParseObject> parseObjects;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        new LoadPosts().execute();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoadPosts extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Posts");
            query.orderByAscending("createdAt");

            try {
                parseObjects = query.find();
            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("Error : ", e.getMessage());
            }

            for (ParseObject parseObject : parseObjects) {
                Log.d("track", parseObject.getString("postContent"));
                Log.d("track", String.valueOf(parseObject.getCreatedAt()));

                Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Log.d("track",formatter.format(parseObject.getCreatedAt()));

                ParseFile image = (ParseFile) parseObject.get("image");


                if (image!=null) {

                    Log.d("track", image.getUrl());
                }


            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }


}
