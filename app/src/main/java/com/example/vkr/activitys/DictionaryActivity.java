package com.example.vkr.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.vkr.R;
import com.example.vkr.adapters.DictionaryAdapter;
import com.example.vkr.model.Dictionary;
import com.example.vkr.retrofit.DictionaryApi;
import com.example.vkr.retrofit.RetrofitService;
import com.example.vkr.simple.Note;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DictionaryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DictionaryAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    String currentId;

    SearchView searchView;

    private String cusrrentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);

        currentId = FirebaseAuth.getInstance().getCurrentUser().getUid();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = (SearchView) findViewById(R.id.search_view);





        cusrrentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recyclerView = (RecyclerView) findViewById(R.id.res_dict);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(DictionaryActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        initRecords( );

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

        //getUserMatchId();


    }

    private void initializeComponents() {
        RetrofitService retrofitService = new RetrofitService();

        DictionaryApi dictionaryApi = retrofitService.getRetrofit().create(DictionaryApi.class);

        Dictionary dictionary = new Dictionary();

        dictionary.setOwnerId("a");
        dictionary.setSourceLanguage("ru");
        dictionary.setSourcePhrase("Почему?");
        dictionary.setTranslatedPhrase("it");
        dictionary.setTranslatedPhrase("Perke?");

        dictionaryApi.save(dictionary)
                .enqueue(new Callback<Dictionary>() {
                    @Override
                    public void onResponse(Call<Dictionary> call, Response<Dictionary> response) {
                        Toast.makeText(DictionaryActivity.this,"Success",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<Dictionary> call, Throwable t) {
                        Toast.makeText(DictionaryActivity.this,"Server is unavalable",Toast.LENGTH_LONG).show();
                        Logger.getLogger(DictionaryActivity.class.getName()).log(Level.SEVERE,"error");
                    }
                });

    }

    private void initRecords( ) {
        RetrofitService retrofitService = new RetrofitService();


        DictionaryApi dictionaryApi = retrofitService.getRetrofit().create(DictionaryApi.class);
        dictionaryApi.getAllDictionary()
                .enqueue(new Callback<List<Dictionary>>() {
                    @Override
                    public void onResponse(Call<List<Dictionary>> call, Response<List<Dictionary>> response) {
                        initDictionary(response.body());
                        Toast.makeText(DictionaryActivity.this,"asd",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<List<Dictionary>> call, Throwable t) {
                        Toast.makeText(DictionaryActivity.this,"Failed to load dictionary records",Toast.LENGTH_LONG).show();

                    }
                });

    }

    private void initDictionary(List<Dictionary> body) {
        List<Dictionary> arrayList = new ArrayList<>() ;

        for (Dictionary dictionary : body) {
            if(dictionary.getOwnerId().equals(currentId))
                arrayList.add(dictionary);
        }
        adapter = new DictionaryAdapter(arrayList, DictionaryActivity.this);
        recyclerView.setAdapter(adapter);


    }

//    private void getUserMatchId() {
//
//        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Notes");
//        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    for(DataSnapshot match : snapshot.getChildren()){
//                        FetchMatchInformation(match.getKey());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void FetchMatchInformation(String key) {
//        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Notes").child(key);
//        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    Note item = dataSnapshot.getValue(Note.class);
//                    if(item.getCreator().equals(cusrrentUserID)) {
//                        records.add(item);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }


//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.add_menu, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent intent= new Intent(NotebookActivity.this,AddNoteActivity.class);
//        startActivity(intent);
//        return super.onOptionsItemSelected(item);
//    }


}