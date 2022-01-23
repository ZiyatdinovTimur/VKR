package com.example.vkr.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkr.R;
import com.example.vkr.accordIdeadView;
import com.example.vkr.simple.Idea;
import com.example.vkr.simple.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowingsAdapter extends RecyclerView.Adapter<accordIdeadView> {
    private List<User> ideaList;
    private Context context;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String imageName;

    public FollowingsAdapter(List<User> matchesList, Context context) {
        this.ideaList = matchesList;
        this.context = context;
    }

    @Override
    public accordIdeadView onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.accorded_idea, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        accordIdeadView rcv = new accordIdeadView(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(accordIdeadView holder, int position) {
        holder.accordTopic.setText(ideaList.get(position).getName()+ " "+ ideaList.get(position).getSurname());
        holder.id = ideaList.get(position).getName();
        holder.uid = ideaList.get(position).getId();
        holder.accordSt.setText(ideaList.get(position).getStatus());
        imageName = ideaList.get(position).getEmail() +".jpg";
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        StorageReference imageRef = storageReference.child("images").child(imageName);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(holder.accordImage);
            }
        });

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                builder.setMessage(
//                        "Описание:" + " " + ideaList.get(position).getDescription()
//                                + "\n" + "\n" +
//                                "Ресурсы:" + " " +ideaList.get(position).getRequest()
//                                + "\n" + "\n" +
//                                "Специалисты:" + " " +ideaList.get(position).getPeople()
//                                + "\n" )
//                        .setTitle(ideaList.get(position).getTopic());
//
//
//                AlertDialog dialog = builder.create();
//                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                });
//                builder.show();
//
//                return true; }
//        });

    }

    @Override
    public int getItemCount() {
        return this.ideaList.size();
    }
}
