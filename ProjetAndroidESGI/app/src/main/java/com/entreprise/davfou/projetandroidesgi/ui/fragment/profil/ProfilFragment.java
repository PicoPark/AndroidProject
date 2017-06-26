package com.entreprise.davfou.projetandroidesgi.ui.fragment.profil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.login.ConnectUser;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davidfournier on 26/06/2017.
 */

public class ProfilFragment extends Fragment {

    @BindView(R.id.btnLogout)
    Button btnLogout;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewFirstName)
    TextView textViewFirstName;
    ConnectUser connectUser;
    UserRealm userRealm;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    public static ProfilFragment newInstance() {
        return new ProfilFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        connectUser= new ConnectUser(getContext(),getActivity());



        userRealm = connectUser.getUserConnected();

        textViewName.setText(userRealm.getEmail());


        if(userRealm.getFirstName().equals("")){
            textViewFirstName.setText("faire appel");

            UserInfo userInfo = connectUser.getInfo(userRealm);

            
        }else{
            textViewFirstName.setText(userRealm.getFirstName());

        }
        System.out.println(userRealm.getToken());
    }


    @OnClick(R.id.btnLogout)
    public void clickLogout(){
        connectUser.logout();
    }

}