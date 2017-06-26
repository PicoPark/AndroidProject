package com.entreprise.davfou.projetandroidesgi.ui.fragment.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.entreprise.davfou.projetandroidesgi.R;

/**
 * Created by davidfournier on 26/06/2017.
 */

public class ListNewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_news, container, false);
    }

    public static ListNewsFragment newInstance() {
        return new ListNewsFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("zmksdjvpmsvnb^msrùijnvpmqrsjbnv");

    }


}