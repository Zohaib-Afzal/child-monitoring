package com.example.childmonitoring.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.childmonitoring.R;
import com.example.childmonitoring.model.Child;

import java.util.ArrayList;

public class AllRecyclerViewAdapter extends RecyclerView.Adapter<AllRecyclerViewAdapter.ViewHolder> {
    // ArrayList<String> al;
    Context context;
    ArrayList<Child> childArrayList;

    public AllRecyclerViewAdapter(Context context, ArrayList<Child> arrayList) {
        // al = arrayList;
        this.context = context;
        childArrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_all_content_recyler_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Child child = childArrayList.get(position);
        holder.textViewName.setText(child.getChildName());
        if (child.isStatus()) {
            holder.textViewStatus.setText("Arrived");
            holder.textViewStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorArrived));
        } else {
            holder.textViewStatus.setText("On Way");
            holder.textViewStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorOnWay));

        }

    }

    @Override
    public int getItemCount() {
        return childArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewStatus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.all_child_name);
            textViewStatus = itemView.findViewById(R.id.child_status);
        }
    }
}
