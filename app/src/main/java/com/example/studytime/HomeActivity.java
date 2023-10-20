package com.example.studytime;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
GoogleSignInClient gClient;
GoogleSignInOptions gOptions;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    BottomNavigationView bottomNavigationView;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        NavigationView navigationView = findViewById(R.id.navigationView);
        View vi = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        MenuItem menuItem_logout = menu.findItem(R.id.Logout);
        TextView username = vi.findViewById(R.id.user_name);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        gOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gClient = GoogleSignIn.getClient(this, gOptions);
        GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (gAccount != null) {
            String gName = gAccount.getDisplayName();
            username.setText(gName);
        }
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.hamburger_icon).setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        findViewById(R.id.search_icon).setOnClickListener(view -> {
            finish();
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        menuItem_logout.setOnMenuItemClickListener(menuItem_logout1 -> {
            showLogoutAlert();
            return true;
        });
        fragment = new MenuFragment();
        loadFragment(fragment);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.Menu:
                    loadFragment(new MenuFragment());
                    break;
                case R.id.Categories:
                    loadFragment(new CategoryFragment());
                    break;
                case R.id.Live:
                    loadFragment(new LiveFragment());
                    break;
                case R.id.Tabs:
                    loadFragment(new TabsFragment());
                    break;
            }
            return true;
        });
    }
    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment);
            fragmentTransaction.commit();
        }
    }
    private void showLogoutAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_logout, null);
        builder.setView(dialogView);
        final AlertDialog exitDialog = builder.create();
        Button yesButton = dialogView.findViewById(R.id.yes_button);
        Button noButton = dialogView.findViewById(R.id.no_button);
        yesButton.setOnClickListener(view -> gClient.signOut().addOnCompleteListener(task -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
            exitDialog.dismiss();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }));
        noButton.setOnClickListener(view -> exitDialog.dismiss());
        exitDialog.show();
    }
    private void showExitAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_exit, null);
        builder.setView(dialogView);
        final AlertDialog exitDialog = builder.create();
        Button yesButton = dialogView.findViewById(R.id.yes_button);
        Button noButton = dialogView.findViewById(R.id.no_button);
        yesButton.setOnClickListener(view -> {
            finishAffinity();
            exitDialog.dismiss();
            System.exit(0);
        });
        noButton.setOnClickListener(view -> exitDialog.dismiss());
        exitDialog.show();
    }
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame_layout);
        if (currentFragment instanceof MenuFragment) {
            showExitAlert();
        } else if (currentFragment instanceof CategoryFragment) {
            bottomNavigationView.setSelectedItemId(R.id.Menu);
        } else if (currentFragment instanceof LiveFragment) {
            bottomNavigationView.setSelectedItemId(R.id.Menu);
        } else if (currentFragment instanceof TabsFragment) {
            bottomNavigationView.setSelectedItemId(R.id.Menu);
        } else if (currentFragment instanceof PdfFragment) {
            // If child fragment is visible, go back to parent CategoryFragment
            fragmentManager.popBackStack();
        }  else if (currentFragment instanceof PdfViewerFragment) {
            // If child fragment is visible, go back to parent CategoryFragment
            fragmentManager.popBackStack();
        }
    }
}


