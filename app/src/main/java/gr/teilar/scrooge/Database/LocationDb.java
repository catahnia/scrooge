package gr.teilar.scrooge.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import gr.teilar.scrooge.Core.ExpenseLocation;

/**
 * Created by Mitsos on 22/12/16.
 */

public class LocationDb extends SQLiteOpenHelper{


    private static final String DATABASE_NAME = "scroogedb";

    private static final int DATABASE_VERSION = 1;

    public LocationDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


    }

    @Override
    public  void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static long insertLocation (Context context, ExpenseLocation expenseLocation) {
        SQLiteOpenHelper helper = new LocationDb(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("location_latitude", expenseLocation.getLocationLatitude());
        values.put("location_longitude", expenseLocation.getLocationLongitude());
        values.put("location_name", expenseLocation.getLocationName());

        return sqLiteDatabase.insert("locations", null, values);

    }
}
