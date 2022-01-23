package com.example.vkr;

import android.os.Bundle;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.vkr.activitys.ListActivity;
import com.example.vkr.activitys.FollowingsActivity;
import com.example.vkr.activitys.DictionaryActivity;
import com.example.vkr.activitys.ProfileActivity;
import com.example.vkr.activitys.SettingsActivity;
import com.example.vkr.simple.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    private ImageView profileImageView;

    String name;

    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_third, container, false);

        TextView tv = (TextView) inf.findViewById(R.id.nameTextViewMain);
        profileImageView = (ImageView) inf.findViewById(R.id.profileImageView);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                name = user.getName() + " " + user.getSurname();
                tv.setText(name);
                addImage(user.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return inf;
    }

    private void addImage(String imageName) {

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        imageName += ".jpg";
        StorageReference imageRef = storageReference.child("images").child(imageName);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(profileImageView);
            }
        });

    }

    public void openProfile(View view) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        startActivity(intent);
    }


    public void openList(View view) {
        Intent intent = new Intent(getActivity(), ListActivity.class);
        startActivity(intent);
    }

    public void openMessenger(View view) {
        Intent intent = new Intent(getActivity(), FollowingsActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

    public void openNotebook(View view) {
        Intent intent = new Intent(getActivity(), DictionaryActivity.class);
        startActivity(intent);
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent= new Intent(getActivity(), SignInActivity.class);
//        startActivity(intent);
//        return super.onOptionsItemSelected(item);
//    }


}