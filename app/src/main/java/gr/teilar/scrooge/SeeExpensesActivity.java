package gr.teilar.scrooge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import gr.teilar.scrooge.Adapter.ExpenseAdapter;
import gr.teilar.scrooge.Core.Expense;
import gr.teilar.scrooge.Database.ExpenseDb;

public class SeeExpensesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_expenses);

        List<Expense> expenses = ExpenseDb.getExpenses(this);

        ExpenseAdapter adapter = new ExpenseAdapter(this, expenses);

        ListView listView = (ListView) findViewById(R.id.expensesList);

        listView.setAdapter(adapter);


    }
}
