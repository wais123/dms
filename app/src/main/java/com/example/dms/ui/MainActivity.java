package com.example.dms.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dms.ui.home.BerandaFragment;
import com.example.dms.ui.pengaturan.PengaturanFragment;
import com.example.dms.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.navigation_beranda:
                    fragment = new BerandaFragment();
                    loadFragment(fragment, "Home", 0);
                    return true;
                case R.id.navigation_profile:
                    fragment = new PengaturanFragment();
                    loadFragment(fragment, "Profile", 1);
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        bottomNavigationView.setSelectedItemId(R.id.navigation_beranda);
    }

    private void loadFragment(Fragment fragment, String title, int icon) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        if (getSelectedItem(bottomNavigationView) != R.id.navigation_beranda) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_beranda);
        } else {
            super.onBackPressed();
        }
    }

    private int getSelectedItem(BottomNavigationView bottomNavigationView) {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.isChecked()) {
                return menuItem.getItemId();
            }
        }
        return R.id.navigation_beranda;
    }
}
