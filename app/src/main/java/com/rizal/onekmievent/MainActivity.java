package com.rizal.onekmievent;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rizal.onekmievent.client.MobileService;
import com.rizal.onekmievent.model.ApiUtils;
import com.rizal.onekmievent.model.Peserta;
import com.rizal.onekmievent.utils.Preferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    MobileService mobileService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_news, R.id.navigation_info, R.id.navigation_profil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        mobileService = ApiUtils.MobileService(getApplicationContext());
        Peserta peserta = Preferences.getPeserta(getApplicationContext());

    }

}
