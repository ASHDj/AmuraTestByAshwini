package com.ashswini.amura;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.ashswini.amura.Adapter.EventAdapter;
import com.ashswini.amura.Model.EventModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Monthy_view extends Fragment {

    public Monthy_view() {
        // Required empty public constructor
    }

    Spinner spinnermonth;
    Button btnok;
    List<EventModel> eventList=new ArrayList<>();
    RecyclerView recyclerView;
    EventAdapter mAdapter;
    private DatabaseReference mDatabase;
    int mYear,mMonth,mDay;
    String Month="1";
    String strstart="2019-01-01",strend="2019-01-31";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_monthy_view, container, false);
        spinnermonth=view.findViewById(R.id.spmonth);
        btnok=view.findViewById(R.id.btnok);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView=view.findViewById(R.id.RVList);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("11 ",Month);
                String strmonth=spinnermonth.getSelectedItem().toString();
                Month=spinnermonth.getSelectedItem().toString();
                if (strmonth.equals("January"))
                {

                    strstart="2019-01-01";
                    strend="2019-01-31";

                }else if(strmonth.equals("February"))
                {


                    strstart="2019-02-01";
                    strend="2019-02-31";
                }
                else if(strmonth.equals("March"))
                {


                    strstart="2019-03-01";
                    strend="2019-03-31";
                }
                else if(strmonth.equals("April"))
                {


                    strstart="2019-12-01";
                    strend="2019-12-31";
                }
              else if(strmonth.equals("May"))
                {


                    strstart="2019-12-01";
                    strend="2019-12-31";
                }
                else if(strmonth.equals("June"))
                {


                    strstart="2019-12-01";
                    strend="2019-12-31";
                }
                else if(strmonth.equals("July"))
                {


                    strstart="2019-12-01";
                    strend="2019-12-31";
                }
                else if(strmonth.equals("Aug"))
                {


                    strstart="2019-12-01";
                    strend="2019-12-31";
                }
                else if(strmonth.equals("September"))
                {

                    strstart="01-09-2019";
                    strend="31-09-2019";
                }
                else if(strmonth.equals("October"))
                {


                    strstart="2019-12-01";
                    strend="2019-12-31";
                }
                else if(strmonth.equals("November"))
                {


                    strstart="2019-12-01";
                    strend="2019-12-31";
                }
                else if(strmonth.equals("December"))
                {

                    strstart="2019-12-01";
                    strend="2019-12-31";
                }
                LoadData(strstart,strend);
            }
        });
        LoadData(strstart,strend);
        return view;
    }
    private void LoadData(String strstart,String strednd) {
        eventList.clear();
        Log.e("date ",strstart+":"+strednd);
        mDatabase.child("event").orderByChild("date").startAt(strstart).endAt(strednd).addValueEventListener(new ValueEventListener() {
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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
