package com.example.c_w.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.c_w.R;
import com.example.c_w.adapters.AccordIdeaAdapter;
import com.example.c_w.adapters.AccordUserAdapter;
import com.example.c_w.simple.Idea;
import com.example.c_w.simple.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    private String cusrrentUserID;
    private ArrayList<User> accordedUsers = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        cusrrentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recyclerView = (RecyclerView) findViewById(R.id.userRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(SettingsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AccordUserAdapter(getDataSetMatches(), SettingsActivity.this);
        recyclerView.setAdapter(adapter);

        getUserMatchId();

    }

    private List<User> getDataSetMatches() {
        return accordedUsers;
    }

    private void getUserMatchId() {

        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(cusrrentUserID)
                .child("chats");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot match : snapshot.getChildren()){
                        FetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void FetchMatchInformation(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    User item = dataSnapshot.getValue(User.class);
                    if(!checkUser(item)) {
                        accordedUsers.add(item);
                        adapter.notifyDataSetChanged();
                    }
//                    accordedIdeas.add(item);
//                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getUserInfo(Idea item) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(item.getCreator());
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    User item = dataSnapshot.getValue(User.class);
                    if(!checkUser(item)) {
                        accordedUsers.add(item);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private boolean checkUser(User item) {
        boolean  flag = false;

        for (User user :accordedUsers) {

            if(user.getId().equals(item.getId()))
                flag=true;

        }




        return flag;


    }

}