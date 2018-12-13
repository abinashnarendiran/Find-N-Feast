package ca.csci4100.uoit.project;

import android.support.v7.app.AppCompatActivity;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.Comparator;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.net.URL;

public class SearchPlacesActivity extends AppCompatActivity implements  LocationListener{

    String spinner_price;
    private String address;
    private DatabaseReference Database;
    LocationManager locManager;
    double latitude;
    double longitude;
    double latitude2;
    double longitude2;
    String type;
    String restaurant_name;
    String description;
    String price_range;
    String phone_number;
    String rating;
    String sqlRating;
    String worldRating;
    String website;
    String image;
    float distance;
    float distanceInKm;
    int count = 0;
    String url = "https://csci4100app.firebaseio.com/.json";
    String jsonData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_search_places);

        Database = FirebaseDatabase.getInstance().getReference().child("Restaurants");

        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Intent intent = getIntent();
        latitude = Double.valueOf(intent.getStringExtra("latitude"));
        longitude = Double.valueOf(intent.getStringExtra("longitude"));
        DownloadSearchRestaurantsTask downloadTask = new  DownloadSearchRestaurantsTask();
        downloadTask.execute(url , String.valueOf(latitude),String.valueOf(longitude));

    }


    public void onLocationChanged(Location location) {
        // update the user interface to match (e.g. map);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        Log.i("App", "onLocationChanged");
    }

    public void onProviderDisabled(String provider) {
        // switch providers or request the user enable
        Log.i("App", "Provider - "+ provider);
    }
    public void onProviderEnabled(String provider) {
        // possibly, switch providers (if better)
        Log.i("App", "Provider enabled: " + provider);
    }
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // ask user to enable, shut down, go without
        Log.i("App", "Provider ("+ provider +") status changed: " + status);
    }



    public void showRestaurants(ArrayList<Restaurant> data) {
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this, data);
        ListView listView = (ListView)findViewById(R.id.search_places_listView);
        listView.setAdapter(restaurantAdapter);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SearchPlacesActivity.this, SearchActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    class DownloadSearchRestaurantsTask extends AsyncTask<String, Void, ArrayList<Restaurant>> {
        ArrayList<Restaurant> restaurants2 = new ArrayList<>();

        @Override
        protected ArrayList<Restaurant> doInBackground(String... params) {
            try {
                SqliteReviewHelper reviewHelper= new SqliteReviewHelper(SearchPlacesActivity.this);
                Intent intent = getIntent();
                spinner_price = intent.getStringExtra("price");
                URL url = new URL(params[0]);
                double lat = Double.parseDouble(params[1]);
                double lot = Double.parseDouble(params[2]);

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
                        //Log.v("Details", getRestaurant.toString());
                        //Log.d("Size", String.valueOf(getRestaurant.length()));
                        for(int i = 1; i <= getRestaurant.length(); i++){

                            JSONObject restaurant_details = getRestaurant.getJSONObject("Restaurant_"+i);
                            //Log.d("Number", "Restaurant_"+i);


                            type = restaurant_details.getString("type");
                            String[] types = type.split("\\s*,\\s*");
                            float[] results = new float[1];
                            latitude2 = restaurant_details.getDouble("latitude");
                            longitude2 = restaurant_details.getDouble("longitude");
                            Location.distanceBetween(latitude2, longitude2, lat, lot, results);
                            distance = Math.round(results[0]);


                            Intent intent2 = getIntent();
                            double radius = Double.parseDouble(intent2.getStringExtra("radius"));


                            if (distance <= (radius * 1000)){

                                for (int j = 0; j < types.length; j++) {

                                    Intent intent3 = getIntent();
                                    String search = intent3.getStringExtra("search");

                                    String lowercaseSearch = search.toLowerCase();
                                    lowercaseSearch.replaceAll("\\s", "");

                                    String type_search = types[j].toLowerCase();
                                    type_search.replaceAll("\\s", "");
                                    restaurant_name = restaurant_details.getString("restaurant_name");
                                    String restaurantLowerCase = restaurant_name.toLowerCase();
                                    restaurantLowerCase.replaceAll("\\s", "");

                                    if (type_search.equals(lowercaseSearch) || restaurantLowerCase.equals(lowercaseSearch)) {
                                        Log.d("Size","What up");
                                        spinner_price = intent.getStringExtra("price");
                                        price_range = restaurant_details.getString("price_range");

                                        if (price_range.equals(spinner_price)) {
                                            Log.d("Size","What up2");

                                            address = restaurant_details.getString("address");
                                            description = restaurant_details.getString("description");
                                            type = restaurant_details.getString("type");
                                            phone_number = restaurant_details.getString("phone_number");
                                            rating = restaurant_details.getString("rating");
                                            website = restaurant_details.getString("website");
                                            image = restaurant_details.getString("image");
                                            String hours1 = "";

                                            sqlRating=reviewHelper.getAverageRating(address);
                                            worldRating=getWorldReview(address);

                                            String[] hours_array = restaurant_details.getString("hours").
                                                    split("\\s*,\\s*");


                                            for(int a = 0; a < hours_array.length; a++){
                                                hours1 += hours_array[a] + "\n";
                                                //Log.d("Hours", hours_array[a]);
                                            }

                                            distanceInKm = distance / 1000;

                                            Restaurant restaurant = new Restaurant(count ,restaurant_name, address, description,
                                                    type, price_range, phone_number, rating, sqlRating, worldRating, hours1, website, image,
                                                    distanceInKm, latitude2, longitude2);

                                            Log.d("Add", String.valueOf(restaurant));

                                            restaurants2.add(restaurant);

                                            break;
                                        }

                                        else if (spinner_price.equals("All")) {

                                            if (price_range.equals("1") || price_range.equals("2") || price_range.equals("3")
                                                    || price_range.equals("4")) {

                                                Log.d("Size","What up3");
                                                address = restaurant_details.getString("address");
                                                description = restaurant_details.getString("description");
                                                type = restaurant_details.getString("type");
                                                phone_number = restaurant_details.getString("phone_number");
                                                rating = restaurant_details.getString("rating");
                                                rating = restaurant_details.getString("rating");
                                                website = restaurant_details.getString("website");
                                                image = restaurant_details.getString("image");
                                                String hours2 = "";

                                                sqlRating=reviewHelper.getAverageRating(address);
                                                worldRating=getWorldReview(address);

                                                String[] hours_array = restaurant_details.getString("hours")
                                                        .split("\\s*,\\s*");



                                                for(int z = 0; z < hours_array.length; z++){
                                                    hours2 += hours_array[z] + "\n";
                                                }

                                                distanceInKm = distance / 1000;

                                                Restaurant restaurant = new Restaurant(count ,restaurant_name, address, description,
                                                        type, price_range, phone_number, rating, sqlRating, worldRating, hours2, website, image,
                                                        distanceInKm, latitude2, longitude2);

                                                Log.d("Add", String.valueOf(restaurant));

                                                restaurants2.add(restaurant);

                                                break;
                                            }
                                        }
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

            return restaurants2;
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

            TextView title = (TextView) findViewById(R.id.search_places);
            title.setText(getResources().getString(R.string.nearbyPlaces));
            TextView numberOfResults = (TextView) findViewById(R.id.search_numberOfResults);
            numberOfResults.setText(String.valueOf("Number of Results: " + data.size()));

            showRestaurants(data);

        }
    }
    public String getWorldReview(String address){
        float total=0,count=0;
        try {
            JSONObject jObject= new JSONObject(jsonData);

            Iterator<String> keys;

            String key = "Reviews";
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
        if(count==0)return "0/5";
        return (total/count)+"/5";
    }
}
