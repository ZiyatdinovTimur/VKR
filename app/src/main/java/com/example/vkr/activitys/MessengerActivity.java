package com.example.vkr.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vkr.R;
import com.example.vkr.adapters.ChatAdapter;
import com.example.vkr.simple.Message;
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

import org.vosk.Model;
import org.vosk.Recognizer;
import org.vosk.android.RecognitionListener;
import org.vosk.android.SpeechService;
import org.vosk.android.SpeechStreamService;
import org.vosk.android.StorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessengerActivity extends AppCompatActivity implements
        RecognitionListener {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceC;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    List<Message> messageList;
    RecyclerView recyclerView;

    EditText editText;
    TextView textView;
    ImageView imageView;
    MediaPlayer mp;

    String userId;
    int lM;
    Intent intent;

    /* Used to handle permission request */
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    private Model model;
    private SpeechService speechService;
    private SpeechStreamService speechStreamService;

    private static final int RECOGNIZER_RESULT = 1;


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
        mp = MediaPlayer.create(getApplicationContext(), R.raw.click);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
        }
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                setTitle(user.getName() + " " + user.getSurname());
                textView.setText(user.getName() + " " + user.getSurname());
                addImage(user.getEmail());
                getMessages(firebaseUser.getUid(), userId);
                String ch = user.getSpeakLanguage();
            if(ch.equals("Russian")) {
                initRuModel();
            }

            if(ch.equals("Italian")) {
                initItModel();
            }
            else {
                initEnModel();
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    //    recognitionInitializer(firebaseUser.getUid());

//        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
//        }
 //       else {
//            if(languageModel==null) {
//                initEnModel();
//            }
//            if(languageModel.equals("Russian")) {
  //              initRuModel();
//            }
//
//            if(languageModel.equals("Italian")) {
//                initItModel();
//            }
//            else {
//                initEnModel();
//
//            }
  //     }

    }

    private void recognitionInitializer(String uid) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String followings = user.getFollowings();
                lM = Integer.valueOf(user.getFollowers());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        Toast.makeText(this, "UID" + lM, Toast.LENGTH_SHORT).show();


    }

    private void setLM(String speakLanguage) {
        Toast.makeText(this, "us" + speakLanguage, Toast.LENGTH_SHORT).show();

    }


    private void initEnModel() {
        StorageService.unpack(this, "model-en-us", "model",
                (model) -> {
                    this.model = model;
                },
                (exception) ->
                        Toast.makeText(this, "Ошибка инициализации модели" + exception.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void initRuModel() {
        StorageService.unpack(this, "model_ru", "model",
                (model) -> {
                    this.model = model;
                },
                (exception) ->
                        Toast.makeText(this, "Ошибка инициализации модели" + exception.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void initItModel() {
        StorageService.unpack(this, "model_it", "model",
                (model) -> {
                    this.model = model;
                },
                (exception) ->
                        Toast.makeText(this, "Ошибка инициализации модели" + exception.getMessage(), Toast.LENGTH_SHORT).show());
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
        databaseReference = FirebaseDatabase.getInstance().getReference("Chat");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message ms = snapshot.getValue(Message.class);
                    if (ms.getReceiver().equals(currentid) && ms.getSender().equals(userId) ||
                            ms.getReceiver().equals(userId) && ms.getSender().equals(currentid)) {
                        messageList.add(ms);
                    }
                    ChatAdapter messageAdapter = new ChatAdapter(MessengerActivity.this, messageList);
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
        if (!body.equals(""))
            createMessage(firebaseUser.getUid(), userId, body);
        else
            Toast.makeText(MessengerActivity.this, "Пожалуйста, введите сообщение!", Toast.LENGTH_SHORT).show();
        editText.setText("");
    }


    public void recognize(View view) {
        if (speechService != null) {
            mp.start();
            speechService.stop();
            speechService = null;
        } else {
            try {
                mp.start();
                Recognizer rec = new Recognizer(model, 16000.0f);
                speechService = new SpeechService(rec, 16000.0f);
                speechService.startListening(this);
            } catch (IOException e) {
                Toast.makeText(this, "Ошибка распознования речи" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
//        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        speechIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speech to text");
//        startActivityForResult(speechIntent,RECOGNIZER_RESULT);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == RECOGNIZER_RESULT && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            createMessage(firebaseUser.getUid(), userId, matches.get(0).toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void createMessage(String sender, String receiver, String body) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("body", body);
        databaseReference.child("Chat").push().setValue(hashMap);

    }

    @Override
    public void onPartialResult(String hypothesis) {
        //   createMessage(firebaseUser.getUid(),userId, hypothesis);

    }

    @Override
    public void onResult(String hypothesis) {
        hypothesis = hypothesis.replaceAll("[^\\da-zA-Zа-яёА-ЯЁ ]", "");
        hypothesis = hypothesis.replaceAll("text", "");
        hypothesis = hypothesis.substring(3);

        createMessage(firebaseUser.getUid(),userId, hypothesis);

    }

    @Override
    public void onFinalResult(String hypothesis) {
       // hypothesis = hypothesis.replaceAll("text", "");
//        hypothesis = hypothesis.replaceAll("\\{","");
        //       hypothesis = hypothesis.replaceAll("}","");
//        hypothesis = hypothesis.replaceAll("[^\\da-zA-Zа-яёА-ЯЁ ]", "");
//        hypothesis = hypothesis.replaceFirst(" ", "");
//        createMessage(firebaseUser.getUid(), userId, hypothesis);
    }

    @Override
    public void onError(Exception exception) {

    }

    @Override
    public void onTimeout() {

    }
}