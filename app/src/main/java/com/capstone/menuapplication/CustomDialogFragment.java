package com.capstone.menuapplication;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Positive for Fragment & Negative for App Exit")
                .setPositiveButton("+", (dialog, id) -> {
                    ((MainActivity) getActivity()).switchToFragmentContainer();
                })
                .setNegativeButton("−", (dialog, id) -> {
                    getActivity().finish();  // Close the app
                });
        return builder.create();
    }
}
