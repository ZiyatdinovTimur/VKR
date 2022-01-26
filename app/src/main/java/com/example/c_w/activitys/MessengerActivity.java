package com.example.c_w.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c_w.R;
import com.example.c_w.adapters.ChatAdapter;
import com.example.c_w.simple.Message;
import com.example.c_w.simple.User;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessengerActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    List<Message> messageList;
    RecyclerView recyclerView;

    EditText editText;
    TextView textView;
    ImageView imageView;

    String userId;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        editText = findViewById(R.id.EditTextSend);
        textView = findViewById(R.id.mesNameTextView);
        imageView = findViewById(R.id.mesProfileImageView);

        intent = getIntent();
        userId = intent.getStringExtra("userId");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                setTitle(user.getName()+" "+user.getSurname());
                textView.setText(user.getName()+" "+user.getSurname());
                addImage(user.getEmail());
                getMessages(firebaseUser.getUid(),userId);
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
                Picasso.get().load(uri.toString()).into(imageView);
            }
        });

    }

    private void getMessages(String currentid, String userId) {
        messageList = new ArrayList<>();
        databaseReference=FirebaseDatabase.getInstance().getReference("Chat");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Message ms= snapshot.getValue(Message.class);
                    if(ms.getReceiver().equals(currentid)&&ms.getSender().equals(userId)||
                            ms.getReceiver().equals(userId)&&ms.getSender().equals(currentid)){
                        messageList.add(ms);
                    }
                    ChatAdapter messageAdapter = new ChatAdapter(MessengerActivity.this,messageList);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void sendMessage(View view) {
        String body = editText.getText().toString();
        if(!body.equals(""))
            createMessage(firebaseUser.getUid(),userId,body);
        else
            Toast.makeText(MessengerActivity.this,"Пожалуйста, введите сообщение!",Toast.LENGTH_SHORT).show();
        editText.setText("");
    }

    public void createMessage(String sender,String receiver, String body){

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("body",body);
        databaseReference.child("Chat").push().setValue(hashMap);

    }
}