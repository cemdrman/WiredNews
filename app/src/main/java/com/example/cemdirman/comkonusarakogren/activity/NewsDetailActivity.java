package com.example.cemdirman.comkonusarakogren.activity;

import android.content.Intent;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.cemdirman.comkonusarakogren.R;
import com.example.cemdirman.comkonusarakogren.model.News;
import com.squareup.picasso.Picasso;

/**
 * Created by cemdirman on 3.01.2018.
 */

public class NewsDetailActivity extends Activity {

    private ImageView imgNewsDetailImage;
    private TextView  txtNewsDetailDescription;
    private TextView  txtNewsDetailAuthor;
    private TextView  txtNewsDetailPublishedAt;
    private TextView  txtNewsDetailTitle;

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
        txtNewsDetailDescription =findViewById(R.id.txtNewsDetailDescription);
        txtNewsDetailAuthor =findViewById(R.id.txtNewsDetailAuthor);
        txtNewsDetailPublishedAt =findViewById(R.id.txtNewsDetailPublishedAt);
        txtNewsDetailTitle =findViewById(R.id.txtNewsDetailTitle);
    }

    private void setNewsDetail(News detailNews){
        txtNewsDetailDescription.setText(detailNews.getDescription());
        txtNewsDetailAuthor.setText("Author: " + detailNews.getAuthor());
        txtNewsDetailPublishedAt.setText("Date: " + detailNews.getPublishedAt());
        txtNewsDetailTitle.setText(detailNews.getTitle());

        Picasso.with(getApplicationContext())
                .load(detailNews.getUrlToImage())
                .fit()
                .into(imgNewsDetailImage);
    }

    private void findMostRecentWord(){
        
    }

    private void translate(){


    }


}
