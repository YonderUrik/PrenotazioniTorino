package com.danieleroccaforte.ium.studentbooking.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.danieleroccaforte.ium.studentbooking.Login;
import com.danieleroccaforte.ium.studentbooking.R;

public class AccountFragment extends Fragment implements View.OnClickListener {
    public AccountFragment(){

    }

    View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.account_fragment, container, false);
        Button logout_button = (Button) requireActivity().findViewById(R.id.logout_button);
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logout_button) {
            Intent intent = new Intent(requireActivity().getApplicationContext(), Login.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }
}
