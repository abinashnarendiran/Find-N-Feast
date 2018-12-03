package ca.csci4100.uoit.project;

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
}
