package com.example.nadiardhian.nadiaardhianie_1202154296_modul6;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PopotoanHomeTerbaru extends android.support.v4.app.Fragment{

    RecyclerView rv;//deklarasi recyclerview dengan nama variable rv
    DatabaseReference dataref; //deklarasi database reference dengan nama dataref
    ArrayList<DBPost> list; //deklarasi arraylist dengan nama variable list
    AdapterPost adapterPost; //deklarasi adapter dengan nama variable adapter post

    public PopotoanHomeTerbaru() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inisialisasi semua objek
        View v = inflater.inflate(R.layout.activity_popotoan_home_terbaru, container, false);
        rv = v.findViewById(R.id.rvhometerbaru);
        list = new ArrayList<>();
        dataref = FirebaseDatabase.getInstance().getReference().child("post");
        adapterPost = new AdapterPost(list, this.getContext());

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);  //Get jumlah kolom yang sesuai
        rv.setLayoutManager(new GridLayoutManager(this.getContext(),gridColumnCount)); //Menampilkan recyclerview
        rv.setAdapter(adapterPost); //menampilkan adapter

        dataref.addChildEventListener(new ChildEventListener() { //Event listener ketika value pada Firebase berubah
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DBPost cur = dataSnapshot.getValue(DBPost.class); //DBPost dataSnapshot.getValue dari class DBPost
                cur.key = dataSnapshot.getKey();  //dataSnapshot dari DBPost untuk getKey
                list.add(cur); //add list DBPost
                adapterPost.notifyDataSetChanged(); //set notifyDataSetChanged pada adapter
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
