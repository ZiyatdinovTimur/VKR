package com.example.vkr.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkr.AccordUserView;
import com.example.vkr.R;
import com.example.vkr.model.Dictionary;
import com.example.vkr.simple.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AccordUserAdapter extends RecyclerView.Adapter<AccordUserView> implements Filterable {
    private List<User> ideaList;
     private List<User> ideaFull ;
    private Context context;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String imageName;

    public AccordUserAdapter(List<User> matchesList,List<User> full, Context context) {
        this.ideaList = matchesList;
        this.context = context;
        ideaFull= full;
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
        holder.accordStatus.setText(ideaList.get(position).getStatus());
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

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                builder.setMessage(
//                        "Email:" + " " + ideaList.get(position).getEmailaddres()
//                        + "\n" + "\n" +
//                                "Номер телефона:" + " " +ideaList.get(position).getPhoneNumber()
//                        + "\n" + "\n" )
//                        .setTitle(ideaList.get(position).getName() + " " + ideaList.get(position).getSurname());
//
//
//                AlertDialog dialog = builder.create();
//                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                });
//                builder.show();
//
//                return true; }
//        });






    }

    @Override
    public int getItemCount() {
        return this.ideaList.size();
    }

    @Override
    public Filter getFilter() {
        return filteredList;
    }

    private Filter filteredList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<User> filteredDictionaries = new ArrayList<>();
            if(charSequence==null|| charSequence.length()==0)
                filteredDictionaries.addAll(ideaFull);
            else {
                Toast.makeText(context, "ideaFull.size()", Toast.LENGTH_SHORT).show();

                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (User record : ideaFull ){
                    if(record.getName().toLowerCase().contains(filterPattern)){
                        filteredDictionaries.add(record);
                    }
                    }
            }

            FilterResults results = new FilterResults();
            results.values = filteredDictionaries;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ideaList.clear();
            ideaList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

//    private void initList() {
//        ideaFull = new ArrayList<>(ideaList);
//    }

}
