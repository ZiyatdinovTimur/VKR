package com.example.c_w;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.c_w.activitys.MainMenuActivity;
import com.example.c_w.activitys.MessengerActivity;

public class noteView  extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView accordTopic;
    public String id;
    public noteView(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        Object
        accordTopic = (TextView) itemView.findViewById(R.id.noteText);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), MainMenuActivity.class);
        Bundle b = new Bundle();
        b.putString("userId", id);
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}
