package com.example.vkr.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class UserCardActivity extends AppCompatActivity {

    String userId;
    Intent intent;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    String fullName;
    String followers;
    String followings;
    String email;
    String phone;
    String speakL;
    String learnL;

    TextView textView;
    TextView textViewFollowings;
    TextView textViewFollowers;

    private ImageView userCardImage;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_card);

        textView = findViewById(R.id.userCardName);
        textViewFollowings = findViewById(R.id.userFollowingsCard);
        textViewFollowers = findViewById(R.id.userFollowersCard);
        userCardImage = findViewById(R.id.userCardImage);
        intent = getIntent();
        userId = intent.getStringExtra("userId");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    fullName = user.getName() + " " + user.getSurname();
                    followings = user.getFollowings();
                    followers = user.getFollowers();
                    email = user.getEmailaddres();
                    phone = "+"+user.getCountryCode() + " "+ user.getPhoneNumber();
                    speakL = user.getSpeakLanguage();
                    learnL = user.getLearnLanguage();
                    textView.setText(fullName);
                    addImage(user.getEmail());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addImage(String imageName) {

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        imageName += ".jpg";
        StorageReference imageRef = storageReference.child("images").child(imageName);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(userCardImage);
            }
        });

    }

    public void openMore(View view) {
        Intent intent = new Intent(UserCardActivity.this, ConsideredUser.class);
        Bundle b = new Bundle();
        b.putString("userId", userId);
        intent.putExtras(b);
        startActivity(intent);
    }
}