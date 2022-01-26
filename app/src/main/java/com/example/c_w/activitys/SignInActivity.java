package com.example.c_w.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c_w.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in3);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void SignIn(View view) {

        String email;
        String password;

        final EditText getEmail = (EditText) findViewById(R.id.editEmailSignIn);
        final EditText getPassword = (EditText) findViewById(R.id.editPasswordSignIn);

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
                                        Intent intent = new Intent(SignInActivity.this, MainMenuActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

                            } else {
                                Toast.makeText(SignInActivity.this, "Ошибка входа! ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
}


