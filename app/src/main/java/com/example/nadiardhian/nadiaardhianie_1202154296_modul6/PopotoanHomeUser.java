package com.example.nadiardhian.nadiaardhianie_1202154296_modul6;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PopotoanHomeUser extends android.support.v4.app.Fragment{
    DatabaseReference ref; //deklarasi database
    AdapterPost adapter; //deklarasi adapter
    ArrayList<DBPost> list; //deklarasi arraylist yang bernama list
    RecyclerView rv; //deklarasi recyclerview dengan nama variable rv

    public PopotoanHomeUser() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inisialisasi semua objek pada database
        View v = inflater.inflate(R.layout.activity_popotoan_home_user, container, false);
        ref = FirebaseDatabase.getInstance().getReference().child("post");
        list = new ArrayList<>();
        adapter = new AdapterPost(list, this.getContext());
        rv = v.findViewById(R.id.rvhomeuser);

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count); //Get jumlah kolom yang sesuai
        rv.setLayoutManager(new GridLayoutManager(this.getContext(),gridColumnCount));  //Menampilkan recyclerview
        rv.setAdapter(adapter); //get nama adapter
        ref.addChildEventListener(new ChildEventListener() { //Event listener ketika data pada Firebase berubah
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DBPost cur = dataSnapshot.getValue(DBPost.class);  //DBPost dataSnapshot.getValue dari class DBPost
                if (cur.getUser().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){ //kondisi getUser() apakah equals dengan authentication firebase dengan getCurrentUser dan getEmail
                    cur.setKey(dataSnapshot.getKey()); //dataSnapshot dari DBPost untuk getKey
                    list.add(cur); //add list DBPost
                    adapter.notifyDataSetChanged(); //set notifyDataSetChanged pada adapter
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
        return v;
    }
}
