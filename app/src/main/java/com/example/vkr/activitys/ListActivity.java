package com.example.vkr.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.vkr.R;
import com.example.vkr.simple.User;
import com.example.vkr.adapters.ListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ListAdapter lAdapter;
    private int i;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    String targetLanguage;


    ListView listView;
    List<User> users;

    int followersCount;
    int followingsCount;
    int matchesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        inputIdeas();
        users = new ArrayList<User>();

        lAdapter = new ListAdapter(this, R.layout.item, users);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);


        flingContainer.setAdapter(lAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
//                Log.d("LIST", "removed object!");
//                users.remove(0);
//                lAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                User ob = (User) dataObject;
                String userId = ob.getId();
                databaseReference.child(userId).child("swipes").child(firebaseUser.getUid()).setValue(true);
 //               Toast.makeText(ListActivity.this, "Left!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                User ob = (User) dataObject;
                String userId = ob.getId();
                String ideaId = ob.getId();
                databaseReference.child(userId).child("swipes").child(firebaseUser.getUid()).setValue(true);
                databaseReference.child(userId).child("positivSwipes").child(firebaseUser.getUid()).setValue(true);
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                databaseReference.child(firebaseUser.getUid()).child("connections").child(userId).setValue(true);
                databaseReference.child(firebaseUser.getUid()).child("chats").child(userId).setValue(true);
                databaseReference.child(userId).child("chats").child(firebaseUser.getUid()).setValue(true);

                increaseFollowings(firebaseUser.getUid());

                increaseFollowers(userId);

            }



            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here

                lAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
//                Idea ob = (Idea) dataObject;
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
//
//                builder.setMessage(
//                        "Описание:" + " " + ob.getDescription()
//                                + "\n" + "\n" +
//                                "Ресурсы:" + " " +ob.getRequest()
//                                + "\n" + "\n" +
//                                "Специалисты:" + " " +ob.getPeople()
//                                + "\n" )
//                        .setTitle(ob.getTopic());
//
//
//                AlertDialog dialog = builder.create();
//                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                });
//                builder.show();
            }
        });
    }

    private void increaseFollowings(String uid) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String followings = user.getFollowings();
                followingsCount = Integer.valueOf(followings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        followingsCount++;

        databaseReference.child("followings").setValue(String.valueOf(followingsCount));

    }

    private void increaseFollowers(String creatorId) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(creatorId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String followers = user.getFollowers();
                followersCount = Integer.valueOf(followers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        followersCount++;

        databaseReference.child("followers").setValue(String.valueOf(followersCount));

    }

    private void increaseMathces(String uid) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String matches = user.getMatches();
                matchesCount = Integer.valueOf(matches);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void inputIdeas() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists() && !dataSnapshot.child("swipes").hasChild(firebaseUser.getUid())) {
                    User user = dataSnapshot.getValue(User.class);
                    if (!(user.getId().equals(firebaseUser.getUid())) ) {
                        users.add(user);
                        lAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void openAc(View view) {
        Intent intent = new Intent(ListActivity.this, ConsideredUser.class);
        Bundle b = new Bundle();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        b.putString("userId", firebaseUser.getUid());
        intent.putExtras(b);
        startActivity(intent);
    }
}