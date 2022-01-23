package com.example.vkr;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vkr.activitys.ProfileActivity;
import com.example.vkr.simple.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    private ImageView profileImage;
    TextView nameTextView;
    TextView emailTextView ;
    TextView phoneNumberTextView;
    TextView followersTextView;
    TextView followingsTextView;
    TextView speakLanguageTextView;
    TextView learnLanguageTextView;
    TextView statusTextView;

    String name;
    String email;
    String phone;
    String speakL;
    String LearnL;
    String followers;
    String followings;
    String status;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_first, container, false);
        nameTextView = (TextView) inf.findViewById(R.id.nameTextProfile);
        emailTextView = (TextView) inf.findViewById(R.id.email_prof_text);
        phoneNumberTextView = (TextView) inf.findViewById(R.id.phone_prof_text);
        speakLanguageTextView = (TextView) inf.findViewById(R.id.speak_prof_text);
        learnLanguageTextView = (TextView) inf.findViewById(R.id.learn_prof_text);
        followersTextView = (TextView) inf.findViewById(R.id.followersTextView);
        followingsTextView = (TextView) inf.findViewById(R.id.followingsTextView);
        statusTextView = (TextView) inf.findViewById(R.id.statusTextProfile);
        profileImage = (ImageView) inf.findViewById(R.id.profileImageM);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                name = user.getName() + " " + user.getSurname();
                email = user.getEmailaddres();
                phone = "+"+user.getCountryCode() + " "+ user.getPhoneNumber();
                speakL = user.getSpeakLanguage();
                LearnL = user.getLearnLanguage();
                followers = user.getFollowers();
                followings = user.getFollowings();
                status = user.getStatus();


                nameTextView.setText(name);
                emailTextView.setText(email);
                phoneNumberTextView.setText(phone);
                speakLanguageTextView.setText(speakL);
                learnLanguageTextView.setText(LearnL);
                followersTextView.setText(followers);
                followingsTextView.setText(followings);
                statusTextView.setText(status);

                addImage(user.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return inf;
    }


    private void addImage(String imageName) {
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        imageName+=".jpg";
        StorageReference imageRef = storageReference.child("images").child(imageName);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(profileImage);
            }
        });
    }



//    public void updateSpecialization(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setTitle("Введите новую специализацию");
//        final EditText input = new EditText(ProfileActivity.this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        input.setLayoutParams(lp);
//        builder.setView(input);
//
//        AlertDialog dialog = builder.create();
//        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//                databaseReference.child("Users").child(firebaseUser.getUid()).child("specialization").setValue(input.getText().toString());
//
//
//            }
//
//
//        });
//        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//
//
//
//            }
//
//
//        });
//        builder.show();
//    }

//    public void updateNumber(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setTitle("Введите новый номер телефона");
//        final EditText input = new EditText(ProfileActivity.this);
//        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        input.setLayoutParams(lp);
//        builder.setView(input);
//
//        AlertDialog dialog = builder.create();
//        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//                databaseReference.child("Users").child(firebaseUser.getUid()).child("phoneNumber").setValue(input.getText().toString());
//
//
//            }
//
//
//        });
//        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//
//
//
//            }
//
//
//        });
//        builder.show();
//    }
}