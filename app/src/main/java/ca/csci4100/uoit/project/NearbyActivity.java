package ca.csci4100.uoit.project;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class NearbyActivity extends AppCompatActivity implements LocationListener {
    String[] arraySpinner = new String[] {"1", "2", "3", "4" , "All"};
    private String locationProvider;
    public static final int PERMISSION_REQUEST_LOCATION = 410001;
    LocationManager locManager;
    String longitude;
    String latitude;
    public static int GET_RESULT_INTENT = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        Spinner spinner = (Spinner) findViewById(R.id.price_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinner.setAdapter(adapter);

        setupLocationServices();

    }

    private void setupLocationServices() {
        requestLocationPermissions();
        locationProvider = LocationManager.GPS_PROVIDER;
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locManager .isProviderEnabled(locationProvider)) {
            String settings = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            Intent enableGPS = new Intent(settings);
            startActivity(enableGPS);
        }
        else{
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                updateLocation();
            }
            else {
                // TODO: Tell the user of any features that will be disabled
            }
        }
    }

    private void requestLocationPermissions() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // no location permissions given yet

            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // TODO: explain to the user why we need it
            }

            String[] perms = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
            requestPermissions(perms, PERMISSION_REQUEST_LOCATION);
        }
    }


    public void search_nearby_restaurants(View view){
        Spinner spinner = (Spinner) findViewById(R.id.price_spinner);
        String price = spinner.getSelectedItem().toString();
        Intent intent = new Intent(NearbyActivity.this, NearbyPlacesActivity.class);
        intent.putExtra("latitude", latitude(latitude));
        intent.putExtra("longitude", longitude(longitude));
        intent.putExtra("price", price);
        Log.d("latitude", latitude(latitude));
        Log.d("longitude", longitude(longitude));
        startActivityForResult(intent,GET_RESULT_INTENT);

    }

    private String latitude(String lat){
        return lat;

    }

    private String longitude(String lot){
        return lot;

    }

    private void updateLocation() {

        // just to test this ongoing location functionality
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Location location2 = locManager.getLastKnownLocation(locationProvider);
            latitude = String.valueOf(location2.getLatitude());
            longitude = String.valueOf(location2.getLongitude());


            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            locManager.requestLocationUpdates(locationProvider, 1000, 0, this);
            return;
        }

        locManager.requestLocationUpdates(locationProvider, 1000, 0, this);
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent resultIntent) {
        if(requestCode == GET_RESULT_INTENT) {
            if(resultCode == Activity.RESULT_OK){
                //correct password
                Toast toast = Toast.makeText(NearbyActivity.this, "No Results", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        // TODO: handle the province select event
    }


    public void onLocationChanged(Location location) {
        // update the user interface to match (e.g. map);

        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());


    }

    public void onProviderDisabled(String provider) {
        // switch providers or request the user enable
        Log.i("App", "Provider - " + provider);
    }

    public void onProviderEnabled(String provider) {
        // possibly, switch providers (if better)
        Log.i("App", "Provider enabled: " + provider);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // ask user to enable, shut down, go without
        Log.i("App", "Provider (" + provider + ") status changed: " + status);
    }
}