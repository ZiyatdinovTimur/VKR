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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    static final int GALLERY_REQUEST = 1;

    public static String name;
    public static String surname;
    public static String email;
    public static String password;
    public static String specialization;
    public static String phoneNumber;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
    }

    public void signUp(View view) {

        final EditText getName = (EditText) findViewById(R.id.editName);
        final EditText getSurname = (EditText) findViewById(R.id.editSurname);
        final EditText getEmail = (EditText) findViewById(R.id.editEmail);
        final EditText getPassword = (EditText) findViewById(R.id.editPassword);
        final EditText getSpecialization = (EditText) findViewById(R.id.editSpecialization);
        final EditText getPhoneNumber = (EditText) findViewById(R.id.editPhoneNumber);

        name = getName.getText().toString();
        surname = getSurname.getText().toString();
        email = getEmail.getText().toString();
        password = getPassword.getText().toString();
        specialization = getSpecialization.getText().toString();
        phoneNumber = getPhoneNumber.getText().toString();


        if (TextUtils.isEmpty(name) | TextUtils.isEmpty(surname) | TextUtils.isEmpty(email) |
                TextUtils.isEmpty(password))
            Toast.makeText(this, "Пожалуйста, заполните все поля!", Toast.LENGTH_SHORT).show();


        else if (password.length() < 6)
            Toast.makeText(this, "Пароль должен быть не короче 6 символов", Toast.LENGTH_SHORT).show();

        else if (!flag)
            Toast.makeText(this, "Пожалуйста, добавте фото профиля!", Toast.LENGTH_SHORT).show();

        else
            createRecord(name, surname, email, password, specialization, phoneNumber);
    }

    public void createRecord(final String name, final String surname, final String email,
                             final String password, final String specialization, final String phoneNumber) {
        final String ideas = "0";
        final String matches = "0";
        final String responses = "0";
        firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("name", name);
                            hashMap.put("surname", surname);
                            hashMap.put("email", email);
                            hashMap.put("emailaddres", email);
                            hashMap.put("specialization", specialization);
                            hashMap.put("phoneNumber", phoneNumber);
                            hashMap.put("ideas", ideas);
//                            hashMap.put("matches", matches);
//                            hashMap.put("responses", responses);

                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(SignUpActivity.this, MainMenuActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignUpActivity.this, "Вы не можете зарегистрироваться с данным логином или паролем",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    public void addImage(View view) {

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
                    StorageReference rr = storageReference.child("images");
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

                    final EditText getEmail = (EditText) findViewById(R.id.editEmail);

                    String imageName = getEmail.getText().toString() + ".jpg";
                    StorageReference imageRef = storageReference.child("images").child(imageName);

                    UploadTask uploadTask = imageRef.putFile(selectedImage);
                    flag = true;

                }
        }
    }
}