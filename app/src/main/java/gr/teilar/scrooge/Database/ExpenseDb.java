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
        sqLiteDatabase.execSQL("CREATE TABLE expenses ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "expense_date INTEGER, "
                + "expense_description TEXT, "
                + "expense_amount REAL"
                + "expense_location_id TEXT , "
                + "expense_category_id TEXT,"
                + "FOREIGN KEY(expense_location_id) REFERENCES locations(id), "
                + "FOREIGN KEY(expense_category_id) REFERENCES categories(id)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertCategory (Context context, Expense expense) {

        SQLiteOpenHelper helper = new LocationDb(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("expense_date", expense.getExpenseDate());
        values.put("expense_description", expense.getExpenseDescription());
        values.put("expense_amount", expense.getExpenseAmount());
        values.put("expense_location_id", expense.getExpenseLocation().getLocationId());
        values.put("expenses_category_id", expense.getExpenseCategory().getCategoryId());
        sqLiteDatabase.insert("locations", null, values);

    }
}
