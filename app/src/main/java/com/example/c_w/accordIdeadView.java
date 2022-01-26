package com.example.c_w;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c_w.activitys.MainMenuActivity;
import com.example.c_w.activitys.MessengerActivity;


public class  accordIdeadView extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView accordTopic;
    public ImageView accordImage;
    public String id;
    public accordIdeadView(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        
        accordTopic = (TextView) itemView.findViewById(R.id.accordTopic);
        accordImage = (ImageView) itemView.findViewById(R.id.accordImage);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), MessengerActivity.class);
        Bundle b = new Bundle();
        b.putString("userId", id);
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}

