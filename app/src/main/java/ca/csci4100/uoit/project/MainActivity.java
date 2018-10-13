package ca.csci4100.uoit.project;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mToggle;
    Toolbar toolbar;
    public static int GET_LOGIN_INTENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
    }

    private void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
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
