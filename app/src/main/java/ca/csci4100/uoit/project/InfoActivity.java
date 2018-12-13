package ca.csci4100.uoit.project;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    public void backInfo(View view) {
        finish();
    }
}
