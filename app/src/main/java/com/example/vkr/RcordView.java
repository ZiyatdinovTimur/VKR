package com.example.vkr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vkr.activitys.MainMenuActivity;

public class RcordView extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView targetText;
    public TextView sourceText;

    public RcordView(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        targetText = (TextView) itemView.findViewById(R.id.target_phrase);
        sourceText = (TextView) itemView.findViewById(R.id.source_phrase);
    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent(view.getContext(), MainMenuActivity.class);
//        Bundle b = new Bundle();
//        b.putString("userId", id);
//        intent.putExtras(b);
//        view.getContext().startActivity(intent);
    }
}
