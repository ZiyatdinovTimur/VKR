package com.example.vkr.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.vkr.R;
import com.example.vkr.adapters.AccordUserAdapter;
import com.example.vkr.simple.Idea;
import com.example.vkr.simple.User;
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
    private AccordUserAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    SearchView searchView;

    private String cusrrentUserID;
    private ArrayList<User> accordedUsers = new ArrayList<User>();
    private ArrayList<User> full = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_m);
        setSupportActionBar(toolbar);

        searchView = (SearchView) findViewById(R.id.search_view_mes);

        cusrrentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recyclerView = (RecyclerView) findViewById(R.id.userRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(SettingsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AccordUserAdapter(getDataSetMatches(), getFull(),SettingsActivity.this);
        recyclerView.setAdapter(adapter);


        getUserMatchId();
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private List<User> getFull() {
        return full;
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
                        full.add(item);
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