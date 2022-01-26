package com.example.c_w.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c_w.R;
import com.example.c_w.simple.User;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class CreatorActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    int ideaCount;

    boolean flag = false;

    static final int GALLERY_REQUEST = 1;

    public static String topic;
    public static String description;
    public static String request;
    public static String people;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                String ideas = user.getIdeas();
                ideaCount = Integer.valueOf(ideas);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void createIdea(View view) {
        final EditText getTopic = (EditText) findViewById(R.id.editTopic);
        final EditText getDescription = (EditText) findViewById(R.id.editDescription);
        final EditText getRequest = (EditText) findViewById(R.id.editRequest);
        final EditText getPeople = (EditText) findViewById(R.id.editPeople);


        topic = getTopic.getText().toString();
        description = getDescription.getText().toString();
        request = getRequest.getText().toString();
        people = getPeople.getText().toString();

        if (TextUtils.isEmpty(topic) | TextUtils.isEmpty(description) | TextUtils.isEmpty(request))
            Toast.makeText(this, "Пожалуйста, заполните все поля!", Toast.LENGTH_SHORT).show();

        else if (!flag)
            Toast.makeText(this, "Пожалуйста, добавте фото идеи!", Toast.LENGTH_SHORT).show();

        else
            publishIdea(firebaseUser.getUid(), topic, description, request, people);
    }

    private void publishIdea(String creator, String topic, String description, String request, String people) {
        String imageref = firebaseUser.getEmail() + ideaCount + ".jpg";
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String id = databaseReference.child("Ideas").push().getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("creator", creator);
        hashMap.put("topic", topic);
        hashMap.put("description", description);
        hashMap.put("request", request);
        hashMap.put("people", people);
        hashMap.put("imageref", imageref);
        databaseReference.child("Ideas").child(id).setValue(hashMap);
        databaseReference.child("Users").child(creator).child("ideas").setValue(String.valueOf(ideaCount));
        Intent intent= new Intent(CreatorActivity.this, MainMenuActivity.class);
        startActivity(intent);


    }


    public void addIdeaImage(View view) {
        ideaCount++;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;

        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    StorageReference rr = storageReference.child("ideaImages");
                    rr.putFile(selectedImage)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {

                                }
                            });


                    String imageName = firebaseUser.getEmail() + ideaCount + ".jpg";
                    StorageReference imageRef = storageReference.child("ideaImages").child(imageName);

                    UploadTask uploadTask = imageRef.putFile(selectedImage);
                    flag = true;

                }
        }
    }

}