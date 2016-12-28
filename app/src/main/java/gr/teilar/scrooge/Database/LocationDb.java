package gr.teilar.scrooge.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import gr.teilar.scrooge.Core.Location;

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
        sqLiteDatabase.execSQL("CREATE TABLE locations ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "location_latitude TEXT, "
                + "location_longitude TEXT, "
                + "location_name TEXT);");
    }

    @Override
    public  void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static void insertLocation (Context context, Location location) {
        SQLiteOpenHelper helper = new LocationDb(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("location_latitude", location.getLocationLatitude());
        values.put("location_longitude", location.getLocationLongitude());
        values.put("location_name", location.getLocationName());
        sqLiteDatabase.insert("locations", null, values);

    }
}
