package ca.csci4100.uoit.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    public void backInfo(View view) {
        Intent backInfoIntent = new Intent(
                InfoActivity.this,
                LoginActivity.class
        );
        startActivity(backInfoIntent);
    }
}
