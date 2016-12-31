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

                expenses.add(new Expense(cursor.getLong(0),new Date(cursor.getLong(1)), cursor.getString(2), cursor.getFloat(3),
                        cursor.getInt(5), cursor.getInt(4)));

            }
            cursor.close();
            sqLiteDatabase.close();
        }
        catch (SQLiteException e) {
            Log.v("Error Read Expenses", e.toString());
        }

        for (Expense expense : expenses) {
            expense.setExpenseCategory(CategoryDb.getCategory(context, expense.getExpenseCategory().getCategoryId()));
            expense.setExpenseExpenseLocation(LocationDb.getLocation(context, expense.getExpenseExpenseLocation().getLocationId()));

        }

        return expenses;
    }

    public static int editExpense(Context context, long expenseId, String description, float amount, Long categoryId){

        int result=-1;

        try {
            SQLiteOpenHelper helper = new ExpenseDb(context);
            SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put("expense_description", description);
            values.put("expense_amount", amount);
            values.put("expense_category_id", categoryId);

            result = sqLiteDatabase.update("expenses",values,"id=?",new String[] {Long.toString(expenseId)});

            sqLiteDatabase.close();

        }
        catch (SQLiteException e) {
            Log.v("UpdateExpense", e.toString());
        }
        return result;


    }
}
