package com.codingwithset.ear.callback;

import com.google.firebase.auth.FirebaseUser;

public interface OnLoginCallBack {
    void onsStarted();
    void onSuccess(FirebaseUser firebaseUser);
    void onFailed(String errorMessage);


}
