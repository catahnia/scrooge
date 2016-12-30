package gr.teilar.scrooge.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gr.teilar.scrooge.Core.Category;
import gr.teilar.scrooge.Core.Expense;

/**
 * Created by Mitsos on 22/12/16.
 */

public class ExpenseDb extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "scroogedb";

    private static final int DATABASE_VERSION = 1;

    public ExpenseDb (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    public static long insertExpense (Context context, Expense expense) {

        SQLiteOpenHelper helper = new LocationDb(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("expense_date", expense.getExpenseDate());
        values.put("expense_description", expense.getExpenseDescription());
        values.put("expense_amount", expense.getExpenseAmount());
        values.put("expense_location_id", expense.getExpenseExpenseLocation().getLocationId());
        values.put("expense_category_id", expense.getExpenseCategory().getCategoryId());

        return sqLiteDatabase.insert("expenses", null, values);

    }

    public static List<Expense> getExpenses(Context context) {
        List<Expense> expenses = new ArrayList<>();
        try {
            SQLiteOpenHelper helper = new ExpenseDb(context);
            SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query("expenses",
                    new String[] {"id", "expense_date", "expense_description", "expense_amount",
                    "expense_category_id", "expense_location_id"},null,null,null,null,null);

            while (cursor.moveToNext()){

                expenses.add(new Expense(cursor.getInt(0),new Date(cursor.getInt(1)), cursor.getString(2), cursor.getFloat(3),
                        cursor.getInt(4), cursor.getInt(5)));
            }
            cursor.close();
            sqLiteDatabase.close();
        }
        catch (SQLiteException e) {
            Log.v("Error Read Expenses", e.toString());
        }
        for (Expense expense : expenses) {
            try {
                SQLiteOpenHelper helper = new ExpenseDb(context);
                SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

                Cursor cursor = sqLiteDatabase.query("locations", new String[] {"location_latitude", "location_longitude",
                "location_name"},"id=?", new String[] {Long.toString(expense.getExpenseExpenseLocation().getLocationId())},null,null,null);

                while (cursor.moveToNext()) {
                    expense.getExpenseExpenseLocation().setLocationLatitude(cursor.getDouble(0));
                    expense.getExpenseExpenseLocation().setLocationLongitude(cursor.getDouble(1));
                    expense.getExpenseExpenseLocation().setLocationName(cursor.getString(2));
                }

                cursor.close();
                sqLiteDatabase.close();
            } catch (SQLiteException e){
                Log.v("Error Read Locations", e.toString());
            }

            try {
                SQLiteOpenHelper helper = new ExpenseDb(context);
                SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

                Cursor cursor = sqLiteDatabase.query("categories", new String[] {"category_name", "category_description"},
                        "id=?", new String[] {Long.toString(expense.getExpenseCategory().getCategoryId())},null,null,null);

                while (cursor.moveToNext()) {
                    expense.getExpenseCategory().setCategoryName(cursor.getString(0));
                    expense.getExpenseCategory().setCategoryDescription(cursor.getString(1));
                    Log.v("category", expense.getExpenseExpenseLocation().getLocationName());

                }

                cursor.close();
                sqLiteDatabase.close();

            } catch (SQLiteException e){
                Log.v("Error Read Categories", e.toString());
            }

        }

        return expenses;
    }
}
