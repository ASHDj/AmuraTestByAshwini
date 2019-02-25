package com.ashswini.amura;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ashswini.amura.Adapter.EventAdapter;
import com.ashswini.amura.Model.EventModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventList extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ArrayList<String> selectedUserList= new ArrayList<>();
    List<EventModel> eventList=new ArrayList<>();
    RecyclerView recyclerView;
    EventAdapter mAdapter;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        recyclerView=findViewById(R.id.RVList);
        fab=findViewById(R.id.fab_add);

        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EventList.this,Create_Event.class);
                startActivity(i);
                //finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mDatabase.child("event").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List event=new ArrayList<>();
                for (DataSnapshot eventSnapshot:dataSnapshot.getChildren())
                {
                    EventModel eventModel=eventSnapshot.getValue(EventModel.class);
                    event.add(eventModel);
                    eventList.add(eventModel);
                    Collections.reverse(eventList);

                }
                setAdapter();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void setAdapter() {
        mAdapter = new EventAdapter(eventList);
        Log.e("11 event size ",""+eventList.size());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}