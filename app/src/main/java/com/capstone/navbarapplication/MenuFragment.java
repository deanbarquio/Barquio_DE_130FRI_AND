package com.capstone.navbarapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MenuFragment extends Fragment {

    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Set up the Toolbar
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem menuFragment = menu.findItem(R.id.menu_fragment);
        MenuItem menuDialog = menu.findItem(R.id.menu_dialog);
        MenuItem menuExit = menu.findItem(R.id.menu_exit);

        setCustomMenuLayout(menuFragment, R.drawable.fragment, "Fragment");
        setCustomMenuLayout(menuDialog, R.drawable.dialog, "Dialog");
        setCustomMenuLayout(menuExit, R.drawable.exit, "Exit");

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setCustomMenuLayout(MenuItem item, int iconResId, String title) {
        LinearLayout menuLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.custom_menu_item, null);
        ImageView iconView = menuLayout.findViewById(R.id.menu_item_icon);
        iconView.setImageResource(iconResId);
        TextView titleView = menuLayout.findViewById(R.id.menu_item_title);
        titleView.setText(title);
        item.setActionView(menuLayout);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_fragment) {
            switchToFragmentContainer();
            return true;
        } else if (id == R.id.menu_dialog) {
            CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance(getParentFragmentManager());
            dialogFragment.show(getParentFragmentManager(), "CustomDialogFragment");
            return true;
        } else if (id == R.id.menu_exit) {
            if (getActivity() != null) {
                getActivity().finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchToFragmentContainer() {
        View titleView = getActivity().findViewById(R.id.appTitle);
        View subtitleView = getActivity().findViewById(R.id.appSubtitle);
        if (titleView != null) titleView.setVisibility(View.GONE);
        if (subtitleView != null) subtitleView.setVisibility(View.GONE);

        MenuDisplayFragment fragment = new MenuDisplayFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
