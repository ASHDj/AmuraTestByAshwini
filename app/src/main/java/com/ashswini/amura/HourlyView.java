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
public class HourlyView extends Fragment {
    EditText edtdate;
    Button btnok;
    List<EventModel> eventList=new ArrayList<>();
    RecyclerView recyclerView;
    EventAdapter mAdapter;
    private DatabaseReference mDatabase;
    int mYear,mMonth,mDay;
    public HourlyView() {
        // Required empty public constructor
    }

    String Date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_hourly_view, container, false);
        edtdate=view.findViewById(R.id.edtdate);
        btnok=view.findViewById(R.id.btnok);
        Date=AppUtil.FetchCurrentDate();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView=view.findViewById(R.id.RVList);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("11 ",Date);
                LoadData(Date);
            }
        });
        edtdate.setText(Date);
        edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Date=AppUtil.GetSTRingFRomStringDate(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth,"yyyy-MM-dd");
                                edtdate.setText(Date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


            }
        });
        LoadData(Date);
        return view;
    }
//mDatabase.child("TripSummry").orderByChild("attime").equalTo(Selecteddate()).
    private void LoadData(String date) {

        eventList.clear();
        mDatabase.child("event").orderByChild("date").equalTo(date).addValueEventListener(new ValueEventListener() {
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
