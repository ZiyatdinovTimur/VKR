package com.example.vkr.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vkr.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        TextInputLayout textInputLayoutLog = findViewById(R.id.textInputLogin);
        textInputLayoutLog.setHint("Login");
        TextInputLayout textInputLayoutPas = findViewById(R.id.textInputPassword);
        textInputLayoutPas.setHint("Password");
        TextView textView = findViewById(R.id.textView_createAc);
        String text ="Don't have an account? Create new.";
        SpannableString ss =new SpannableString(text);

        ForegroundColorSpan fcs = new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.main_color));

        ClickableSpan cs = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent= new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        };
        ss.setSpan(fcs,23,34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(cs,23,34,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

    }

//    public void signUp(View view) {
//                Intent intent= new Intent(MainActivity.this, SignUpActivity.class);
//                startActivity(intent);
//    }


    public void SignIn(View view) {
        String email;
        String password;

        final TextInputEditText getEmail = (TextInputEditText) findViewById(R.id.LoginAuthTextInput);
        final TextInputEditText getPassword = (TextInputEditText) findViewById(R.id.PasswordAuthTextInput);

        email = getEmail.getText().toString();
        password = getPassword.getText().toString();

        if (TextUtils.isEmpty(email) | TextUtils.isEmpty(password))
            Toast.makeText(this, "Все поля должны быть заполнены!", Toast.LENGTH_SHORT).show();

        else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                databaseReference = FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("Users").child(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

                            } else {
                                Toast.makeText(MainActivity.this, "Ошибка входа! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}