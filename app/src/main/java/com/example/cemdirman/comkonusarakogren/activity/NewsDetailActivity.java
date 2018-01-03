package com.example.cemdirman.comkonusarakogren.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cemdirman on 3.01.2018.
 */

public class NewsDetailActivity extends Activity {

    private ImageView imgNewsDetailImage;
    private TextView  txtNewsDetailDescription;
    private TextView  txtNewsDetailAuthor;
    private TextView  txtNewsDetailPublishedAt;
    private TextView  txtNewsDetailTitle;
    private TextView  txtTranslatedWords;
    private JsonParse  jsonParse = new JsonParse();

    private String[][] translatedWordList = new String[5][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initialComponents();

        Intent intent = getIntent();

        News detailNews = (News) intent.getSerializableExtra("newsObj");
        setNewsDetail(detailNews);

    }

    private void initialComponents(){
        imgNewsDetailImage = findViewById(R.id.imgNewsDetailImage);
        txtNewsDetailDescription = findViewById(R.id.txtNewsDetailDescription);
        txtNewsDetailAuthor = findViewById(R.id.txtNewsDetailAuthor);
        txtNewsDetailPublishedAt = findViewById(R.id.txtNewsDetailPublishedAt);
        txtNewsDetailTitle = findViewById(R.id.txtNewsDetailTitle);
        txtTranslatedWords = findViewById(R.id.txtTranslatedWords);
    }

    private void setNewsDetail(News detailNews){
        txtNewsDetailDescription.setText(detailNews.getDescription());
        txtNewsDetailAuthor.setText("Author: " + detailNews.getAuthor());
        txtNewsDetailPublishedAt.setText("Date: " + dateFormater(detailNews.getPublishedAt()));
        txtNewsDetailTitle.setText(detailNews.getTitle());

        Picasso.with(getApplicationContext())
                .load(detailNews.getUrlToImage())
                .fit()
                .into(imgNewsDetailImage);

        findRepatedWord(getListOfWord());
        String translatedWords = "" ;
        for (int i = 0; i < translatedWordList.length; i++) {

            System.out.println(translatedWordList[i][0] + "-" + translatedWordList[i][1]);

            txtTranslatedWords.setText(translatedWords);
        }
        System.out.println("sadasddasd");
        System.out.println("translated-words: " + translatedWords);

    }

    private String[] getListOfWord(){
        String words = txtNewsDetailDescription.getText().toString().concat(txtNewsDetailTitle.getText().toString());
        String[] wordList = words.toLowerCase().split("[.? ,]");
        return wordList;
    }

    private void findRepatedWord(String[] wordList){
        HashMap<String,Integer> counterMap = new HashMap<>();
        int defaultValue = 1;
        boolean isEmpty = true;
        for (int i = 0; i < wordList.length; i++) {
            if (isEmpty) {
                counterMap.put(wordList[i], defaultValue); //just only first one
                isEmpty = false;
            }else{
                if (counterMap.containsKey(wordList[i])) {
                    counterMap.put(wordList[i],counterMap.get(wordList[i]) + 1);
                }else{
                    counterMap.put(wordList[i],defaultValue);
                }
            }
        }

        String[] sortedWordList = sortWordNumber(counterMap);
        translatedWordList = match(sortedWordList);

    }

    private String[] sortWordNumber( HashMap<String,Integer> counterMap){
        Set<Map.Entry<String, Integer>> entries = counterMap.entrySet();
        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                Integer v1 = e1.getValue();
                Integer v2 = e2.getValue();
                return v1.compareTo(v2);
            }
        };

        List<Map.Entry<String, Integer>> listOfEntries = new ArrayList<>(entries);
        Collections.sort(listOfEntries, valueComparator);
        System.out.println("sorted: " + listOfEntries);

        String[] sortedWords = new String[5];
        int counter = 0;
        for (int i = listOfEntries.size()-1; i > 0 ; i--) {
            if (counter < 5) {
                sortedWords[counter] = listOfEntries.get(i).getKey();
                counter++;
            }
        }

        return sortedWords;
    }

    private String[][] match(String[] words){
        String[][] translatedWords = new String[5][2];
        for (int i = 0; i < words.length; i++) {
            translatedWords[i][0] = words[i];

            translatedWords[i][1] = translate(RequestURL.getTranslatinonUrl(words[i]));
        }
        return translatedWords;
    }
    private String translatedWord;
    private String translate(String translationUrl){

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, translationUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                translatedWord = jsonParse.translatedWordParser(response);
                System.out.println("sonuc:" + translatedWord);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
        return translatedWord;
    }

    private String dateFormater(String date){
        return String.valueOf(date.split("T")[0]);
    }


}
