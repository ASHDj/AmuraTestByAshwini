package com.ashswini.amura;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ashswini.amura.Adapter.EventAdapter;
import com.ashswini.amura.Model.EventModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Create_Event extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;

    FirebaseAuth mFirebaseAuth;

    TextView txtparticipants;
    EditText edteventname,edt_discription;
    TextView edtdate,edttime;;
    RecyclerView recyclerView;
    EventAdapter mAdapter;
    Button btnsubmit,btnLodEvent;
    private int mYear, mMonth, mDay, mHour, mMinute;
    List<EventModel> eventList=new ArrayList<>();
    String  StrCity="";
    ArrayList<String> selectedUserList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__event);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        initComponent();

    }

    private void initComponent() {

        txtparticipants=findViewById(R.id.txtparticipants);
        edteventname=findViewById(R.id.edteventname);
        edt_discription=findViewById(R.id.edt_discription);
        edtdate=findViewById(R.id.edtdate);
        edttime=findViewById(R.id.edttime);
        btnsubmit=findViewById(R.id.btnsubmit);
        btnLodEvent=findViewById(R.id.btnLodEvent);
        txtparticipants.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
        btnLodEvent.setOnClickListener(this);
        edtdate.setOnClickListener(this);
        edttime.setOnClickListener(this);
    }

    public void withMultiChoiceItems(View view, String Title) {
          final String[] items = {"ashvini.jadhav08@gmail.com",
                  "sona.ash@gmail.com", "arun@gmail.com",
                  "Nikita@gmail.com"};
        final ArrayList<Integer> selectedList = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(Create_Event.this);
        builder.setTitle("select Participants ");
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            selectedList.add(which);
                        } else if (selectedList.contains(which)) {
                            selectedList.remove(which);
                        }
                    }
                });

        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            //    ArrayList<String> selectedStrings = new ArrayList<>();
                selectedUserList.clear();
                String strselecteditm="";
                for (int j = 0; j < selectedList.size(); j++) {
                    selectedUserList.add(items[selectedList.get(j)]);
                    strselecteditm+=  selectedUserList.get(j)+"\n";
                }
                txtparticipants.setText(strselecteditm);
            }
        });

        builder.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtparticipants:
                withMultiChoiceItems(v,"Abc");
                break;
            case R.id.edtdate:
                setDate();

                break;
            case R.id.edttime:
                setTime();

                break;
            case R.id.btnsubmit:
                CreateEvent();

                break;

        }
    }

    private void CreateEvent() {

        String strname,strpariticipant,strdate,strtime;
        strdate=edtdate.getText().toString();
        strname=edteventname.getText().toString().trim();
        strtime=edttime.getText().toString().trim();
        if (TextUtils.isEmpty(strname))
        {
            Toast.makeText(getApplicationContext(),"Please enter Name",Toast.LENGTH_SHORT).show();
        }else
        if (TextUtils.isEmpty(strdate))
        {
            Toast.makeText(getApplicationContext(),"Please enter date",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(strtime))
        {
          Toast.makeText(getApplicationContext(),"Please enter date",Toast.LENGTH_SHORT).show();
        }
        else if (selectedUserList.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please select participant",Toast.LENGTH_SHORT).show();
        }else {
            EventModel eventModel = new EventModel();
            eventModel.setId(mDatabase.child("event").push().getKey());
            eventModel.setEventname(strname);
            eventModel.setDate(strdate);
            eventModel.setTime(strtime);
            eventModel.setParticipant(selectedUserList);
            mDatabase.child("event").child(eventModel.getId()).setValue(eventModel);
            Toast.makeText(this, "Event Added Successfully ", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    private void setTime() {
        final Calendar c1 = Calendar.getInstance();
        mHour = c1.get(Calendar.HOUR_OF_DAY);
        mMinute = c1.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Create_Event.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        edttime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void setDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Create_Event.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edtdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
