package com.ashswini.amura.Model;

import java.util.ArrayList;

public class EventModel {
    String id,eventname,date,time;
    ArrayList<String> participant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getParticipant() {
        return participant;
    }

    public void setParticipant(ArrayList<String> participant) {
        this.participant = participant;
    }
    public EventModel() {


    }
    public EventModel(String eventname, String date, String time, ArrayList<String> participant) {

        this.eventname = eventname;
        this.date = date;
        this.time = time;
        this.participant = participant;
    }
}
