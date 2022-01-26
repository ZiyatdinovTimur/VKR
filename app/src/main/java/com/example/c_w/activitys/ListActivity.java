package com.example.c_w.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.c_w.R;
import com.example.c_w.simple.Idea;
import com.example.c_w.simple.User;
import com.example.c_w.adapters.ListAdapter;
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


    ListView listView;
    List<Idea> ideas;

    int responsesCount;
    int matchesCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Ideas");

        inputIdeas();
        ideas = new ArrayList<Idea>();

        lAdapter = new ListAdapter(this, R.layout.item, ideas);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);


        flingContainer.setAdapter(lAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                ideas.remove(0);
                lAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Idea ob = (Idea) dataObject;
                String ideaId = ob.getId();
                databaseReference.child(ideaId).child("swipes").child(firebaseUser.getUid()).setValue(true);
                Toast.makeText(ListActivity.this, "Left!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Idea ob = (Idea) dataObject;
                String creatorId = ob.getCreator();
                String ideaId = ob.getId();
                databaseReference.child(ideaId).child("swipes").child(firebaseUser.getUid()).setValue(true);
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                databaseReference.child(firebaseUser.getUid()).child("connections").child(ideaId).setValue(true);
                databaseReference.child(firebaseUser.getUid()).child("chats").child(creatorId).setValue(true);
                databaseReference.child(creatorId).child("chats").child(firebaseUser.getUid()).setValue(true);

//                increaseMathces(firebaseUser.getUid());
//                increaseResponses(creatorId);
                Toast.makeText(ListActivity.this, "right!", Toast.LENGTH_LONG).show();
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
                Idea ob = (Idea) dataObject;

                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);

                builder.setMessage(
                        "Описание:" + " " + ob.getDescription()
                                + "\n" + "\n" +
                                "Ресурсы:" + " " +ob.getRequest()
                                + "\n" + "\n" +
                                "Специалисты:" + " " +ob.getPeople()
                                + "\n" )
                        .setTitle(ob.getTopic());


                AlertDialog dialog = builder.create();
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.show();
            }
        });
    }

    private void increaseResponses(String creatorId) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(creatorId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String ideas = user.getResponses();
                responsesCount = Integer.valueOf(ideas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        responsesCount++;

        databaseReference.child("responses").setValue(String.valueOf(responsesCount));

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
        FirebaseDatabase.getInstance().getReference().child("Ideas").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists() && !dataSnapshot.child("swipes").hasChild(firebaseUser.getUid())) {
                    Idea item = dataSnapshot.getValue(Idea.class);
                    if (!(item.getCreator().equals(firebaseUser.getUid()))) {
                        ideas.add(item);
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
}