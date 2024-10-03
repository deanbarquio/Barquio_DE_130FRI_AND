package com.capstone.navbarapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView displayName, displayEmail, displayGender, displayTerms;
    private Button buttonEdit;

    private static final String PREFS_NAME = "UserProfilePrefs";
    private static final String KEY_NAME = "key_name";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_GENDER = "key_gender";
    private static final String KEY_TERMS = "key_terms";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize view components
        displayName = view.findViewById(R.id.display_name);
        displayEmail = view.findViewById(R.id.display_email);
        displayGender = view.findViewById(R.id.display_gender);
        displayTerms = view.findViewById(R.id.display_terms);
        buttonEdit = view.findViewById(R.id.button_edit);

        loadProfileData();

        buttonEdit.setOnClickListener(v -> showEditDialog());

        return view;
    }

    private void loadProfileData() {
        SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String name = preferences.getString(KEY_NAME, "John Doe");
        String email = preferences.getString(KEY_EMAIL, "john@example.com");
        String gender = preferences.getString(KEY_GENDER, "Male");
        boolean termsAccepted = preferences.getBoolean(KEY_TERMS, true);

        // Set the values in the TextViews
        displayName.setText("Name: " + name);
        displayEmail.setText("Email: " + email);
        displayGender.setText("Gender: " + gender);
        displayTerms.setText("Terms Accepted: " + (termsAccepted ? "Yes" : "No"));
    }

    private void showEditDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogView = inflater.inflate(R.layout.dialog_edit_profile, null);

        EditText editName = dialogView.findViewById(R.id.edit_name);
        EditText editEmail = dialogView.findViewById(R.id.edit_email);
        RadioGroup radioGender = dialogView.findViewById(R.id.radio_group_gender); // Updated ID
        CheckBox editTerms = dialogView.findViewById(R.id.edit_checkbox_terms);    // Updated ID

        editName.setText(displayName.getText().toString().replace("Name: ", ""));
        editEmail.setText(displayEmail.getText().toString().replace("Email: ", ""));

        // Set existing gender in RadioGroup
        if (displayGender.getText().toString().contains("Male")) {
            radioGender.check(R.id.radio_male);
        } else if (displayGender.getText().toString().contains("Female")) {
            radioGender.check(R.id.radio_female);
        }

        editTerms.setChecked(displayTerms.getText().toString().contains("Yes"));

        new AlertDialog.Builder(getActivity())
                .setTitle("Edit Profile")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    // Get updated values from the dialog
                    String updatedName = editName.getText().toString();
                    String updatedEmail = editEmail.getText().toString();

                    // Get selected gender
                    String updatedGender = "";
                    int selectedGenderId = radioGender.getCheckedRadioButtonId();

                    if (selectedGenderId == R.id.radio_male) {
                        updatedGender = "Male";
                    } else if (selectedGenderId == R.id.radio_female) {
                        updatedGender = "Female";
                    }

                    boolean updatedTerms = editTerms.isChecked();

                    saveProfileData(updatedName, updatedEmail, updatedGender, updatedTerms);

                    displayName.setText("Name: " + updatedName);
                    displayEmail.setText("Email: " + updatedEmail);
                    displayGender.setText("Gender: " + updatedGender);
                    displayTerms.setText("Terms Accepted: " + (updatedTerms ? "Yes" : "No"));
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveProfileData(String name, String email, String gender, boolean termsAccepted) {
        SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_GENDER, gender);
        editor.putBoolean(KEY_TERMS, termsAccepted);
        editor.apply();

        Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }
}
