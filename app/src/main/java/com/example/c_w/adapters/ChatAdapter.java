package com.example.c_w.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c_w.R;
import com.example.c_w.simple.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    private Context mContext;
    private List<Message> messageList;

    FirebaseUser firebaseUser;

    public ChatAdapter(Context mContext, List<Message> mMessgs) {
        this.mContext = mContext;
        this.messageList =mMessgs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(viewType==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.internal_messgae, parent, false);
            return new ChatAdapter.ViewHolder(view);
        }
        else {

            View view = LayoutInflater.from(mContext).inflate(R.layout.external_messahe, parent, false);
            return new ChatAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {

        Message message = messageList.get(position);
        ViewHolder myHolder = (ViewHolder) holder;
        myHolder.messageTextView.setText(message.getBody());




    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        public TextView messageTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView=itemView.findViewById(R.id.messageTextView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(messageList.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }
    }
}
