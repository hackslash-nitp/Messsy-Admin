package com.hackslash.messsyadmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hackslash.messsyadmin.Adapters.SectionPageAdapter;
import com.hackslash.messsyadmin.R;

public class AdminActivityFragment extends Fragment {
    private SectionPageAdapter sectionPageAdapter;


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_activity,container,false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
           return view;
    }


    private void setupViewPager(ViewPager viewPager){

        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());
        adapter.addFragment(new AdminDMsFragment(),"DMs");
        adapter.addFragment(new AdminTalkFragment(),"Admin's Talk");
//        adapter.addFragment(new AdminMessageMessFragment(),"Message Mess");
        viewPager.setAdapter(adapter);

    }
}
