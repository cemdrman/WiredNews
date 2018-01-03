package com.example.cemdirman.comkonusarakogren.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cemdirman.comkonusarakogren.R;
import com.example.cemdirman.comkonusarakogren.adapters.ListViewAdapter;
import com.example.cemdirman.comkonusarakogren.model.News;
import com.example.cemdirman.comkonusarakogren.utility.JsonParse;
import com.example.cemdirman.comkonusarakogren.utility.RequestURL;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    final JsonParse jsonParse = new JsonParse();
    private ListView newsListView;
    private News[] newsList = new News[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
        getNews();

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = newsList[position];
                Intent detailActivityIntent = new Intent(MainActivity.this,NewsDetailActivity.class);
                detailActivityIntent.putExtra("newsObj", news);
                startActivity(detailActivityIntent);
            }
        });

    }

    private void initComponent(){
        newsListView = findViewById(R.id.newsListView);
    }

    private void getNews(){
        String newsUrl = RequestURL.getNewsUrl();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, newsUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: " + response);
                newsList = jsonParse.newsJsonParser(response);
                ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(),newsList);
                newsListView.setAdapter(listViewAdapter);
                for (News n: newsList ) {
                    System.out.println(n.getAuthor() + " - " + n.getDescription());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage().toString());
                Toast.makeText(getApplicationContext(),"Haberler Getirilemedi İnternet Bağlantınızı Kontrol Ediniz!",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }

}
