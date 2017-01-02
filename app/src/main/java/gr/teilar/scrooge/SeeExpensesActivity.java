package gr.teilar.scrooge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

        final List<Expense> expenses = ExpenseDb.getExpenses(this);

        ExpenseAdapter adapter = new ExpenseAdapter(this, expenses);

        ListView listView = (ListView) findViewById(R.id.expensesList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editExpenseIntent = new Intent(SeeExpensesActivity.this, EditExpenseActivity.class);
                editExpenseIntent.putExtra(EditExpenseActivity.DESCRIPTON,expenses.get(position).getExpenseDescription());
                editExpenseIntent.putExtra(EditExpenseActivity.CATEGORY,expenses.get(position).getExpenseCategory().getCategoryId());
                editExpenseIntent.putExtra(EditExpenseActivity.AMOUNT,expenses.get(position).getExpenseAmount());
                editExpenseIntent.putExtra(EditExpenseActivity.EXPENSEID, expenses.get(position).getExpenseId());
                startActivity(editExpenseIntent);
            }
        });

    }
}
