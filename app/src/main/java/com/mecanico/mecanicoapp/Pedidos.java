package com.mecanico.mecanicoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.mecanico.mecanicoapp.PagerControl;
import com.mecanico.mecanicoapp.R;

public class Pedidos extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem tabped, tabdir;
    PagerControl pagerControl;

    FirebaseAuth mAuth;
    DatabaseReference mDatebase;

    public Pedidos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedidos,container,false);

        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);
        tabdir = view.findViewById(R.id.tab_dir);
        tabped = view.findViewById(R.id.tab_ped);

        pagerControl = new PagerControl(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerControl);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pagerControl.notifyDataSetChanged();
                }

                if (tab.getPosition() == 1) {
                    pagerControl.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        return view;
    }
}
