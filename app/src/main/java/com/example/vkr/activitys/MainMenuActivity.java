package com.example.vkr.activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vkr.FirstFragment;
import com.example.vkr.R;
import com.example.vkr.ThirdFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.vkr.databinding.ActivityMainMenuBinding;


public class MainMenuActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    BottomNavigationView bottomNavigationView;

    private ImageView profileImageView;

    String name;

    ActivityMainMenuBinding activityMainMenuBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        activityMainMenuBinding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(activityMainMenuBinding.getRoot());
        replaceFragment(new ThirdFragment());
        activityMainMenuBinding.bottomNavView.setOnNavigationItemSelectedListener(item->{

            switch (item.getItemId()){

                case R.id.profile:
                    replaceFragment(new FirstFragment());
                    break;
                case R.id.home:
                    replaceFragment(new ThirdFragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameL,fragment);
        fragmentTransaction.commit();


    }

//    private void addImage(String imageName) {
//
//        firebaseStorage = FirebaseStorage.getInstance();
//        storageReference = firebaseStorage.getReference();
//        imageName += ".jpg";
//        StorageReference imageRef = storageReference.child("images").child(imageName);
//        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri.toString()).into(profileImageView);
//            }
//        });
//
//    }

    public void openProfile(View view) {
        Intent intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openCreator(View view) {
        Intent intent = new Intent(MainMenuActivity.this, ListActivity.class);
        startActivity(intent);
    }

    public void openList(View view) {
        Intent intent = new Intent(MainMenuActivity.this, FollowersActivity.class);
        startActivity(intent);
    }

    public void openMessenger(View view) {
        Intent intent = new Intent(MainMenuActivity.this, FollowingsActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openVoskDemo(View view) {
//        Intent intent = new Intent(MainMenuActivity.this, VoskDemoActivity.class);
//        startActivity(intent);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Данная функциональность будет добавлена в следующем релизе! "
 )
                .setTitle("Путь дальнейшего развития");


        AlertDialog dialog = builder.create();
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();
    }

    public void editEmailInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Input new email");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        AlertDialog dialog = builder.create();
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Users").child(firebaseUser.getUid()).child("emailaddres").setValue(input.getText().toString());
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();
    }

    public void editStatusInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Input new status");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        AlertDialog dialog = builder.create();
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Users").child(firebaseUser.getUid()).child("status").setValue(input.getText().toString());
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();
    }

    public void editPhoneInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Input new phone");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        AlertDialog dialog = builder.create();
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Users").child(firebaseUser.getUid()).child("phoneNumber").setValue(input.getText().toString());
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();
    }

    public void editMotherTongueInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Input new mother tongue");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        AlertDialog dialog = builder.create();
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Users").child(firebaseUser.getUid()).child("speakLanguage").setValue(input.getText().toString());
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();
    }

    public void editLearnLanguageInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Input new studied language");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        AlertDialog dialog = builder.create();
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Users").child(firebaseUser.getUid()).child("learnLanguage").setValue(input.getText().toString());
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();
    }


    public void openNotebook(View view) {
        Intent intent = new Intent(MainMenuActivity.this, DictionaryActivity.class);
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
        Intent intent = new Intent(MainMenuActivity.this, DictionaryActivity.class);
        startActivity(intent);
    }
}