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

        categories = CategoryDb.getCategories(this);

        final Intent intent = getIntent();



        final EditText descriptionEditTest = (EditText) findViewById(R.id.descriptionEditText);
        final EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        final Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        Button editExpenseButton = (Button) findViewById(R.id.editExpenseButton);

        descriptionEditTest.setText(intent.getStringExtra(DESCRIPTON));
        amountEditText.setText(Float.toString(intent.getFloatExtra(AMOUNT,0)));

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,categories);
        categorySpinner.setAdapter(arrayAdapter);
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
                    Toast.makeText(getApplicationContext(), "Expense was not saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Expense Successfully changed", Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}
