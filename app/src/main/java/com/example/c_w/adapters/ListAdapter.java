package com.example.c_w.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.c_w.R;
import com.example.c_w.activitys.ListActivity;
import com.example.c_w.simple.Idea;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ListAdapter extends ArrayAdapter<Idea> {

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    private ImageView imageIdea;

    Context context;

    public ListAdapter(Context context, int resourceId, List<Idea> items){
        super(context, resourceId, items);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Idea idea = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView topic = (TextView) convertView.findViewById(R.id.topicText);
//        TextView description = (TextView) convertView.findViewById(R.id.descriptionText);
//        TextView request = (TextView) convertView.findViewById(R.id.requestText);
//        TextView people = (TextView) convertView.findViewById(R.id.peopleText);
        imageIdea = convertView.findViewById(R.id.imageIdea);


        topic.setText(idea.getTopic());
//        description.setText(idea.getDescription());
//        request.setText(idea.getRequest());
//        people.setText(idea.getPeople());
        addImage(idea.getImageref());


        return convertView;

    }

    private void addImage(String imageref) {
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        StorageReference imageRef = storageReference.child("ideaImages").child(imageref);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(imageIdea);
            }
        });
    }


}
