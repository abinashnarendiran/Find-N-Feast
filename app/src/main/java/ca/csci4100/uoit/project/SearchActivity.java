package ca.csci4100.uoit.project;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class SearchActivity extends AppCompatActivity {
    String[] arraySpinner = new String[] {"1", "2", "3", "4" , "All"};
    double latitude;
    double longitude;
    String address;
    String search;
    String price;
    String radius_string;
    public static int GET_RESULT_INTENT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Spinner spinner = (Spinner) findViewById(R.id.price_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinner.setAdapter(adapter);


    }

    public void search_restaurants(View view){
        Intent intent = new Intent(SearchActivity.this, SearchPlacesActivity.class);

        EditText address_input= (EditText)findViewById(R.id.address_input);
        address = address_input.getText().toString();

        EditText search_input= (EditText)findViewById(R.id.search_input);
        search = search_input.getText().toString();

        Spinner spinner = (Spinner) findViewById(R.id.price_spinner);
        price = spinner.getSelectedItem().toString();

        EditText radius_input= (EditText)findViewById(R.id.search_radius);
        radius_string = radius_input.getText().toString();
        //Log.d("Radius", radius_string);




        if(address.isEmpty() && search.isEmpty() && radius_string.isEmpty()){
            showToastMethod(getApplicationContext(), "Address, search, radius  is empty. " +
                    "Please type in an address, what food you want for results, and what radius you want");

        }


        else if(address.isEmpty() || search.isEmpty() || radius_string.isEmpty()){
            showToastMethod(getApplicationContext(), "One of the search boxes is empty. Please fill it in");

        }

        else if (Double.valueOf(radius_string) > 15){
            showToastMethod(getApplicationContext(), "Radius is higher than 15 km. Please type in a radius lower or" +
                    "equal to 15");
        }



        else{

            if (Geocoder.isPresent()) {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                try{
                    List<Address> ls = geocoder.getFromLocationName(
                            address, 1);
                    if(ls.size() > 0){
                        Address addr = ls.get(0);
                        latitude = addr.getLatitude();
                        longitude = addr.getLongitude();
                        Log.d("latitude", String.valueOf(latitude));
                        Log.d("longitude", String.valueOf(longitude));
                        intent.putExtra("price", price);
                        intent.putExtra("search", search);
                        intent.putExtra("radius",radius_string);
                        intent.putExtra("latitude", String.valueOf(latitude));
                        intent.putExtra("longitude",String.valueOf(longitude));

                        startActivityForResult(intent,GET_RESULT_INTENT);
                    }

                    else{
                        //Toast says that there is no address that matches your search
                        showToastMethod(getApplicationContext(), "Address doesn't exist");
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

            }

        }
    }

    public static void showToastMethod(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent resultIntent) {
        if(requestCode == GET_RESULT_INTENT) {
            if(resultCode == Activity.RESULT_OK){
                Toast toast = Toast.makeText(SearchActivity.this, "No Results", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        // TODO: handle the province select event
    }



}
