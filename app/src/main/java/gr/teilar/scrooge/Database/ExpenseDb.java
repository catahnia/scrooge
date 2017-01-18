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
import java.util.jar.Pack200;

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

    //Μέθοδος για την είσοδο ενός εξόδου
    public static long insertExpense (Context context, Expense expense) {

        long result;

        SQLiteOpenHelper helper = new LocationDb(context);
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("expense_date", expense.getExpenseDate());
        values.put("expense_description", expense.getExpenseDescription());
        values.put("expense_amount", expense.getExpenseAmount());
        values.put("expense_location_id", expense.getExpenseExpenseLocation().getLocationId());
        values.put("expense_category_id", expense.getExpenseCategory().getCategoryId());

        result = sqLiteDatabase.insert("expenses", null, values);

        sqLiteDatabase.close();

        return result;

    }

    //Μέθοδος που επιστρέφει τη λίστα με όλα τα έξοδα της βάσης
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

        //Εδώ, για κάθε έξοδο που υπάρχει στη βάση, διαβάζουμε (με βάση το αντίστοιχο αποθηκευμενο id) την κατηγορια και
        //την τοποθεσια του εξόδου, καθώς και τις αντίστοιχες πληροφορίες τους
        for (Expense expense : expenses) {
            expense.setExpenseCategory(CategoryDb.getCategory(context, expense.getExpenseCategory().getCategoryId()));
            expense.setExpenseExpenseLocation(LocationDb.getLocation(context, expense.getExpenseExpenseLocation().getLocationId()));

        }

        return expenses;
    }

    //Μέθοδος για την ενήμερωση ενός εξόδου
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

    //Μέθοδος που χρησιμεύει στην ανάλυση εξόδων.
    //Παίρνει δύο ημερομηνίες και κάνει αναζήτηση κάνοντας groupBy categoryId και orderBy το μεγαλύτερο σύνολο
    public static List<Expense> getAnalysedExpenses (Context context, long from, long to){

        List<Expense> expenses = new ArrayList<>();

        try {
            SQLiteOpenHelper helper = new ExpenseDb(context);
            SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query("expenses",new String[] {"expense_category_id", "SUM(expense_amount) AS Total"},
                    "expense_date BETWEEN ? AND ?",new String[] {Long.toString(from), Long.toString(to)}, "expense_category_id",null, "Total DESC");

            while (cursor.moveToNext()){
                Expense e = new Expense();
                e.setCategoryId(cursor.getLong(0));
                e.setExpenseAmount(cursor.getFloat(1));
                expenses.add(e);
            }

            cursor.close();
            sqLiteDatabase.close();

        }catch (SQLiteException e) {
            Log.v("AnalyseExpense", e.toString());
        }
        for (Expense expense : expenses) {
            expense.setExpenseCategory(CategoryDb.getCategory(context, expense.getExpenseCategory().getCategoryId()));
        }

        return expenses;

    }

    public static int deleteExpense(Context context, long expenseId) {
        int result=-1;

        try {
            SQLiteOpenHelper helper = new ExpenseDb(context);
            SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();


            result = sqLiteDatabase.delete("expenses","id=?",new String[] {Long.toString(expenseId)});

            sqLiteDatabase.close();

        }
        catch (SQLiteException e) {
            Log.v("DeleteExpense", e.toString());
        }
        return result;
    }
}
