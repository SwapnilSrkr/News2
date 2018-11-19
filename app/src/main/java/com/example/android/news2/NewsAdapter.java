package com.example.android.news2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Activity context, ArrayList<News> Newses) {
        super(context, 0, Newses);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        final News currentNews = getItem(position);

        TextView titleNewsTextView = (TextView) listItemView.findViewById(R.id.news_title);
        TextView authorNewsTextView = (TextView) listItemView.findViewById(R.id.author_news);
        TextView sectionNewsTextView = (TextView) listItemView.findViewById(R.id.section_type);
        TextView publicationDateTextView = (TextView) listItemView.findViewById(R.id.publicationDate);
        assert currentNews != null;
        titleNewsTextView.setText(currentNews.getTitle());
        authorNewsTextView.setText(currentNews.getAuthor());
        sectionNewsTextView.setText(String.valueOf(currentNews.getSection()));
        publicationDateTextView.setText(String.valueOf(currentNews.getDate()));
        return listItemView;
    }
}