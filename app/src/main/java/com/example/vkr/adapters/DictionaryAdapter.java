package com.example.vkr.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkr.R;
import com.example.vkr.RcordView;
import com.example.vkr.model.Dictionary;
import com.example.vkr.simple.Note;
import com.example.vkr.simple.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DictionaryAdapter extends RecyclerView.Adapter<RcordView> implements Filterable {
    private List<Dictionary> records;
    private List<Dictionary> recordsFull;
    private Context context;



    public DictionaryAdapter(List<Dictionary> records, Context context) {
        this.records = records;
        this.context = context;
        recordsFull = new ArrayList<>(records);
    }

    @Override
    public RcordView onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item_layout, parent, false);
//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutView.setLayoutParams(lp);
        RcordView rcv = new RcordView(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(RcordView holder, int position) {
        holder.targetText.setText(records.get(position).getSourcePhrase());
        holder.sourceText.setText(records.get(position).getTranslatedPhrase());


//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                builder.setMessage(
//                        "Описание:" + " " + records.get(position).getDescription()
//                              )
//                        .setTitle(records.get(position).getTopic());
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
        return this.records.size();
    }

    @Override
    public Filter getFilter() { ;
        return filteredList;
    }

    private Filter filteredList = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Dictionary> filteredDictionaries = new ArrayList<>();
            if(charSequence==null|| charSequence.length()==0)
                filteredDictionaries.addAll(recordsFull);
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Dictionary record : recordsFull )
                    if(record.getSourcePhrase().toLowerCase().contains(filterPattern)){
                        filteredDictionaries.add(record);
                    }
            }

            FilterResults results = new FilterResults();
            results.values = filteredDictionaries;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        records.clear();
        records.addAll((List)filterResults.values);
        notifyDataSetChanged();
        }
    };
}
