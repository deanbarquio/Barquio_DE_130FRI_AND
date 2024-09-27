package com.capstone.menuapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void switchToFragmentContainer() {
        // Hide only the non-FrameLayout views
        View titleView = findViewById(R.id.appTitle);
        View subtitleView = findViewById(R.id.appSubtitle);
        titleView.setVisibility(View.GONE);
        subtitleView.setVisibility(View.GONE);

        // Replace the fragment inside the FrameLayout
        MenuFragment fragment = new MenuFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Customize the menu items with custom layout
        MenuItem menuFragment = menu.findItem(R.id.menu_fragment);
        MenuItem menuDialog = menu.findItem(R.id.menu_dialog);
        MenuItem menuExit = menu.findItem(R.id.menu_exit);

        // Inflate custom layout for each menu item
        setCustomMenuLayout(menuFragment, R.drawable.fragment, "Fragment");
        setCustomMenuLayout(menuDialog, R.drawable.dialog, "Dialog");
        setCustomMenuLayout(menuExit, R.drawable.exit, "Exit");

        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_fragment) {
            switchToFragmentContainer();
            return true;
        } else if (id == R.id.menu_dialog) {
            CustomDialogFragment dialogFragment = new CustomDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "CustomDialogFragment");
            return true;
        } else if (id == R.id.menu_exit_submenu) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();

            // Show the main layout elements again
            View titleView = findViewById(R.id.appTitle);
            View subtitleView = findViewById(R.id.appSubtitle);
            titleView.setVisibility(View.VISIBLE);
            subtitleView.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
