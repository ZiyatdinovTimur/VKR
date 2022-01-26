package com.example.c_w.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c_w.R;
import com.example.c_w.simple.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class AddNoteActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;


    boolean flag = false;


    public static String topic;
    public static String description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note2);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                String ideas = user.getIdeas();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final EditText getTopic = (EditText) findViewById(R.id.noteTitle);
        final EditText getDescription = (EditText) findViewById(R.id.noteDetails);


        topic = getTopic.getText().toString();
        description = getDescription.getText().toString();


        if (TextUtils.isEmpty(topic) | TextUtils.isEmpty(description) )
            Toast.makeText(this, "Пожалуйста, заполните все поля!", Toast.LENGTH_SHORT).show();

        else
            publishNote(firebaseUser.getUid(), topic, description);


        return super.onOptionsItemSelected(item);
    }


    private void publishNote(String creator, String topic, String description) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String id = databaseReference.child("Notes").push().getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("creator", creator);
        hashMap.put("topic", topic);
        hashMap.put("description", description);
        databaseReference.child("Notes").child(id).setValue(hashMap);
        Intent intent= new Intent(AddNoteActivity.this,NotebookActivity.class);
        startActivity(intent);


    }

}