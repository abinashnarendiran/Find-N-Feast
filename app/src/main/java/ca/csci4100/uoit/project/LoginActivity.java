package ca.csci4100.uoit.project;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    public void login(View view) {
        EditText email = (EditText) findViewById(R.id.email_input);
        EditText password = (EditText) findViewById(R.id.password_input);

        if (email.getText().toString().equals("A@gmail.com") && password.getText().toString().equals("A")) {
            Intent LoginIntent = new Intent(this, MainActivity.class);
            startActivity(LoginIntent);

        }
        else {
            Toast toast2 = Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT);
            toast2.show();
        }

        }
}
