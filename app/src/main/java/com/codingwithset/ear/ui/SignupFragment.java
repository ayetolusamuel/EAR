package com.codingwithset.ear.ui;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codingwithset.ear.R;
import com.codingwithset.ear.utils.Utils;
import com.codingwithset.ear.callback.OnSignupCallBack;
import com.codingwithset.ear.model.User;
import com.codingwithset.ear.modelview.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {
    private EditText mEmailAddress, mFullName, mPassword;
    private Button mButtonRegister;
    private TextView mLogin;
    UserViewModel mUserViewModel;

    public static Fragment newInstance() {
        return new SignupFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        init(view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       mUserViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(getActivity()).getApplication()).create(UserViewModel.class);




        mLogin.setOnClickListener(view -> {

        });
        mButtonRegister.setOnClickListener(v -> {

            if (validate()) {
                User user = new User(mEmailAddress.getText().toString(), mFullName.getText().toString());
                String password = mPassword.getText().toString();

                mUserViewModel.setSignupListener(new OnSignupCallBack() {
                    @Override
                    public void onsStarted() {
                        System.out.println("started");
                    }

                    @Override
                    public void onSuccess(FirebaseUser firebaseUser) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getContext(),LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                      //  Toast.makeText(getContext(), "Welcome "+ Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail(), Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }

                    @Override
                    public void onFailed(String errorMessage) {
                        System.out.println("error"+errorMessage);
                    }
                },user,password);

            }
        });

    }

    private void init(View view) {
        mEmailAddress = view.findViewById(R.id.edit_text_email_address);
        mFullName = view.findViewById(R.id.edit_text_full_name);
        mPassword = view.findViewById(R.id.edit_text_password);
        mButtonRegister = view.findViewById(R.id.button_sign_up);
        mLogin = view.findViewById(R.id.text_view_login_link);
    }


    private boolean validate() {
        if (mEmailAddress.getText().toString().isEmpty()) {
            mEmailAddress.setError("Email can't be empty!");
            return false;
        } else if (!Utils.getInstance().isValidEmail(mEmailAddress.getText().toString())) {
            mEmailAddress.setError("Invalid email address");
            return false;
        } else if (mFullName.getText().toString().isEmpty()) {
            mFullName.setError("full name can't be empty!");
            return false;
        } else if (mPassword.getText().toString().isEmpty()) {
            mPassword.setError("Password can't be empty!");
            return false;
        }
        return true;
    }

}
