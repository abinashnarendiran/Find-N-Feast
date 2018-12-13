package ca.csci4100.uoit.project;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import android.view.View;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity /* implements NavigationView.OnNavigationItemSelectedListener*/ {

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
            System.out.println("local data:");
            while((line=br.readLine())!=null){
                //Toast.makeText(MainActivity.this, line, Toast.LENGTH_LONG).show();
                System.out.println(line);
            }

        }
        catch (IOException e){
            //Toast.makeText(MainActivity.this, e+"", Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, "no local data", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        try{
            this.user = (FirebaseUser) getIntent().getExtras().get("user");
            System.out.println(user.getEmail() + " logged in");
            ((TextView) findViewById(R.id.mainUserNameDisplay)).setText(user.getEmail());
        }
        catch(Exception e) {
            ((TextView) findViewById(R.id.mainUserNameDisplay)).setText("Guest");
            ((Button)findViewById(R.id.review_btn)).setClickable(false);
            ((Button)findViewById(R.id.review_btn)).setAlpha(0.5f);
            ((Button)findViewById(R.id.chat_btn)).setClickable(false);
            ((Button)findViewById(R.id.chat_btn)).setAlpha(0.5f);
        }
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


        getUserData();
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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }





    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        Toast.makeText(MainActivity.this, "You are logged out",Toast.LENGTH_SHORT).show();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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
    }

}
