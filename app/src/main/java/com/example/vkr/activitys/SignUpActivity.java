package com.example.vkr.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vkr.R;
import com.example.vkr.SpinAdapter;
import com.example.vkr.simple.LanguageLearn;
import com.example.vkr.simple.LanguageSpeak;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;

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
    public static String phoneNumber;
    public static String countryCode;
    public static String speakLanguage;
    public static String learnLanguage;

    HashMap<Integer, String> languageMap = new HashMap<>();

    {
        languageMap.put(0,"English");
        languageMap.put(1,"Spanish");
        languageMap.put(2,"Chinese");
        languageMap.put(3,"Hindi");
        languageMap.put(4,"Arab");
        languageMap.put(5,"Bengal");
        languageMap.put(6,"Portuguese");
        languageMap.put(7,"Russian");
        languageMap.put(8,"Japanese");
        languageMap.put(9,"Lakhnda");
        languageMap.put(10,"Malay");
        languageMap.put(11,"Turkish");
        languageMap.put(12,"Korean");
        languageMap.put(13,"French");
        languageMap.put(14,"German");
        languageMap.put(15,"Vietnamese");
        languageMap.put(16,"Javanese");
        languageMap.put(17,"Italian");
        languageMap.put(18,"Persian");
        languageMap.put(19,"Polish");
    }


    private Spinner spinnerSpeak;
    private Spinner spinnerLearn;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        TextInputLayout textInputLayout = findViewById(R.id.textInputName);
        textInputLayout.setHint("First name");
        TextInputLayout textInputLayout2 = findViewById(R.id.textInputName2);
        textInputLayout2.setHint("Surname");
        TextInputLayout textInputLayout3 = findViewById(R.id.textInputName3);
        textInputLayout3.setHint("Email");
        TextInputLayout textInputLayout4 = findViewById(R.id.textInputName4);
        textInputLayout4.setHint("Password");
        TextInputLayout textInputLayout5 = findViewById(R.id.textInputName5);
        textInputLayout5.setHint("Phone");
        TextView textView = findViewById(R.id.textView_exAc);
        String text ="Already have an account?";
        SpannableString ss =new SpannableString(text);
        ForegroundColorSpan fcs = new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.main_color));
        ClickableSpan cs = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent= new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        ss.setSpan(fcs,0,24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(cs,0,24,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        LanguageSpeak.initUsers();
        spinnerSpeak = (Spinner) findViewById(R.id.spinnerExample);

        SpinAdapter customAdapter = new SpinAdapter(this, R.layout.spinner_adapter, LanguageSpeak.getUserArrayList());
        spinnerSpeak.setAdapter(customAdapter);


        LanguageLearn.initUsers();
        spinnerLearn = (Spinner) findViewById(R.id.spinnerExample2);

        SpinAdapterLearn customAdapter2 = new SpinAdapterLearn(this, R.layout.spinner_adapter, LanguageLearn.getUserArrayList());
        spinnerLearn.setAdapter(customAdapter2);

        spinnerSpeak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                speakLanguage = languageMap.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerLearn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                learnLanguage = languageMap.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void signUp(View view) {

        final CountryCodePicker getCcp = (CountryCodePicker) findViewById(R.id.ccp);

        final TextInputEditText getName = (TextInputEditText) findViewById(R.id.FirstNameTextInput);
        final TextInputEditText getSurname = (TextInputEditText) findViewById(R.id.SecondNameTextInput);
        final TextInputEditText getPhoneNumber = (TextInputEditText) findViewById(R.id.PhoneTextInput);
        final TextInputEditText getEmail = (TextInputEditText) findViewById(R.id.EmailTextInput);
        final TextInputEditText getPassword = (TextInputEditText) findViewById(R.id.PasswordTextInput);

        name = getName.getText().toString();
        surname = getSurname.getText().toString();
        email = getEmail.getText().toString();
        password = getPassword.getText().toString();
        phoneNumber = getPhoneNumber.getText().toString();
        countryCode = getCcp.getSelectedCountryCode().toString();




        if (TextUtils.isEmpty(name) | TextUtils.isEmpty(surname) | TextUtils.isEmpty(email) |
                TextUtils.isEmpty(password)|TextUtils.isEmpty(phoneNumber))
            Toast.makeText(this, "Пожалуйста, заполните все поля!", Toast.LENGTH_SHORT).show();


        else if (password.length() < 6)
            Toast.makeText(this, "Пароль должен быть не короче 6 символов", Toast.LENGTH_SHORT).show();

        else if (!flag)
            Toast.makeText(this, "Пожалуйста, добавте фото профиля!", Toast.LENGTH_SHORT).show();

        else
            createRecord(name, surname, email, password, phoneNumber,speakLanguage,learnLanguage);
    }

    public void createRecord(final String name, final String surname, final String email,
                             final String password, final String phoneNumber,final String speakLanguage, final String learnLanguage) {
        final String followers = "0";
        final String followings = "0";
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
                            hashMap.put("phoneNumber", phoneNumber);
                            hashMap.put("countryCode", countryCode);
                            hashMap.put("followers", followers);
                            hashMap.put("followings", followings);
                            hashMap.put("speakLanguage", speakLanguage);
                            hashMap.put("learnLanguage", learnLanguage);
                            hashMap.put("status", "I am new user!");
                            hashMap.put("additionalSpeakLanguage", "");
                            hashMap.put("additionalLearnLanguage", "");
                            hashMap.put("visibility", "true");


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

                    final TextInputEditText getEmail = (TextInputEditText) findViewById(R.id.EmailTextInput);

                    String imageName = getEmail.getText().toString() + ".jpg";
                    StorageReference imageRef = storageReference.child("images").child(imageName);

                    UploadTask uploadTask = imageRef.putFile(selectedImage);
                    flag = true;

                }
        }
    }
}