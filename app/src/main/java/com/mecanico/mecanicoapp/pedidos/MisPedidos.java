package com.mecanico.mecanicoapp.pedidos;

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

import java.util.ArrayList;

public class MisPedidos extends Fragment {

    public MisPedidos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mis_pedidos, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_ped);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<Pedido> list = new ArrayList<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uID = mAuth.getUid();
        assert uID != null;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Pedido").child(uID);

        FirebaseRecyclerOptions<Pedido> options =
                new FirebaseRecyclerOptions.Builder<Pedido>()
                        .setQuery(database.orderByChild("date"), Pedido.class)
                        .build();
        PedidoAdapter adapter = new PedidoAdapter(options, getContext(), list);
        recyclerView.setAdapter(adapter);
        adapter.startListening();


        return view;
    }

}
