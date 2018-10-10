package ca.csci4100.uoit.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SqliteHelper extends SQLiteOpenHelper {



    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_NAME = "users";

    // User Table Columns names
    private static final String COLUMN_USER_FIRSTNAME = "first_name";
    private static final String COLUMN_USER_LASTNAME = "last_name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table users" +
                        "(first_name text,last_name text,email text, password text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }


    }


    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();


        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(COLUMN_USER_FIRSTNAME, user.first_name);

        //Put email in  @values
        values.put(COLUMN_USER_LASTNAME, user.last_name);

        //Put email in  @values
        values.put(COLUMN_USER_EMAIL, user.email);

        //Put password in  @values
        values.put(COLUMN_USER_PASSWORD, user.password);

        // insert row
        db.insert(TABLE_NAME, null, values);

        db.close();


    }

    /*
    public boolean CheckIfEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM TABLE_NAME WHERE + COLUMN_USER_EMAIL" + " =" + email ;
        Cursor cursor = db.rawQuery(query, null);
        System.out.print("query");

        if (cursor.getCount() == 0) {
            return true;
        }
        else{
            return false;
        }

    }

    */

}
