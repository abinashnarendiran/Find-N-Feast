package ca.csci4100.uoit.project;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Review {
    float rating;
    String address;
    String date;
    String description;
    public Review(float rating, String address, String date, String description){
        this.address=address;
        this.date=date;
        this.description=description;
        this.rating=rating;
    }
    public String toString(){
        return address+":\t"+description+"\n"+rating+"/10\n";
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Address", address);
        result.put("Date", date);
        result.put("Description", description);
        result.put("Rating", rating);

        return result;
    }
}
