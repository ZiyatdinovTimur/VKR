package com.example.c_w.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;

import com.example.c_w.R;

public class MainActivity extends AppCompatActivity {

    private android.widget.ImageSwitcher imageSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);
    }

    public void signUp(View view) {
                Intent intent= new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
    }

    public void signIn(View view) {
        Intent intent= new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    public void onSwitcherClick(View view) {
        imageSwitcher.showNext();
    }
}