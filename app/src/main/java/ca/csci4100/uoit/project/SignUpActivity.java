package ca.csci4100.uoit.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void getNext(View view) {
        Intent getNextIntent = new Intent(
                SignUpActivity.this,
                UserQuesActivity.class
        );
        startActivity(getNextIntent);

        /*
        EditText first_name = (EditText) findViewById(R.id.first_name_input);
        EditText last_name = (EditText) findViewById(R.id.last_name_input);
        EditText email = (EditText) findViewById(R.id.email_input);
        EditText password = (EditText) findViewById(R.id.password_input);
        EditText password_confirm = (EditText) findViewById(R.id.password_confirm_input);

        String First_Name = first_name.getText().toString();
        String Last_Name = last_name.getText().toString();
        String Email = email.getText().toString();
        String Password1 = password.getText().toString();
        String Password2 = password_confirm.getText().toString();

        /*
        //Email not in database
        if (Password1.equals(Password2)) {
            sqliteHelper = new SqliteHelper (this);

            sqliteHelper.addUser(new User(First_Name, Last_Name, Email, Password1));

            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
        }
        else{
            Toast.makeText(SignUpActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }


        if (!sqliteHelper.CheckIfEmailExists(Email)) {

        }

        else{
            //Email already in database
            Toast.makeText(SignUpActivity.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
        }
        */

    }



}