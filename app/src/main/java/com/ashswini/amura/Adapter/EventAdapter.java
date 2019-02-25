package com.ashswini.amura.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashswini.amura.Model.EventModel;
import com.ashswini.amura.R;

import java.util.List;

public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

private List<EventModel> eventList;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name, date, time,participant;

    public MyViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.txtname);
        date = (TextView) view.findViewById(R.id.txtdate);
        time = (TextView) view.findViewById(R.id.txttime);
        participant = (TextView) view.findViewById(R.id.txtparticipants);
    }
}


    public EventAdapter(List<EventModel> eventList) {
        this.eventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventModel eventModel = eventList.get(position);
        holder.name.setText(eventModel.getEventname());
        holder.date.setText(eventModel.getDate());
        holder.time.setText(eventModel.getTime());
        holder.participant.setText(eventModel.getParticipant().toString());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
    public void updateList(List events) {
        // Allow recyclerview animations to complete normally if we already know about data changes
        if (events.size() != this.eventList.size() || !this.eventList.containsAll(events)) {
            this.eventList = events;
            notifyDataSetChanged();
        }
    }
}