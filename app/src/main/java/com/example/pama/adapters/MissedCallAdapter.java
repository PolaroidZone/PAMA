package com.example.pama.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pama.R;
import com.example.pama.objects.MissedCallItem;

import java.util.List;

// MissedCallAdapter.java
public class MissedCallAdapter extends RecyclerView.Adapter<MissedCallAdapter.MissedCallViewHolder> {

    private List<MissedCallItem> missedCallsList;

    public MissedCallAdapter(List<MissedCallItem> missedCallsList) {
        this.missedCallsList = missedCallsList;
    }

    @NonNull
    @Override
    public MissedCallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_missed_call, parent, false);
        return new MissedCallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MissedCallViewHolder holder, int position) {
        MissedCallItem missedCall = missedCallsList.get(position);
        holder.textViewContactName.setText(missedCall.getContactName());
        // Bind other missed call data to the corresponding views here
        // ...
    }

    @Override
    public int getItemCount() {
        return missedCallsList.size();
    }

    public static class MissedCallViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewContactName;
        // Add other views for missed call information

        public MissedCallViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContactName = itemView.findViewById(R.id.textViewContactName);
            // Initialize other views for missed call information
            // ...
        }
    }
}
