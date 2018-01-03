package com.example.cemdirman.comkonusarakogren.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cemdirman.comkonusarakogren.R;
import com.example.cemdirman.comkonusarakogren.model.News;
import com.squareup.picasso.Picasso;

/**
 * Created by cemdirman on 3.01.2018.
 */

public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private News[] newsLit;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, News[] newsLit) {
        this.context = context;
        this.newsLit = newsLit;
    }

    @Override
    public int getCount() {
        return newsLit.length;
    }

    @Override
    public News getItem(int position) {
        return newsLit[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View news = inflater.inflate(R.layout.news_layout,null);

        ImageView imgNewsImage = news.findViewById(R.id.imgNewsImage);
        TextView txtNewsTitle = news.findViewById(R.id.txtNewsTitle);

        txtNewsTitle.setText(newsLit[position].getTitle());
        Picasso.with(context)
                .load(newsLit[position].getUrlToImage())
                .fit()
                .into(imgNewsImage);


        return news;
    }
}
