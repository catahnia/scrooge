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

        //Σ αυτή τη δραστηριότητα διαβάζουμε όλα τα έξοδα που υπάρχουν στη ΒΔ και τις παρουσιάζουμε σ ένα listview
        //μέσω ενός δικού μας adapter. Ορίσαμε τον ExpenseAdapter ο οποίος επεκτείνει τον ArrayAdapter.
        //Στη συνέχεια βάζουμε έναν listener στη listview και ανάλογα με το πιο έξοδο επέλεξε ο χρήστης,
        //ξεκινάμε ένα Intent και βάζουμε σαν έξτρα τις πληροφορίες του εξόδου που μας ενδιαφέρουν(περιγραφή, κατηγορία,
        //ποσό καθώς και το id του εξόδου.
        //Εδώ να σημειώσω οτι προσπάθησα να βάλω όλο το expense σαν extra(που θα ηταν πιο σωστό),
        // κάνοντάς το serializable, αλλά μάλλον αποτελείται κι απο άλλα αντικείμενα, δεν γινόταν.
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
