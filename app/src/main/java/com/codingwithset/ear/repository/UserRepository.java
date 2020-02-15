package com.codingwithset.ear.repository;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.codingwithset.ear.callback.OnLoginCallBack;
import com.codingwithset.ear.callback.OnSignupCallBack;
import com.codingwithset.ear.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserRepository {


    private static final String TAG = "UserRepository";
    private static UserRepository instance;
    private OnLoginCallBack mOnLoginCallBack;
    private OnSignupCallBack mOnSignupCallBack;
    private Context mContext;
    FirebaseAuth mFirebaseAuth;

    public void setOnLoginCallBack(OnLoginCallBack onLoginCallBack) {
        mOnLoginCallBack = onLoginCallBack;
    }

    public void setOnSignupCallBack(OnSignupCallBack onSignupCallBack) {
        mOnSignupCallBack = onSignupCallBack;
    }

    private UserRepository(Context context) {
        mContext = context.getApplicationContext();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public static UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepository(context);
        }
        return instance;
    }


    public void loginUser(Activity activity, String email, String password) {
        if (mOnLoginCallBack != null)
            mOnLoginCallBack.onsStarted();

        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                    if (mOnLoginCallBack != null)
                        mOnLoginCallBack.onSuccess(firebaseUser);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (mOnLoginCallBack != null) {
                    mOnLoginCallBack.onFailed(e.getMessage());
                }
            }
        });
    }


    public void signupUser(User user, String password) {
        if (mOnSignupCallBack != null)
            mOnSignupCallBack.onsStarted();
        mFirebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {




                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        databaseReference.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(user).addOnSuccessListener(aVoid -> {
                            if (mOnSignupCallBack != null) {
                                mOnSignupCallBack.onSuccess(Objects.requireNonNull(task.getResult()).getUser());
                            }

                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "onCancelled: " + databaseError.getMessage());
                    }
                });





            } else {
                if (mOnSignupCallBack != null) {
                    mOnSignupCallBack.onFailed(Objects.requireNonNull(task.getException()).toString());
                }
            }

        }).addOnFailureListener(e -> {

            if (mOnSignupCallBack != null)
                mOnSignupCallBack.onFailed(e.getMessage());
        });


    }
}
