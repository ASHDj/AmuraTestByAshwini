package com.ashswini.amura;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class EventList extends Fragment {
    private DatabaseReference mDatabase;
    ArrayList<String> selectedUserList= new ArrayList<>();
    List<EventModel> eventList=new ArrayList<>();
    RecyclerView recyclerView;
    EventAdapter mAdapter;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_event_list, container, false);
        initComponent(view);
        return view;
    }

    private void initComponent(View view) {
        recyclerView=view.findViewById(R.id.RVList);
        fab=view.findViewById(R.id.fab_add);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Create_Event.class);
                startActivity(i);
                //finish();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mDatabase.child("event").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List event=new ArrayList<>();
                eventList.clear();
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}