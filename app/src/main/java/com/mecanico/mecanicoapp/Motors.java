package com.mecanico.mecanicoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.mecanico.mecanicoapp.servicios.pAutosActivity;
import com.mecanico.mecanicoapp.servicios.pBikeActivity;
import com.mecanico.mecanicoapp.servicios.pCargaActivity;
import com.mecanico.mecanicoapp.servicios.pDesvActivity;
import com.mecanico.mecanicoapp.servicios.pLavAutosActivity;
import com.mecanico.mecanicoapp.servicios.pMaquinariaActivity;
import com.mecanico.mecanicoapp.servicios.pMotosActivity;

import java.util.ArrayList;
import java.util.List;



public class Motors extends Fragment {

    public Motors() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_motors, container, false);

        ImageSlider imageSlider = view.findViewById(R.id.SliderM);
        Button mBtnAuto = view.findViewById(R.id.btn_car);
        Button mBtnMoto = view.findViewById(R.id.btn_mbike);
        Button mBtnBike = view.findViewById(R.id.btn_bike);
        Button mBtnMecha = view.findViewById(R.id.btn_maqui);
        Button mBtnCarga = view.findViewById(R.id.btn_carga);

        mBtnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), pAutosActivity.class));
                Toast.makeText(getContext(), "Autos", Toast.LENGTH_SHORT).show();
            }
        });

        mBtnMoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), pMotosActivity.class));
                Toast.makeText(getContext(), "Motocicletas", Toast.LENGTH_SHORT).show();
            }
        });

        mBtnBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), pBikeActivity.class));
                Toast.makeText(getContext(), "Motocicletas", Toast.LENGTH_SHORT).show();
            }
        });
        mBtnMecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), pMaquinariaActivity.class));
                Toast.makeText(getContext(), "Motocicletas", Toast.LENGTH_SHORT).show();
            }
        });
        mBtnCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), pCargaActivity.class));
                Toast.makeText(getContext(), "Motocicletas", Toast.LENGTH_SHORT).show();
            }
        });

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner_1,null));
        slideModels.add(new SlideModel(R.drawable.banner_2,null));
        slideModels.add(new SlideModel(R.drawable.banner_3,null));
        slideModels.add(new SlideModel(R.drawable.banner_4,null));
        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);
        return view;
    }
}

