package com.codingwithset;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.codingwithset.ear.R;
import com.codingwithset.ear.ui.MainActivity;

import java.util.Objects;

public class ExitAppConfirmationDialog extends DialogFragment {

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_exit_app, container, false);
        mContext = getActivity();


        TextView yesDialog = view.findViewById(R.id.dialogConfirm);
        yesDialog.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).finishAffinity();
            getActivity().finishAndRemoveTask();
            Objects.requireNonNull(getDialog()).dismiss();

        });

        // Cancel button for closing the dialog
        TextView noDialog = view.findViewById(R.id.dialogCancel);
        noDialog.setOnClickListener((v) ->{
            startActivity(new Intent(mContext, MainActivity.class));
            Objects.requireNonNull(getDialog()).dismiss();
        });

        return view;
    }


}

















