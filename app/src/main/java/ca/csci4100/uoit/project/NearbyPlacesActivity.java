package ca.csci4100.uoit.project;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.config.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class NearbyPlacesActivity extends AppCompatActivity{


    LocationManager locManager;
    String search;
    private  DatabaseReference Database;
    String restaurant_name;
    String address;
    String description;
    String type;
    String price_range;
    String phone_number;
    String rating;
    String sqlRating;
    String worldRating;
    String website;
    String image;
    String spinner_price;
    String longitude;
    String latitude;
    double longitude2;
    double latitude2;
    float distance;
    float distanceInKm;
    int count = 0;
    String url = "https://csci4100app.firebaseio.com/.json";
    String jsonData = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_places);

        Intent intent = getIntent();
        latitude= intent.getStringExtra("latitude");
        longitude= intent.getStringExtra("longitude");


        DownloadNearbyRestaurantsTask downloadTask = new DownloadNearbyRestaurantsTask();
        downloadTask.execute(url , latitude,longitude);

    }



    public void rate(View v){
        Intent i = new Intent(this,reviewActivity.class);

        String address="ERROR";

        View a = ((ViewGroup) v.getParent().getParent()).getChildAt(1);//gets Address block
        //View a = ((ViewGroup) v.getParent().getParent()).getChildAt(0);//gets name block
        TextView t = (TextView) ((LinearLayout) a).getChildAt(0);

        address=t.getText().toString();

        i.putExtra("address",address);
        this.startActivity(i);
    }

    public void showRestaurants(ArrayList<Restaurant> data) {
        Log.d("Show","Show");
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this, data);
        ListView feed = (ListView) findViewById(R.id.places_listView);
        feed.setAdapter(restaurantAdapter);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(NearbyPlacesActivity.this, NearbyActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }


    class DownloadNearbyRestaurantsTask extends AsyncTask<String, Void, ArrayList<Restaurant>> {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        @Override
        protected ArrayList<Restaurant> doInBackground(String... params) {


            try {
                SqliteReviewHelper reviewHelper= new SqliteReviewHelper(NearbyPlacesActivity.this);
                Intent intent = getIntent();
                spinner_price = intent.getStringExtra("price");
                URL url = new URL(params[0]);
                double latitude = Double.parseDouble(params[1]);
                double longitude = Double.parseDouble(params[2]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));
                StringBuilder sBuilder = new StringBuilder();
                String line;
                while ((line = buffRead.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }
                in.close();

                jsonData = sBuilder.toString();

                Log.d("Restaurant", "Hi");

                JSONObject jObject= new JSONObject(jsonData);

                Iterator<String> keys =  jObject.keys();

                while (keys.hasNext()) {
                    String key = keys.next();
                    try{
                        JSONObject getRestaurant = jObject.getJSONObject(key);

                        for(int i = 1; i <= getRestaurant.length(); i++){
                            JSONObject restaurant_details;
                            try {
                                restaurant_details = getRestaurant.getJSONObject("Restaurant_"+i);
                                Log.d("Number", "Restaurant_"+i);
                                latitude2 = restaurant_details.getDouble("latitude");
                                longitude2 = restaurant_details.getDouble("longitude");
                            }
                            catch (org.json.JSONException j){
                                continue;
                            }

                            float[] results = new float[1];
                            Location.distanceBetween(latitude2, longitude2, latitude, longitude, results);
                            distance = Math.round(results[0]);

                            if (distance <= 5000) {

                                price_range = restaurant_details.getString("price_range");

                                if (price_range.equals(spinner_price)) {

                                    restaurant_name = restaurant_details.getString("restaurant_name");
                                    address = restaurant_details.getString("address");
                                    description = restaurant_details.getString("description");
                                    type = restaurant_details.getString("type");
                                    phone_number = restaurant_details.getString("phone_number");
                                    rating = restaurant_details.getString("rating");
                                    website = restaurant_details.getString("website");
                                    image = restaurant_details.getString("image");
                                    String hours_list = restaurant_details.getString("hours");
                                    String[] hours_array = hours_list.split("\\s*,\\s*");
                                    String hours1 = "";

                                    sqlRating=reviewHelper.getAverageRating(address);
                                    worldRating=getWorldReview(address);

                                    for(int j = 0; j < hours_array.length; j++){
                                        hours1 += hours_array[j] + "\n";
                                        Log.d("Hours", hours_array[j]);
                                    }

                                    distanceInKm = distance / 1000;

                                    Restaurant restaurant = new Restaurant(count ,restaurant_name, address, description, type,
                                            price_range, phone_number, rating, sqlRating, worldRating, hours1, website, image, distanceInKm,
                                            latitude2, longitude2);

                                    Log.d("Restaurant", String.valueOf(restaurant));

                                    restaurants.add(restaurant);



                                }
                                else if (spinner_price.equals("All")) {
                                    if (price_range.equals("1") || price_range.equals("2") || price_range.equals("3")
                                            || price_range.equals("4")) {

                                        restaurant_name = restaurant_details.getString("restaurant_name");
                                        address = restaurant_details.getString("address");
                                        description = restaurant_details.getString("description");
                                        type = restaurant_details.getString("type");
                                        phone_number = restaurant_details.getString("phone_number");
                                        rating = restaurant_details.getString("rating");
                                        website = restaurant_details.getString("website");
                                        image = restaurant_details.getString("image");
                                        String hours_list2 = restaurant_details.getString("hours");
                                        String[] hours_array = hours_list2.split("\\s*,\\s*");
                                        String hours2 = "";

                                        sqlRating=reviewHelper.getAverageRating(address);
                                        worldRating=getWorldReview(address);

                                        for(int j = 0; j < hours_array.length; j++){
                                            hours2 += hours_array[j] + "\n";
                                            Log.d("Hours1", hours_array[j]);
                                        }


                                        distanceInKm = distance / 1000;

                                        Restaurant restaurant = new Restaurant(count ,restaurant_name, address, description, type,
                                                price_range, phone_number, rating,sqlRating,worldRating, hours2, website, image, distanceInKm,
                                                latitude2, longitude2);


                                        Log.d("Restaurant", String.valueOf(restaurant));

                                        restaurants.add(restaurant);


                                    }
                                }
                            }
                        }
                    }catch(JSONException e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return restaurants;

        }

        protected void onPostExecute(ArrayList<Restaurant> data) {

            if(data.size() == 0){
                setResult(Activity.RESULT_OK);
                finish();
            }


            Collections.sort(data, new Comparator<Restaurant>() {
                public int compare(Restaurant o1, Restaurant o2) {
                    if (o1.getDistance() == o2.getDistance()) {
                        return 0;
                    } else {
                        return o1.getDistance() > o2.getDistance() ? 1 : -1;
                    }
                }
            });



            for(int i=0; i< data.size(); i++) {
                data.get(i).setCount(i+1);
            }

            TextView title = (TextView) findViewById(R.id.places);
            title.setText(getResources().getString(R.string.nearbyPlaces));
            TextView numberOfResults = (TextView) findViewById(R.id.numberOfResults);
            numberOfResults.setText(String.valueOf("Number of Results: " +data.size()));
            showRestaurants(data);

        }
        public String getWorldReview(String address){
            float total=0,count=0;
            try {
                JSONObject jObject= new JSONObject(jsonData);

                Iterator<String> keys =  jObject.keys();

                keys.next();
                String key = keys.next();
                JSONObject getRestaurant = jObject.getJSONObject(key);
                //System.out.println("reviews: "+getRestaurant.toString());
                keys =  getRestaurant.keys();
                while(keys.hasNext()) {
                    key=keys.next();
                    //System.out.println("reviews: " + getRestaurant.getJSONObject(key).get("address"));
                    if(address.equals(getRestaurant.getJSONObject(key).get("address"))){
                        total+=Float.parseFloat(getRestaurant.getJSONObject(key).get("rating").toString());
                        count+=1;
                    }
                }
            }
            catch (org.json.JSONException j){
                System.err.println(j);
            }
            return (total/count)+"/5";
        }
    }

}

