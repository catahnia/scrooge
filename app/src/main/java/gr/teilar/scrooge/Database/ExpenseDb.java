package gr.teilar.scrooge.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
