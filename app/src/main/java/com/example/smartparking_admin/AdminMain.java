package com.example.smartparking_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMain extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        bottomNavigationView=findViewById(R.id.navigationView);

        loadFragment(new renterfrag());

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Fragment fragment = null;

                        switch (item.getItemId()) {
                            case R.id.nav_renter:
                                fragment = new renterfrag();
                                break;
                            case R.id.nav_tenant:
                                fragment = new tenantfrag();
                                break;
                            case R.id.nav_other:
                                fragment = new otherfrag();
                                break;
                        }
                        return loadFragment(fragment);
                    }
                });

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_renter, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}