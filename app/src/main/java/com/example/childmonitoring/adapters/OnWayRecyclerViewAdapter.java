package com.example.childmonitoring.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.childmonitoring.R;
import com.example.childmonitoring.model.Child;

import java.util.ArrayList;

public class OnWayRecyclerViewAdapter extends RecyclerView.Adapter<OnWayRecyclerViewAdapter.ViewHolder> {
    ArrayList<Child> childArrayList;

    public OnWayRecyclerViewAdapter(ArrayList<Child> arrayList) {
        childArrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_name_display, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Child child = childArrayList.get(position);
        holder.textViewName.setText(child.getChildName());
    }

    @Override
    public int getItemCount() {
        return childArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.child_name);

        }
    }
}





