package com.example.c_w.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c_w.AccordUserView;
import com.example.c_w.R;
import com.example.c_w.accordIdeadView;
import com.example.c_w.activitys.MessengerActivity;
import com.example.c_w.simple.Idea;
import com.example.c_w.simple.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AccordUserAdapter extends RecyclerView.Adapter<AccordUserView> {
    private List<User> ideaList;
    private Context context;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String imageName;

    public AccordUserAdapter(List<User> matchesList, Context context) {
        this.ideaList = matchesList;
        this.context = context;
    }

    @Override
    public AccordUserView onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.accorded_user, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        AccordUserView rcv = new AccordUserView(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull AccordUserView holder, int position) {
        holder.accordTopic.setText(ideaList.get(position).getName() + " " + ideaList.get(position).getSurname());
        holder.id = ideaList.get(position).getId();
        imageName = ideaList.get(position).getEmail();
        imageName += ".jpg";
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        StorageReference imageRef = storageReference.child("images").child(imageName);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).into(holder.accordImage);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage(
                        "Email:" + " " + ideaList.get(position).getEmailaddres()
                        + "\n" + "\n" +
                                "Номер телефона:" + " " +ideaList.get(position).getPhoneNumber()
                        + "\n" + "\n" +
                                "Специализация:" + " " +ideaList.get(position).getSpecialization()
                        + "\n" )
                        .setTitle(ideaList.get(position).getName() + " " + ideaList.get(position).getSurname());


                AlertDialog dialog = builder.create();
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.show();

                return true; }
        });






    }

    @Override
    public int getItemCount() {
        return this.ideaList.size();
    }
}
