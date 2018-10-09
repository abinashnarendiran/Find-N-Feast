package ca.csci4100.uoit.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
    }

}
