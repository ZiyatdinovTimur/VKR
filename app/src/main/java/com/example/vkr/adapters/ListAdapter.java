package com.example.vkr.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vkr.R;
import com.example.vkr.activitys.ConsideredUser;
import com.example.vkr.activitys.ListActivity;
import com.example.vkr.activitys.UserCardActivity;
import com.example.vkr.simple.Idea;
import com.example.vkr.simple.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ListAdapter extends ArrayAdapter<User> {

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    private ImageView imageSpeaker;

    Context context;

    public ListAdapter(Context context, int resourceId, List<User> items){
        super(context, resourceId, items);
        this.context=context;
    }
    public View getView(int position, View convertView, ViewGroup parent){

        User user = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView topic = (TextView) convertView.findViewById(R.id.topicText);
        TextView status = (TextView) convertView.findViewById(R.id.statusCardTextProfile);
        TextView followers = (TextView) convertView.findViewById(R.id.followersCard);
        TextView followings = (TextView) convertView.findViewById(R.id.followingsCard);
//        TextView description = (TextView) convertView.findViewById(R.id.descriptionText);
//        TextView request = (TextView) convertView.findViewById(R.id.requestText);
//        TextView people = (TextView) convertView.findViewById(R.id.peopleText);
        imageSpeaker = convertView.findViewById(R.id.imageSpeaker);
        TextView more_c = (TextView) convertView.findViewById(R.id.more_cl);

        String fullName = user.getName()+ " "+ user.getSurname();
        topic.setText(fullName);
        followers.setText(user.getFollowers());
        followings.setText(user.getFollowings());
        status.setText(user.getStatus());
        more_c.setOnClickListener((view -> openM(user.getId())));

//        description.setText(idea.getDescription());
//        request.setText(idea.getRequest());
//        people.setText(idea.getPeople());
        addImage(user.getEmail());

        return convertView;

    }

    private void openM(String id) {
        Intent intent = new Intent(context, ConsideredUser.class);
        Bundle b = new Bundle();
        b.putString("userId", id);
        intent.putExtras(b);
        context.startActivity(intent);
    }


    private void addImage(String imageName) {

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        imageName += ".jpg";
        StorageReference imageRef = storageReference.child("images").child(imageName);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(imageSpeaker);
            }
        });

    }

}
