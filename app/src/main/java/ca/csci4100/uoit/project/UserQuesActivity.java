package ca.csci4100.uoit.project;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserQuesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_questions);
    }
    public void signUp(View v) {
        System.out.println("return user info");
        Intent i = new Intent();
        if (((RadioGroup) findViewById(R.id.mealList)).getCheckedRadioButtonId() == -1 || ((RadioGroup) findViewById(R.id.priceList)).getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Do not leave fields empty",Toast.LENGTH_SHORT).show();
        } else {
            String fMeal = ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.mealList)).getCheckedRadioButtonId())).getText().toString();
            String pRange = ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.priceList)).getCheckedRadioButtonId())).getText().toString();
            //System.out.println(fMeal);
            //System.out.println(pRange);
            i.putExtra("favouriteMeal", fMeal);
            i.putExtra("priceRange", pRange);
            ArrayList<String> typeList = new ArrayList<>();
            int listSize = ((LinearLayout) findViewById(R.id.favouriteList)).getChildCount();
            for (int x = 0; x < listSize; x++) {
                if (((CheckBox) ((LinearLayout) findViewById(R.id.favouriteList)).getChildAt(x)).isChecked()) {
                    String item = ((CheckBox) ((LinearLayout) findViewById(R.id.favouriteList)).getChildAt(x)).getText().toString();
                    if (item.equals("Other")) {
                        //todo add new activity for custom entry
                    } else {
                        //System.out.println(item);
                        typeList.add(item);
                    }
                }
            }
            i.putExtra("foodList", typeList);
            setResult(Activity.RESULT_OK, i);
            finish();
        }
    }

}
