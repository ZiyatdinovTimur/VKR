package com.example.c_w.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c_w.R;
import com.example.c_w.accordIdeadView;
import com.example.c_w.noteView;
import com.example.c_w.simple.Idea;
import com.example.c_w.simple.Note;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<noteView> {
    private List<Note> ideaList;
    private Context context;



    public NoteAdapter(List<Note> matchesList, Context context) {
        this.ideaList = matchesList;
        this.context = context;
    }

    @Override
    public noteView onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_l, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        noteView rcv = new noteView(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(noteView holder, int position) {
        holder.accordTopic.setText(ideaList.get(position).getTopic());
        holder.id = ideaList.get(position).getCreator();


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage(
                        "Описание:" + " " + ideaList.get(position).getDescription()
                              )
                        .setTitle(ideaList.get(position).getTopic());


                AlertDialog dialog = builder.create();
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.show();

                return true; }
        });

    }

    @Override
    public int getItemCount() {
        return this.ideaList.size();
    }
}
