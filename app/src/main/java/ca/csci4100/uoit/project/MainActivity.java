package ca.csci4100.uoit.project;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity /* implements NavigationView.OnNavigationItemSelectedListener*/ {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mToggle;
    Toolbar toolbar;
    public static int GET_LOGIN_INTENT = 1;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();

        /*
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(mToggle);
        */
        navigationView= (NavigationView) findViewById(R.id.navigationView);

        if (savedInstanceState == null) {
            //navigationView.setCheckedItem(R.id.nearby);
        }

        //navigationView.setNavigationItemSelectedListener(this);
    }


    public void search(View view) {
        Intent searchIntent = new Intent(
                MainActivity.this,
                SearchActivity.class
        );
        startActivity(searchIntent);
    }

    public void nearby(View view) {
        Intent nearbyIntent = new Intent(
                MainActivity.this,
                NearbyActivity.class
        );
        startActivity(nearbyIntent);
    }

    public void logout(View view) {
        Intent logoutIntent = new Intent(
                MainActivity.this,
                LoginActivity.class
        );
        startActivity(logoutIntent);
    }

    /*@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }*/

    private void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
    }





    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



        @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent resultIntent) {
        if(requestCode == GET_LOGIN_INTENT) {
            if(resultCode == Activity.RESULT_OK){
                //correct password
                Toast toast = Toast.makeText(MainActivity.this, " Login Success", Toast.LENGTH_SHORT);
                toast.show();

            }
        }
        // TODO: handle the province select event
    }

}
