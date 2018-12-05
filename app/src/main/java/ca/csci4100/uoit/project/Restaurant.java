package ca.csci4100.uoit.project;



public class Restaurant {
    private int count;
    private String restaurant_name;
    private String address;
    private String description;
    private String type;
    private String price_range;
    private String phone_number;
    private String rating;
    private String hours;
    private String website;
    private String image;
    private float distance;
    private double latitude;
    private double longitude;


    public Restaurant(int count, String restaurant_name, String address , String description , String type, String price_range, String phone_number,
                      String rating, String hours, String website, String image, float distance, double latitude, double longitude) {

        this.setCount(count);
        this.setRestaurant_name(restaurant_name);
        this.setAddress(address);
        this.setDescription(description);
        this.setType(type);
        this.setPrice_range(price_range);
        this.setPhone_number(phone_number);
        this.setRating(rating);
        this.setHours(hours);
        this.setImage(image);
        this.setWebsite(website);
        this.setDistance(distance);
        this.setLatitude(latitude);
        this.setLongitude(longitude);

    }


    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice_range() {
        return price_range;
    }

    public void setPrice_range(String price_range) {
        this.price_range = price_range;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

