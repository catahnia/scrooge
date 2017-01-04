package gr.teilar.scrooge.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gr.teilar.scrooge.Core.Category;

/**
 * Created by Mitsos on 22/12/16.
 */

public class CategoryDb extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "scroogedb";

    private static final int DATABASE_VERSION = 1;

    public CategoryDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Χρησιμοποιέιται για την δημιουργία της βάσης. Επείδη για να κάνει ο χρήστης το οτιδίποτε,
    // πρέπει πρώτα να δημιουργήσει κατηγόρια, έτσι δημιουργούμε εδώ τη βάση.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Πίνακας για τις κατηγορίες
        sqLiteDatabase.execSQL("CREATE TABLE categories ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "category_name TEXT, "
                + "category_description TEXT, "
                + "category_image_id INTEGER);");

        //Πίνακας για τις τοποθεσίες
        sqLiteDatabase.execSQL("CREATE TABLE locations ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "location_latitude REAL, "
                + "location_longitude REAL, "
                + "location_name TEXT);");

        //Πίνακας για τα έξοδα
        sqLiteDatabase.execSQL("CREATE TABLE expenses ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "expense_date INTEGER, "
                + "expense_description TEXT, "
                + "expense_amount REAL, "
                + "expense_location_id INTEGER , "
                + "expense_category_id INTEGER);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Μέθοδος για την είσοδο κατηγορίας
    public static long insertCategory (Context context,Category category) {
        SQLiteOpenHelper helper = new CategoryDb(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("category_name", category.getCategoryName());
        values.put("category_description", category.getCategoryDescription());
        values.put("category_image_id", -1);
        long result = sqLiteDatabase.insert("categories", null, values);

        sqLiteDatabase.close();

        return result;

    }

    //Μέθοδος που μας επιστρέφει τις αποθηκευμένες κατηγορίες
    public static List<Category> getCategories(Context context) {
        List<Category> categories = new ArrayList<>();

        try {
            SQLiteOpenHelper helper = new CategoryDb(context);
            SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query("categories",
                    new String[] {"id", "category_name"},null,null,null,null,null);

            while (cursor.moveToNext()){
                categories.add(new Category(cursor.getInt(0),cursor.getString(1),""));
            }
            cursor.close();
            sqLiteDatabase.close();
        }
        catch (SQLiteException e) {
            Log.v("Error Read Categories", e.toString());
        }


        return categories;
    }

    //Μέθοδος που επιστρέφει κατηγορία με βάση το id της
    public static Category getCategory (Context context, long id) {
        Category category = new Category();
        try {
            SQLiteOpenHelper helper = new CategoryDb(context);
            SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query("categories", new String[] {"category_name", "category_description"},
                    "id=?", new String[] {Long.toString(id)},null,null,null);

            while (cursor.moveToNext()) {
                category.setCategoryId(id);
                category.setCategoryName(cursor.getString(0));
                category.setCategoryDescription(cursor.getString(1));
            }
            cursor.close();
            sqLiteDatabase.close();

        }
        catch (SQLiteException e) {
            Log.v("Error Read Categories", e.toString());

        }

        return category;
    }


}
