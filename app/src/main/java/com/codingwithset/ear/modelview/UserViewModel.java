package com.codingwithset.ear.modelview;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.codingwithset.ear.callback.OnLoginCallBack;
import com.codingwithset.ear.callback.OnSignupCallBack;
import com.codingwithset.ear.model.User;
import com.codingwithset.ear.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {


    private final UserRepository mUserRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = UserRepository.getInstance(application);
    }

    public void setLoginListener(OnLoginCallBack loginListener, Activity activity, String email, String password) {
        mUserRepository.setOnLoginCallBack(loginListener);
        loginUser(activity, email, password);
    }

    private void loginUser(Activity activity, String email, String password) {
        mUserRepository.loginUser(activity, email, password);
    }

    public void setSignupListener(OnSignupCallBack listener, User user, String password) {
        mUserRepository.setOnSignupCallBack(listener);
        signupUser(user, password);
    }


    private void signupUser(User user, String password) {
        mUserRepository.signupUser(user, password);
    }

}
