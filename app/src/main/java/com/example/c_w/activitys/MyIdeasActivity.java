package com.example.c_w.activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.c_w.R;
import com.example.c_w.adapters.ListAdapter;
import com.example.c_w.simple.Idea;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MyIdeasActivity extends AppCompatActivity {

    private ListAdapter lAdapter;
    private int i;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;


    ListView listView;
    List<Idea> ideas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ideas);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Ideas");

        inputIdeas();
        ideas = new ArrayList<Idea>();

        lAdapter = new ListAdapter(this, R.layout.item, ideas);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.myFrame);


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
//                Idea ob = (Idea) dataObject;
//                String ideaId = ob.getId();
//                databaseReference.child(ideaId).child("swipes").child(firebaseUser.getUid()).setValue(true);
//                Toast.makeText(MyIdeasActivity.this, "Left!", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onRightCardExit(Object dataObject) {
//                Idea ob = (Idea) dataObject;
//                String creatorId = ob.getCreator();
//                String ideaId = ob.getId();
//                databaseReference.child(ideaId).child("swipes").child(firebaseUser.getUid()).setValue(true);
//                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
//                databaseReference.child(firebaseUser.getUid()).child("connections").child(ideaId).setValue(true);
////                increaseMathces(firebaseUser.getUid());
////                increaseResponses(creatorId);
//                Toast.makeText(MyIdeasActivity.this, "right!", Toast.LENGTH_LONG).show();
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

                AlertDialog.Builder builder = new AlertDialog.Builder(MyIdeasActivity.this);

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
    public void inputIdeas() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("Ideas").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    Idea item = dataSnapshot.getValue(Idea.class);
                    if ((item.getCreator().equals(firebaseUser.getUid()))) {
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