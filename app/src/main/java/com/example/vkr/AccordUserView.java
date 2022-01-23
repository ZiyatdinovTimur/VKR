package com.example.vkr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vkr.activitys.MessengerActivity;
import com.example.vkr.activitys.UserCardActivity;

public class AccordUserView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView accordTopic;
    public TextView accordStatus;
    public ImageView accordImage;
    public String id;
    public AccordUserView(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        accordTopic = (TextView) itemView.findViewById(R.id.accordName);
        accordStatus = (TextView) itemView.findViewById(R.id.accordStatus);
        accordImage = (ImageView) itemView.findViewById(R.id.accordProfileImage);
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
