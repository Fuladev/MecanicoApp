package com.mecanico.mecanicoapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mecanico.mecanicoapp.direction.MisDirecciones;
import com.mecanico.mecanicoapp.pedidos.MisPedidos;

public class PagerControl extends FragmentPagerAdapter {
    int numoftabs;

    public PagerControl(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numoftabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MisPedidos();
            case 1:
                return new MisDirecciones();
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return numoftabs;
    }
}