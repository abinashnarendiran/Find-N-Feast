package ca.csci4100.uoit.project;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity /* implements NavigationView.OnNavigationItemSelectedListener*/ {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mToggle;
    Toolbar toolbar;
    public static int GET_LOGIN_INTENT = 1;
    NavigationView navigationView;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    public Context context;

    public void getUserData(){
        String line = "";
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(openFileInput("data")));
            while((line=br.readLine())!=null){
                //Toast.makeText(MainActivity.this, line, Toast.LENGTH_LONG).show();
                System.out.println(line);
            }

        }
        catch (IOException e){
            Toast.makeText(MainActivity.this, e+"", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        this.user=(FirebaseUser) getIntent().getExtras().get("user");
        System.out.println(user.getEmail()+" logged in");
        ((TextView)findViewById(R.id.mainUserNameDisplay)).setText(user.getEmail());
        /*
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(mToggle);
        */
        navigationView= (NavigationView) findViewById(R.id.navigationView);

        mAuth = FirebaseAuth.getInstance();
        context = this;

        if (savedInstanceState == null) {
            //navigationView.setCheckedItem(R.id.nearby);
        }

        getUserData();
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
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        Toast.makeText(MainActivity.this, "You are logged out",Toast.LENGTH_SHORT).show();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void review(View view) {
        Intent logoutIntent = new Intent(MainActivity.this, reviewActivity.class);
        logoutIntent.putExtra("email",user.getEmail());
        startActivity(logoutIntent);
    }

    public void chat(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        Intent chatIntent = new Intent(context, ChatActivity.class);
        chatIntent.putExtra("user",user);
        context.startActivity(chatIntent);
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
            //super.onBackPressed();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            Toast.makeText(MainActivity.this, "You are logged out",Toast.LENGTH_SHORT).show();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
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
