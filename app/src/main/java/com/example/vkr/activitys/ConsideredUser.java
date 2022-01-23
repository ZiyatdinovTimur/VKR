package com.example.vkr.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vkr.R;
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

public class ConsideredUser extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    private ImageView profileImage;
    TextView nameTextView;
    TextView emailTextView ;
    TextView phoneNumberTextView;
    TextView followersTextView;
    TextView followingsTextView;
    TextView speakLanguageTextView;
    TextView learnLanguageTextView;
    TextView statusTextView;

    String name;
    String email;
    String phone;
    String speakL;
    String LearnL;
    String followers;
    String followings;
    String status;

    String userId;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_considered_user);
        intent = getIntent();
        userId = intent.getStringExtra("userId");

        nameTextView = (TextView) findViewById(R.id.nameTextProfileC);
        emailTextView = (TextView) findViewById(R.id.email_prof_textC);
        phoneNumberTextView = (TextView) findViewById(R.id.phone_prof_textC);
        speakLanguageTextView = (TextView) findViewById(R.id.speak_prof_textC);
        learnLanguageTextView = (TextView) findViewById(R.id.learn_prof_textC);
        followersTextView = (TextView) findViewById(R.id.followersTextViewC);
        followingsTextView = (TextView) findViewById(R.id.followingsTextViewC);
        statusTextView = (TextView) findViewById(R.id.statusCTextProfile);
        profileImage = (ImageView) findViewById(R.id.profileImageC);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                name = user.getName() + " " + user.getSurname();
                email = user.getEmailaddres();
                phone = "+"+user.getCountryCode() + " "+ user.getPhoneNumber();
                speakL = user.getSpeakLanguage();
                LearnL = user.getLearnLanguage();
                followers = user.getFollowers();
                followings = user.getFollowings();
                status = user.getStatus();

                nameTextView.setText(name);
                emailTextView.setText(email);
                phoneNumberTextView.setText(phone);
                speakLanguageTextView.setText(speakL);
                learnLanguageTextView.setText(LearnL);
                followersTextView.setText(followers);
                followingsTextView.setText(followings);
                statusTextView.setText(status);

                addImage(user.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void addImage(String imageName) {
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        imageName+=".jpg";
        StorageReference imageRef = storageReference.child("images").child(imageName);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(profileImage);
            }
        });
    }

}