package com.example.yoyoiq.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.yoyoiq.Fragment.ARFragment;
import com.example.yoyoiq.Fragment.BATFragment;
import com.example.yoyoiq.Fragment.BOWLFragment;
import com.example.yoyoiq.Fragment.WKFragment;
import com.example.yoyoiq.InSideContestActivityFragments.AllSelectedPlayerFromServer;

import java.util.List;

public class PageAdapterPlayer extends FragmentPagerAdapter {
    int tabCount;
    String match_id = "";
    String matchA = "";
    String matchB = "";
    String logo_url_a = "";
    String logo_url_b = "";
    String AllSelectedData = "";
    String abbr = "";

    public PageAdapterPlayer(@NonNull FragmentManager fm, int behavior, String match_id, String matchA, String matchB, String logo_url_a, String logo_url_b, List<AllSelectedPlayerFromServer> AllSelectedData, String abbr) {
        super(fm, behavior);
        tabCount = behavior;
        this.match_id = match_id;
        this.matchA = matchA;
        this.matchB = matchB;
        this.logo_url_a = logo_url_a;
        this.logo_url_b = logo_url_b;
        this.AllSelectedData = AllSelectedData.toString();
        this.abbr = abbr;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putString("match_id", match_id);
                bundle.putString("matchA", matchA);
                bundle.putString("matchB", matchB);
                bundle.putString("AllSelectedData", AllSelectedData);
                bundle.putString("abbr", abbr);
                WKFragment wkFragment = new WKFragment();
                wkFragment.setArguments(bundle);
                return wkFragment;
            case 1:
                Bundle bundle1 = new Bundle();
                bundle1.putString("match_id", match_id);
                bundle1.putString("matchA", matchA);
                bundle1.putString("matchB", matchB);
                bundle1.putString("AllSelectedData", AllSelectedData);
                bundle1.putString("abbr", abbr);
                BATFragment batFragment = new BATFragment();
                batFragment.setArguments(bundle1);
                return batFragment;
            case 2:
                Bundle bundle2 = new Bundle();
                bundle2.putString("match_id", match_id);
                bundle2.putString("matchA", matchA);
                bundle2.putString("matchB", matchB);
                bundle2.putString("AllSelectedData", AllSelectedData);
                bundle2.putString("abbr", abbr);
                ARFragment arFragment = new ARFragment();
                arFragment.setArguments(bundle2);
                return arFragment;
            case 3:
                Bundle bundle3 = new Bundle();
                bundle3.putString("match_id", match_id);
                bundle3.putString("matchA", matchA);
                bundle3.putString("matchB", matchB);
                bundle3.putString("AllSelectedData", AllSelectedData);
                bundle3.putString("abbr", abbr);
                BOWLFragment bowlFragment = new BOWLFragment();
                bowlFragment.setArguments(bundle3);
                return bowlFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
