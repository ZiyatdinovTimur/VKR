package com.example.c_w.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.c_w.R;
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

public class MainMenuActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    private ImageView profileImageView;

    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        TextView nameTixtView = (TextView) findViewById(R.id.nameTextView);
        profileImageView = findViewById(R.id.profileImageView);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                name = user.getName() + " " + user.getSurname();
                nameTixtView.setText(name);
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
        Intent intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openCreator(View view) {
        Intent intent = new Intent(MainMenuActivity.this, CreatorActivity.class);
        startActivity(intent);
    }

    public void openList(View view) {
        Intent intent = new Intent(MainMenuActivity.this, ListActivity.class);
        startActivity(intent);
    }

    public void openMessenger(View view) {
        Intent intent = new Intent(MainMenuActivity.this, MatchIdeasActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openNotebook(View view) {
        Intent intent = new Intent(MainMenuActivity.this, NotebookActivity.class);
        startActivity(intent);
    }

    public void openInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Приложение для поиска start-up единомышленников "
                + "\n" + "\n" +
                "Работа выполнена в рамках курсовой работы ФКН ДПИ"
                + "\n" + "\n" +
                "По всем вопросам писать на почту taziyatdinov@edu.hse.ru"
                + "\n" + "\n" +
                "Зиятдинов Тимур БПИ181"
                + "\n" )
                .setTitle("О проекте");


        AlertDialog dialog = builder.create();
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();

    }


    public void openMyIdeais(View view) {
        Intent intent = new Intent(MainMenuActivity.this, MyIdeasActivity.class);
        startActivity(intent);
    }
}