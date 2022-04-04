package com.example.Sleepy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Sleepy.R;

import java.util.List;

public class TimeCardsAdapter extends RecyclerView.Adapter<TimeCardsAdapter.ViewHolder>{

    private final List<TimeCards> timeCards;
    View view;

    public TimeCardsAdapter(List<TimeCards> timeCards) {
        this.timeCards = timeCards;
    }

    @Override
    public TimeCardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TimeCards timeCards = this.timeCards.get(position);
        holder.titleView.setText(timeCards.getTitle());
        holder.secText.setText(timeCards.getSecText());
    }

    @Override
    public int getItemCount() {
        return timeCards.size();
    }

    public void delete(int position){
        timeCards.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView titleView, secText;
        ViewHolder(View view){
            super(view);
            titleView = view.findViewById(R.id.tvTitleCard);
            secText = view.findViewById(R.id.tvSecCard);
        }
    }
}