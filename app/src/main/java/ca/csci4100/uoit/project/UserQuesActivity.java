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
import android.widget.TextView;
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
        if (((RadioGroup) findViewById(R.id.mealList)).getCheckedRadioButtonId() == -1 || ((EditText)findViewById(R.id.minPrice)).getText().toString()==null || ((EditText)findViewById(R.id.minPrice)).getText().toString()==null) {
            Toast.makeText(this, "Do not leave fields empty",Toast.LENGTH_SHORT).show();
        } else {
            String fMeal;
            double pMin, pMax;
            try {
                fMeal = ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.mealList)).getCheckedRadioButtonId())).getText().toString();
                pMin = Double.parseDouble(((EditText) findViewById(R.id.minPrice)).getText().toString());
                pMax = Double.parseDouble(((EditText) findViewById(R.id.maxPrice)).getText().toString());
                //System.out.println(fMeal);
                //System.out.println(pRange);
                i.putExtra("favouriteMeal", fMeal);
                i.putExtra("minPrice", pMin);
                i.putExtra("maxPrice", pMax);
                ArrayList<String> typeList = new ArrayList<>();
                System.out.println(((LinearLayout) findViewById(R.id.favouriteList)).getChildCount());
                //System.out.println(((CheckBox) ((LinearLayout)((LinearLayout) findViewById(R.id.favouriteList)).getChildAt(4)).getChildAt(0)).isChecked());
                //System.out.println(((CheckBox)findViewById(R.id.checkBox6)).isChecked());
                //System.out.println(R.id.checkBox6);
                //System.out.println(((CheckBox)findViewById(R.id.checkBox6)).getText());
                int listSize = ((LinearLayout) findViewById(R.id.favouriteList)).getChildCount();

                for (int x = 0; x < listSize; x++) {
                    if(x==(listSize-1)){
                        if (((CheckBox)findViewById(R.id.checkBox6)).isChecked()){
                            String item = ((EditText)findViewById(R.id.optionEdit)).getText().toString();
                            typeList.add(item);
                        }
                    }
                    else if (((CheckBox) ((LinearLayout) findViewById(R.id.favouriteList)).getChildAt(x)).isChecked()) {
                        String item = ((CheckBox) ((LinearLayout) findViewById(R.id.favouriteList)).getChildAt(x)).getText().toString();
                        typeList.add(item);
                    }
                }
                System.out.println("finished");
                i.putExtra("foodList", typeList);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
            catch(Exception e){
                System.out.println("input: "+e);
                Toast.makeText(this, "invalid input: "+e, Toast.LENGTH_SHORT);
            }
        }
    }
}
