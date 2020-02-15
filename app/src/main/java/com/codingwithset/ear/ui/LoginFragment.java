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
import android.widget.Toast;

import com.codingwithset.ear.R;
import com.codingwithset.ear.utils.Utils;
import com.codingwithset.ear.callback.OnLoginCallBack;
import com.codingwithset.ear.modelview.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginFragment extends Fragment {


  EditText mTextUsername;
  EditText mTextPassword;
  Button mButtonLogin;
  TextView mTextViewRegister;
  ViewGroup progressView;
  protected boolean isProgressShowing = false;
  OnLoginCallBack mOnLoginCallBack;
  UserViewModel mUserViewModel;


    public static Fragment newInstance() {
      return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      try{
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
          Intent intent = new Intent(getContext(), MainActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
          Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
          getActivity().finish();
        }
      }catch (Exception e){
        e.printStackTrace();
      }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        return view;
    }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mUserViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(getActivity()).getApplication()).create(UserViewModel.class);


    mTextViewRegister.setOnClickListener(view -> {
      Intent registerIntent = new Intent(getContext(), RegisterActivity.class);
      startActivity(registerIntent);
    });


    mButtonLogin.setOnClickListener(view -> {
      String user = mTextUsername.getText().toString().trim();
      String pwd = mTextPassword.getText().toString().trim();
      if (validate()) {
        mUserViewModel.setLoginListener(new OnLoginCallBack() {
          @Override
          public void onsStarted() {
            System.out.println("started");
          }

          @Override
          public void onSuccess(FirebaseUser firebaseUser) {


            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            Objects.requireNonNull(getActivity()).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            Toast.makeText(getContext(), "Welcome "+firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
            getActivity().finish();



          }

          @Override
          public void onFailed(String errorMessage) {
            System.out.println(errorMessage);

          }
        }, getActivity(), mTextUsername.getText().toString(), mTextPassword.getText().toString());
      }


    });
  }

  private void init(View view){
  mTextUsername = view.findViewById(R.id.text_email);
  mTextPassword = view.findViewById(R.id.edit_text_password);
  mButtonLogin = view.findViewById(R.id.button_sign_in);
  mTextViewRegister = view.findViewById(R.id.text_view_register);


}











  private boolean validate() {
    if (mTextUsername.getText().toString().isEmpty()) {
      mTextUsername.setError("Email can't be empty!");
      return false;
    } else if (!Utils.getInstance().isValidEmail(mTextUsername.getText().toString())) {
      mTextUsername.setError("Invalid email address");
      return false;
    } else if (mTextPassword.getText().toString().isEmpty()) {
      mTextPassword.setError("Password can't be empty!");
      return false;
    }
    return true;
  }

}