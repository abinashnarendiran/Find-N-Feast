package ca.csci4100.uoit.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void getInfo(View view) {
        Intent getInfoIntent = new Intent(
                LoginActivity.this,
                InfoActivity.class
        );
        startActivity(getInfoIntent);
    }

    public void signup(View view) {
        Intent getSignupIntent = new Intent(
                LoginActivity.this,
                SignUpActivity.class
        );
        startActivity(getSignupIntent);
    }
}
