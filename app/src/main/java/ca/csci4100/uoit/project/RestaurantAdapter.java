package ca.csci4100.uoit.project;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RestaurantAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Restaurant> data;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Restaurant RestaurantToDisplay = data.get(position);
        if (convertView == null) {
            // create the layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_restaurant_item,
                    parent, false);
        }

        // populate the views with the data from story

        TextView lblNumber = (TextView)convertView.findViewById(R.id.lblNumber);
        lblNumber.setText(String.valueOf(RestaurantToDisplay.getCount() + "."));

        TextView lblRestaurant_name = (TextView)convertView.findViewById(R.id.lblRestaurant);
        lblRestaurant_name.setText(RestaurantToDisplay.getRestaurant_name());

        TextView lblAddress = (TextView)convertView.findViewById(R.id.lblAddress);
        SpannableString content = new SpannableString(RestaurantToDisplay.getAddress());
        content.setSpan(new UnderlineSpan(), 0, RestaurantToDisplay.getAddress().length(), 0);
        lblAddress.setText(content);

        TextView lblDescription = (TextView)convertView.findViewById(R.id.lblDescription);
        lblDescription .setText(RestaurantToDisplay.getDescription());

        TextView lblType = (TextView)convertView.findViewById(R.id.lblType);
        lblType.setText(RestaurantToDisplay.getType());

        TextView lblPriceRange = (TextView)convertView.findViewById(R.id.lblPriceRange);
        lblPriceRange.setText(RestaurantToDisplay.getPrice_range());

        TextView lblPhone_number = (TextView)convertView.findViewById(R.id.lblPhone_number);
        lblPhone_number.setText(RestaurantToDisplay.getPhone_number());

        TextView lblRating = (TextView)convertView.findViewById(R.id.lblRating);
        lblRating.setText(RestaurantToDisplay.getType());

        TextView lblHours = (TextView)convertView.findViewById(R.id.lblHours);
        lblHours.setText(RestaurantToDisplay.getHours());

        TextView lblWebsite = (TextView)convertView.findViewById(R.id.lblWebsite);
        SpannableString content2 = new SpannableString(RestaurantToDisplay.getWebsite());
        content2.setSpan(new UnderlineSpan(), 0, RestaurantToDisplay.getWebsite().length(), 0);
        lblWebsite.setText(content2);

        ImageView lblImage = (ImageView)convertView.findViewById(R.id.lblImage);
        // Set Image
        new ImageLoadTask(lblImage).execute(RestaurantToDisplay.getImage());

        TextView lblDistance = (TextView)convertView.findViewById(R.id.lblDistance);
        lblDistance.setText(String.valueOf(RestaurantToDisplay.getDistance() + " km"));


        lblWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www."+ RestaurantToDisplay.getWebsite()));
                context.startActivity(intent);

            }
        });

        lblAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(context, ShowMapsActivity.class);
                String latitude = String.valueOf(RestaurantToDisplay.getLatitude());
                String longitude = String.valueOf(RestaurantToDisplay.getLongitude());
                intent2.putExtra("latitude", latitude);
                intent2.putExtra("longitude", longitude);
                intent2.putExtra("name", RestaurantToDisplay.getRestaurant_name());
                intent2.putExtra("address", RestaurantToDisplay.getAddress());
                context.startActivity(intent2);

            }
        });



        return convertView;

    }
}