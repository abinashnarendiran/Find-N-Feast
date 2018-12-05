package ca.csci4100.uoit.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    HttpURLConnection connection = null;


    public ImageLoadTask(ImageView imageView) {
        this.imageView = imageView;
    }



    protected Bitmap doInBackground(String... urls) {
        String imageURL = urls[0];
        URL url = stringToURL(imageURL);
        Bitmap bimage = null;
        HttpURLConnection connection = null;
        try {
            // Initialize a new http url connection
            connection = (HttpURLConnection) url.openConnection();

            // Connect the http url connection
            connection.connect();

            // Get the input stream from http url connection
            InputStream inputStream = connection.getInputStream();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            //InputStream in = new java.net.URL(imageURL).openStream();
            bimage = BitmapFactory.decodeStream(bufferedInputStream);


        } catch (Exception e) {
            Log.e("Error Message", e.getMessage());
            e.printStackTrace();
        }
        return bimage;
    }

    // Custom method to convert string to url
    protected URL stringToURL(String urlString){
        try{
            URL url = new URL(urlString);
            return url;
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }


    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
