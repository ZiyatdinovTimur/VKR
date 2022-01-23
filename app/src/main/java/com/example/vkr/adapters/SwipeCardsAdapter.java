package com.example.vkr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vkr.R;
import com.example.vkr.simple.Idea;

import java.util.List;

public class SwipeCardsAdapter extends ArrayAdapter<Idea> {

    Context context;

    public SwipeCardsAdapter(Context context, int resourceId, List<Idea> items){
        super(context, resourceId, items);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Idea card_item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.swipe_card_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.swipeCardTopic);
        ImageView image = (ImageView) convertView.findViewById(R.id.swipeCardImage);

        name.setText(card_item.getTopic());



        return convertView;

    }
}
