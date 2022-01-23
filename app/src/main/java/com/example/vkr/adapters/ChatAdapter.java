package com.example.vkr.adapters;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkr.R;
import com.example.vkr.activitys.DictionaryActivity;
import com.example.vkr.model.Dictionary;
import com.example.vkr.retrofit.DictionaryApi;
import com.example.vkr.retrofit.RetrofitService;
import com.example.vkr.simple.Message;
import com.example.vkr.simple.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    private Context mContext;
    private List<Message> messageList;

    public TextView internalMessage;
    public TextView externalMessage;
    private String sourceLanguage;
    private String targetLanguage;
    DatabaseReference databaseReference;

    DictionaryApi dictionaryApi;
    RetrofitService retrofitService;
    FirebaseUser firebaseUser;

    private TextToSpeech TTS;


    public ChatAdapter(Context mContext, List<Message> mMessgs) {
        this.mContext = mContext;
        this.messageList =mMessgs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
         retrofitService = new RetrofitService();

         dictionaryApi = retrofitService.getRetrofit().create(DictionaryApi.class);

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                targetLanguage = user.getSpeakLanguage();
                sourceLanguage = user.getLearnLanguage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        TTS = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override public void onInit(int initStatus) {
                if (initStatus == TextToSpeech.SUCCESS) {
                    if (TTS.isLanguageAvailable(new Locale(Locale.getDefault().getLanguage()))
                            == TextToSpeech.LANG_AVAILABLE) {
                        TTS.setLanguage(new Locale(Locale.getDefault().getLanguage()));
                    } else {
                        TTS.setLanguage(Locale.US);
                    }
                }
            }
        });
        if(viewType==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.internal_messgae, parent, false);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    internalMessage = (TextView) view.findViewById(R.id.messageTextView);
              //      Toast.makeText(mContext, internalMessage.getText().toString(), android.widget.Toast.LENGTH_SHORT).show();
                    String utteranceId = UUID.randomUUID().toString();
                    TTS.speak(internalMessage.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, utteranceId);

                    return true;
                }
            });
            return new ChatAdapter.ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.external_messahe, parent, false);
            view.setOnClickListener(new DoubleClickListener() {
                @Override
                public void onDoubleClick() {
                    Toast.makeText(mContext, "Start saving", android.widget.Toast.LENGTH_SHORT).show();
                    translateAndSave(getLanguageCode(targetLanguage),getLanguageCode(sourceLanguage),externalMessage.getText().toString());
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    externalMessage = (TextView) view.findViewById(R.id.messageTextView);
                    String utteranceId = UUID.randomUUID().toString();
                    TTS.speak(externalMessage.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, utteranceId);

                    //      Toast.makeText(mContext, externalMessage.getText().toString(), android.widget.Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            return new ChatAdapter.ViewHolder(view);
        }
    }

    private void translateAndSave(int languageTarget, int languageSource, String text) {

        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(languageSource)
                .setTargetLanguage(languageTarget)
                .build();

        FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();

        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                translator.translate(text).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {

                        Dictionary dictionary = new Dictionary();

                        dictionary.setOwnerId(firebaseUser.getUid());
                        dictionary.setSourceLanguage(sourceLanguage);
                        dictionary.setSourcePhrase(text);
                        dictionary.setTranslateLanguage(targetLanguage);
                        dictionary.setTranslatedPhrase(s);

                        dictionaryApi.save(dictionary)
                                .enqueue(new Callback<Dictionary>() {
                                    @Override
                                    public void onResponse(Call<Dictionary> call, Response<Dictionary> response) {
                           //             Toast.makeText(NotebookActivity.this,"Success",Toast.LENGTH_LONG).show();
                                        Toast.makeText(mContext, "Added to dictionary", android.widget.Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onFailure(Call<Dictionary> call, Throwable t) {
             //                           Toast.makeText(NotebookActivity.this,"Server is unavalable",Toast.LENGTH_LONG).show();
                                        Toast.makeText(mContext, "Failed to save", android.widget.Toast.LENGTH_SHORT).show();

                                        Logger.getLogger(DictionaryActivity.class.getName()).log(Level.SEVERE,"error Saving");
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed to translate", android.widget.Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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

    public int getLanguageCode(String language){
        int languagecode =0;

        switch (language){
            case "English":
                languagecode = FirebaseTranslateLanguage.EN;
                break;
            case "Russian":
                languagecode = FirebaseTranslateLanguage.RU;
                break;
            case "Spanish":
                languagecode = FirebaseTranslateLanguage.ES;
                break;
            case "Chinese":
                languagecode = FirebaseTranslateLanguage.ZH;
                break;
            case "Italian":
                languagecode = FirebaseTranslateLanguage.IT;
                break;
            case "French":
                languagecode = FirebaseTranslateLanguage.FR;
                break;
            case "Germany":
                languagecode = FirebaseTranslateLanguage.DE;
                break;
        }
        return languagecode;
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
