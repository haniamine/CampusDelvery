package ma.ac.emi.campusdelivery.admin;
import ma.ac.emi.campusdelivery.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class AdminActivity extends AppCompatActivity {

    Button btn_logout;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bottomNavigation = findViewById(R.id.admin_bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(DeliverFragment.newInstance("", ""));

        //LogOut
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminActivity.this, "Vous vous êtes déconnecté", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.admin_stores:
                            openFragment(AdminStoresFragment.newInstance("", ""));
                            return true;
                        case R.id.admin_deliver:
                            openFragment(AdminDeliverFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };
}
