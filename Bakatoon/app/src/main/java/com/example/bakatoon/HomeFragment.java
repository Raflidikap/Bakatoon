package com.example.bakatoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    View myFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment =  inflater.inflate(R.layout.fragment_home, container, false);
//        addFragment();
        tabLayout = myFragment.findViewById(R.id.tab_layout);
        viewPager = myFragment.findViewById(R.id.view_pager);
        return myFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new MbtiTestFragment(), "MBTI TEST");
        adapter.addFragment(new MbtiFragment(), "MBTI INFO");

        viewPager.setAdapter(adapter);
    }

//    private void addFragment() {
//        tabLayout = myFragment.findViewById(R.id.tab_layout);
//        viewPager = myFragment.findViewById(R.id.view_pager);
//        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new MbtiTestFragment(), "MBTI test");
//        adapter.addFragment(new MbtiFragment(), "MBTI ");
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);
//    }
}
