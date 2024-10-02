package com.mecanico.mecanicoapp.direction;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mecanico.mecanicoapp.R;
import com.mecanico.mecanicoapp.direction.Direction;
import com.mecanico.mecanicoapp.direction.DirectionAdapter;

import java.util.ArrayList;

public class MisDirecciones extends Fragment {

    public MisDirecciones() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private DirectionAdapter adapter;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private ArrayList<Direction> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_direcciones,container,false);

        recyclerView = view.findViewById(R.id.recycle_dir);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        String uID = mAuth.getUid();
        database = FirebaseDatabase.getInstance().getReference().child("Users").child(uID).child("Direcciones");

        FirebaseRecyclerOptions<Direction> options =
                new FirebaseRecyclerOptions.Builder<Direction>()
                        .setQuery(database, Direction.class)
                        .build();
        adapter = new DirectionAdapter(options, getContext(),list);
        recyclerView.setAdapter(adapter);
        adapter.startListening();


        return view;
    }
}
