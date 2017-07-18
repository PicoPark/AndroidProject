package com.entreprise.davfou.projetandroidesgi.ui.fragment.profil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.entreprise.davfou.projetandroidesgi.R;
import com.entreprise.davfou.projetandroidesgi.bussiness.user.ManageUser;
import com.entreprise.davfou.projetandroidesgi.data.modelLocal.UserRealm;
import com.entreprise.davfou.projetandroidesgi.data.modelRest.User;
import com.entreprise.davfou.projetandroidesgi.ui.activity.MenuActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by davidfournier on 26/06/2017.
 */

public class ProfilFragment extends Fragment {

    @BindView(R.id.btnLogout)
    ImageButton btnLogout;
    @BindView(R.id.textViewEmail)
    TextView textViewEmail;
    @BindView(R.id.textViewFirstName)
    TextView textViewFirstName;
    @BindView(R.id.textViewLastName)
    TextView textViewLastName;
    @BindView(R.id.btn_update_profil)
    Button btn_update_profil;
    ManageUser manageUser;
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
        ButterKnife.bind(this, view);
        manageUser = new ManageUser(getContext(), getActivity());


        userRealm = MenuActivity.getUser(getActivity());

        if (userRealm.getEmail() != null)
            textViewEmail.setText(userRealm.getEmail());
        if (userRealm.getFirstName() != null)
            textViewFirstName.setText(userRealm.getFirstName());
        if (userRealm.getLastName() != null)
            textViewLastName.setText(userRealm.getLastName());


    }

    @OnClick(R.id.btnLogout)
    public void clickLogout() {
        manageUser.logout();
    }

    @OnClick(R.id.btn_update_profil)
    public void clickBtn_update_profil(){
        System.out.println("userRealm.getPassword(): "+userRealm.getPassword());
        System.out.println("userRealm.getEmail(): "+textViewEmail.getText().toString());

        manageUser.updateUser(userRealm, new User(textViewEmail.getText().toString(), userRealm.getPassword(), textViewFirstName.getText().toString(),textViewLastName.getText().toString()));
    }

}