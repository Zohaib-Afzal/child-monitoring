package com.example.childmonitoring.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.childmonitoring.R;
import com.example.childmonitoring.adapters.AllRecyclerViewAdapter;
import com.example.childmonitoring.model.Child;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class AllFragment extends Fragment {
    HashMap<String, Object> data;
    RecyclerView recyclerView;
    AllRecyclerViewAdapter adapter;
    ArrayList<Child> childArrayList = new ArrayList<>();
    Integer statusTemp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        recyclerView = view.findViewById(R.id.all_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();
        return view;
    }

    private void getData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Children")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult()!=null) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Child fireBaseData = new Child();
                                    data = (HashMap<String, Object>) document.getData();
                                    //saving status data from firebase into a temporary variable to convert int status to boolean
                                    statusTemp = Integer.valueOf(data.get("Status").toString());
                                    if (statusTemp == 0) {
                                        fireBaseData.setStatus(false);
                                    } else {
                                        fireBaseData.setStatus(true);
                                    }
                                    //getting name from firebase
                                    fireBaseData.setChildName(data.get("CName").toString());
                                    // passing name and status from fragment to adapter
                                    childArrayList.add(new Child(fireBaseData.getChildName(), fireBaseData.isStatus()));
                                    Log.d("Data", document.getId() + " => " + document.getData());
                                }
                            }
                            adapter = new AllRecyclerViewAdapter(getActivity(), childArrayList);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Log.w("error", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}