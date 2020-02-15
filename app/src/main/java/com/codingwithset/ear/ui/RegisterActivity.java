package com.codingwithset.ear.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.codingwithset.ear.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SignupFragment.newInstance())
                    .commitNow();
        }
    }
}
