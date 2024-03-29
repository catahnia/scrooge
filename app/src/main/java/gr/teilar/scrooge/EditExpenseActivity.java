package gr.teilar.scrooge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gr.teilar.scrooge.Core.Category;
import gr.teilar.scrooge.Core.Expense;
import gr.teilar.scrooge.Database.CategoryDb;
import gr.teilar.scrooge.Database.ExpenseDb;

public class EditExpenseActivity extends AppCompatActivity {

    public static final String DESCRIPTON = "ExpenseDescription";
    public static final String CATEGORY = "ExpenseCategory";
    public static final String AMOUNT = "ExpenseAmount";
    public static final String EXPENSEID = "ExpenseId";

    private List<Category> categories = new ArrayList<>();

    private Category selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        //Σ αυτή τη δραστηριότητα παίρνουμε το Intent και αποθηκεύμουμε τα έξτρα που έχει.
        //Στη συνέχεια παίρνουμε τα στοιχεία της διάταξης και εμφανίζουμε στην οθόνη τα στοιχεία που πήραμε απ το intent
        //Τέλος έχουμε ενα κουμπί που διαβάζει τα στοιχεία που υπάρχουν στην οθόνη και τα αποθηκεύει στη βάση.
        categories = CategoryDb.getCategories(this);

        final Intent intent = getIntent();

        final EditText descriptionEditTest = (EditText) findViewById(R.id.descriptionEditText);
        final EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        final Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        Button editExpenseButton = (Button) findViewById(R.id.editExpenseButton);
        Button deleteExpenseButton = (Button) findViewById(R.id.deleteExpenseButton);

        descriptionEditTest.setText(intent.getStringExtra(DESCRIPTON));
        amountEditText.setText(Float.toString(intent.getFloatExtra(AMOUNT,0)));

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,categories);
        categorySpinner.setAdapter(arrayAdapter);

        //Εδώ, λόγω του ότι οι κατηγορίες αποθηκεόνται με autoincrement key ξερουμε οτι θα έχουν id 1,2 ...
        //Έτσι η θέση στη λίστα θα είναι ότι το id -1 , λόγω του ότι η αρίθμηση ξεκινά απ το μηδέν.
        categorySpinner.setSelection((int)(intent.getLongExtra(CATEGORY,0))-1);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categories.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = descriptionEditTest.getText().toString();
                float amount = Float.parseFloat(amountEditText.getText().toString());
                long expenseId = intent.getLongExtra(EXPENSEID, -1);
                int result;

                result = ExpenseDb.editExpense(EditExpenseActivity.this, expenseId, description, amount, selectedCategory.getCategoryId());

                if (result == -1){
                    Toast.makeText(getApplicationContext(), R.string.editExpenseFail, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.editExpenseSuccess, Toast.LENGTH_LONG).show();
                }

            }
        });

        deleteExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long expenseId = intent.getLongExtra(EXPENSEID, -1);
                int result;

                result = ExpenseDb.deleteExpense(EditExpenseActivity.this, expenseId);

                if (result == -1){
                    Toast.makeText(getApplicationContext(), R.string.expense_delete_fail, Toast.LENGTH_LONG).show();
                } else {
                    EditExpenseActivity.this.finish();
                    Intent i = new Intent(getApplicationContext(), SeeExpensesActivity.class);
                    //Τροπος για να καθαρισουμε το back stack για μην εμφανιστει η λίστα με όλα τα έξοδα πριν την διαγραφή
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                    Toast.makeText(getApplicationContext(), R.string.expense_delete_success, Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
