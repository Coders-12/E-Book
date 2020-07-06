package com.example.e_book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference databaseReference;
    List<String>title_list,content_list;
    ArrayAdapter<String> adapter;
    EBook eBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.ListView);
        databaseReference= FirebaseDatabase.getInstance().getReference("e-book");

        eBook=new EBook();
        title_list=new ArrayList<>();
        content_list=new ArrayList<>();
        adapter=new ArrayAdapter<>(this,R.layout.item,R.id.item_txt,title_list);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                title_list.clear();
                content_list.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    eBook=ds.getValue(EBook.class);
                    if (eBook != null) {
                        title_list.add(eBook.getTitle());
                    }
                    if (eBook != null) {
                        content_list.add(eBook.getContent());
                    }

                }
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(MainActivity.this,Next.class);
                        String p=content_list.get(position);
                        intent.putExtra("key",p);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}