package com.mecanico.mecanicoapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.mecanico.mecanicoapp.servicios.pAirActivity;
import com.mecanico.mecanicoapp.servicios.pCerrActivity;
import com.mecanico.mecanicoapp.servicios.pCompActivity;
import com.mecanico.mecanicoapp.servicios.pLavActivity;
import com.mecanico.mecanicoapp.servicios.pMuebActivity;
import com.mecanico.mecanicoapp.servicios.pNevActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        ImageSlider imageSlider = view.findViewById(R.id.SliderH);
        Button btnLav = (Button) view.findViewById(R.id.btn_lav);
        Button btnNev = (Button) view.findViewById(R.id.btn_nev);
        Button btnAir = (Button) view.findViewById(R.id.btn_air);
        Button btnCer= (Button) view.findViewById(R.id.btn_llav);
        Button btnMueb= (Button) view.findViewById(R.id.btn_mueble);
        Button btnOtros= (Button) view.findViewById(R.id.btn_otros);

        btnNev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

                if (networkInfo!=null && networkInfo.isConnected()){
                    Intent intent = new Intent(getActivity(), pNevActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Neveras", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "No se pudo conectar, verifique el acceso a internet e intente nuevante", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnLav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

                if (networkInfo!=null && networkInfo.isConnected()){
                    Intent intent = new Intent(getActivity(), pLavActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Lavadoras", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "No se pudo conectar, verifique el acceso a internet e intente nuevante", Toast.LENGTH_LONG).show();
                }


            }
        });
        btnAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

                if (networkInfo!=null && networkInfo.isConnected()){
                    Intent intent = new Intent(getActivity(), pAirActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Aires acondicionados", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "No se pudo conectar, verifique el acceso a internet e intente nuevante", Toast.LENGTH_LONG).show();
                }


            }
        });
        btnCer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

                if (networkInfo!=null && networkInfo.isConnected()){
                    Intent intent = new Intent(getActivity(), pCerrActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Cerrajeria", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "No se pudo conectar, verifique el acceso a internet e intente nuevante", Toast.LENGTH_LONG).show();
                }


            }
        });

        btnMueb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

                if (networkInfo!=null && networkInfo.isConnected()){
                    Intent intent = new Intent(getActivity(), pMuebActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Limpieza de muebles", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "No se pudo conectar, verifique el acceso a internet e intente nuevante", Toast.LENGTH_LONG).show();
                }


            }
        });

        btnOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

                if (networkInfo!=null && networkInfo.isConnected()){
                    Intent intent = new Intent(getActivity(), OtrosActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Computadores", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "No se pudo conectar, verifique el acceso a internet e intente nuevante", Toast.LENGTH_LONG).show();
                }


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
