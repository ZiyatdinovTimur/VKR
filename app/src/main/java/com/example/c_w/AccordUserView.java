package com.example.c_w;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.c_w.activitys.ListActivity;
import com.example.c_w.activitys.MessengerActivity;
import com.example.c_w.activitys.SettingsActivity;

public class AccordUserView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView accordTopic;
    public ImageView accordImage;
    public String id;
    public AccordUserView(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        accordTopic = (TextView) itemView.findViewById(R.id.accordName);
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
