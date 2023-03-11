package com.example.spendingmanagement;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;

import com.example.spendingmanagement.fragments.PhanLoaiFragment;
import com.example.spendingmanagement.fragments.ThuFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spendingmanagement.databinding.ActivityMainBinding;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private static final int FRAGMENT_PHANLOAI = 0;
    private static final int FRAGMENT_KHOANTHU = 1;
    private static final int FRAGMENT_KHOANCHI = 2;
    private static final int FRAGMENT_THONGKE = 3;

    private int currentFragment = FRAGMENT_PHANLOAI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new PhanLoaiFragment());
        navigationView.getMenu().findItem(R.id.nav_quanly_loai).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_quanly_loai) {
            if (currentFragment != FRAGMENT_PHANLOAI) {
                replaceFragment(new PhanLoaiFragment());
                currentFragment = FRAGMENT_PHANLOAI;
            }
        } else if (id == R.id.nav_khoanthu) {
            if (currentFragment != FRAGMENT_KHOANTHU) {
                replaceFragment(new ThuFragment());
                currentFragment = FRAGMENT_KHOANTHU;
            }

        } else if (id == R.id.nav_khoanchi) {

        } else if (id == R.id.nav_thongke) {

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
        fragmentTransaction.commit();
    }
}