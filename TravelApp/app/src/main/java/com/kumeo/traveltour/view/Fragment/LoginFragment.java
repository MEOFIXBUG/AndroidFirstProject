package com.kumeo.traveltour.view.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.kumeo.traveltour.R;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    private TextView registerTV;

    private EditText emailInput, passwordInput;
    private Button loginBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        // for login
        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        loginBtn = view.findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loginUser();
            }
        });

        registerTV = view.findViewById(R.id.linkRegister);
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loginFromActivityListener.register();
            }
        });
        return view;
    }
}
