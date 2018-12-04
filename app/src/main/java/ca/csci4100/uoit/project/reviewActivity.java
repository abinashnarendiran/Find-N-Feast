package ca.csci4100.uoit.project;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class reviewActivity extends AppCompatActivity {
    private SqliteReviewHelper reviewHelper;

    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        reviewHelper=new SqliteReviewHelper(this);
        //reviewHelper.remakeTable();
        ArrayList<Review> reviews = new ArrayList<>(reviewHelper.getAllReviews());
        for (int x = 0; x < reviews.size(); x++) {
            System.out.println(reviews.get(x).toString());
        }
        mDatabase=FirebaseDatabase.getInstance();

    }
    public void cancel (View v){
        finish();
    }
    public void confirm(View v){
        //Toast.makeText(reviewActivity.this, "confirm", Toast.LENGTH_LONG).show();
        //database save




        //local save
        try {
            float rating = (float) Double.parseDouble(((EditText) findViewById(R.id.ratingInput)).getText().toString());
            String address = ((EditText) findViewById(R.id.addressInput)).getText().toString();
            String date = "";
            String description = ((EditText) findViewById(R.id.descriptionInput)).getText().toString();

            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            date = df.format(c);

            //push to both
            DatabaseReference data = mDatabase.getReference("Reviews");

            Review add = reviewHelper.addReview(rating,address,date,description);

            data.push().setValue(add);
            
            finish();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
