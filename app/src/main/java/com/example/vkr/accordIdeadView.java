package com.example.vkr;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vkr.activitys.ConsideredUser;
import com.example.vkr.activitys.MessengerActivity;
import com.example.vkr.activitys.UserCardActivity;


public class  accordIdeadView extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView accordTopic;
    public TextView accordSt;
    public ImageView accordImage;
    public String id;
    public String uid;
    public accordIdeadView(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        
        accordTopic = (TextView) itemView.findViewById(R.id.accordTopic);
        accordSt = (TextView) itemView.findViewById(R.id.accordStatusFollowings);
        accordImage = (ImageView) itemView.findViewById(R.id.accordImage);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ConsideredUser.class);
        Bundle b = new Bundle();
        b.putString("userId", uid);
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}

