package ca.csci4100.uoit.project;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class SqliteReviewHelper extends SQLiteOpenHelper {
    // Database Name
    private static final String DATABASE_NAME = "ReviewManager.db";

    // User table name
    private static final String TABLE_NAME = "reviews";

    // User Table Columns names
    private static final String COLUMN_REVIEW_ADDRESS = "address";
    private static final String COLUMN_REVIEW_DESCRIPTION = "description";
    private static final String COLUMN_REVIEW_RATING = "rating";
    private static final String COLUMN_REVIEW_DATE = "date";

    public SqliteReviewHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_NAME +
                "("+COLUMN_REVIEW_DATE+" text,"
                    +COLUMN_REVIEW_ADDRESS +" text,"
                    +COLUMN_REVIEW_DESCRIPTION+" text, "
                    +COLUMN_REVIEW_RATING+" text)"
        );
    }
    public void remakeTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE "+TABLE_NAME);
        db.execSQL(
                "create table " + TABLE_NAME +
                        "("+COLUMN_REVIEW_DATE+" text,"
                        +COLUMN_REVIEW_ADDRESS +" text,"
                        +COLUMN_REVIEW_DESCRIPTION+" text, "
                        +COLUMN_REVIEW_RATING+" text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //todo
    }
    public List<Review> getAllReviews(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {
                COLUMN_REVIEW_DATE,
                COLUMN_REVIEW_ADDRESS,
                COLUMN_REVIEW_DESCRIPTION,
                COLUMN_REVIEW_RATING
        };
        String[] args = new String[] {
        };
        Cursor cursor = db.query(TABLE_NAME, columns,
                "", args,
                "", "",
                COLUMN_REVIEW_ADDRESS);

        List<Review> reviews = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //System.out.print("|");
            String date=cursor.getString(0);
            String address=cursor.getString(1);
            String description=cursor.getString(2);
            float rating=cursor.getFloat(3);

            Review review = new Review(rating,address,date,description);

            reviews.add(review);

            cursor.moveToNext();
        }

        return reviews;
    }
    public List<Review> getAllReviewsWhere(String targetAddress){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] {
            COLUMN_REVIEW_DATE,
            COLUMN_REVIEW_ADDRESS,
            COLUMN_REVIEW_DESCRIPTION,
            COLUMN_REVIEW_RATING
        };
        String[] args = new String[] {
            targetAddress
        };
        Cursor cursor = db.query(TABLE_NAME, columns,
            COLUMN_REVIEW_ADDRESS+"=?", args,
            "", "",
            COLUMN_REVIEW_ADDRESS);

        List<Review> reviews = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //System.out.print("|");
            String date=cursor.getString(0);
            String address=cursor.getString(1);
            String description=cursor.getString(2);
            float rating=cursor.getFloat(3);

            Review review = new Review(rating,address,date,description);

            reviews.add(review);

            cursor.moveToNext();
        }

        return reviews;
    }
    public Review addReview(float rating,String address,String date,String description){
        /*System.out.println(rating);
        System.out.println(address);
        System.out.println(date);
        System.out.println(description);*/

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_DATE,date);
        values.put(COLUMN_REVIEW_ADDRESS,address);
        values.put(COLUMN_REVIEW_DESCRIPTION,description);
        values.put(COLUMN_REVIEW_RATING,rating);
        db.insert(TABLE_NAME,null,values);

        Review review = new Review(rating,address,date,description);
        return review;
    }
    public void printAll(){
        List<Review> list = getAllReviews();
        for(int x=0;x<list.size();x++){
            System.out.println(list.get(x));
        }

    }
    public String getAverageRating(String address){
        List<Review> list = getAllReviewsWhere(address);
        if(list.size()==0)return "No Rating";
        float total=0;

        for(int x=0;x<list.size();x++){
            total+=list.get(x).rating;
        }

        return (total/((float)list.size()))+"/5";
    }
}
