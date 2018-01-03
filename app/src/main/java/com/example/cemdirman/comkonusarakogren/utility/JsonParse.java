package com.example.cemdirman.comkonusarakogren.utility;

import com.example.cemdirman.comkonusarakogren.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by cemdirman on 3.01.2018.
 */

public class JsonParse {

    private int newsNumber = 5;

    /**
     *
     * @param newsJson
     * @return
     */

    public News[] newsJsonParser(String newsJson){
        News[] newsList = new News[newsNumber];

        try {
            JSONObject jsonObject = new JSONObject(newsJson);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");


            for (int i = 0; i < newsList.length; i++) {
                News news = new News();

                news.setAuthor(jsonArray.getJSONObject(i).getString("author"));
                news.setDescription(jsonArray.getJSONObject(i).getString("description"));
                news.setPublishedAt(jsonArray.getJSONObject(i).getString("publishedAt"));
                news.setTitle(jsonArray.getJSONObject(i).getString("title"));
                news.setUrl(jsonArray.getJSONObject(i).getString("url"));
                news.setUrlToImage(jsonArray.getJSONObject(i).getString("urlToImage"));

                newsList[i] = news;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsList;
    }

    public String translatedWordParser(String wordJson){
        String translatedWord = null;

        try {
            JSONObject jsonObject = new JSONObject(wordJson);
            translatedWord = jsonObject.getString("text");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return translatedWord;
    }





}
