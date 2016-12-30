package gr.teilar.scrooge.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import gr.teilar.scrooge.Core.Category;
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

    public static ExpenseLocation getLocation (Context context, long id) {
        ExpenseLocation expenseLocation = new ExpenseLocation();
        try {
            SQLiteOpenHelper helper = new CategoryDb(context);
            SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query("locations", new String[] {"location_latitude", "location_longitude",
                    "location_name"},"id=?", new String[] {Long.toString(id)},null,null,null);

            while (cursor.moveToNext()) {
                expenseLocation.setLocationId(id);
                expenseLocation.setLocationLatitude(cursor.getDouble(0));
                expenseLocation.setLocationLongitude(cursor.getDouble(1));
                expenseLocation.setLocationName(cursor.getString(2));
            }
            cursor.close();
            sqLiteDatabase.close();

        }
        catch (SQLiteException e) {
            Log.v("Error Read Categories", e.toString());

        }

        return expenseLocation;
    }

}
