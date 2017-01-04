package gr.teilar.scrooge;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addCategoryButton = (Button) findViewById(R.id.addCategoryButton);
        Button addExpenseButton = (Button) findViewById(R.id.addExpenseButton);
        Button seeExpensesButton = (Button) findViewById(R.id.seeExpensesButton);
        Button analyseButton = (Button) findViewById(R.id.analyseExpenses);

        //Σ αυτή τη δραστηριότητα έχουμε 4 κουμπία που το καθένα μας πηγαίνει στην αντίστοιχη δραστηριότητα με τα ζητούμενα
        //της εργασιας.Για κάθε ένα απο τα κουμπία έχουμε κι έναν listener, οποίο καλείται όταν πατηθέι κάποιο κουμπί,
        //δημιουργεί ένα intent και ξεκινάει τη δραστηριότητα.

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCategoryIntent = new Intent(MainActivity.this, AddCategoryActivity.class);
                startActivity(addCategoryIntent);
            }
        });

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addExpenseIntent = new Intent(MainActivity.this, AddExpenseActivity.class);
                startActivity(addExpenseIntent);
            }
        });

        seeExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seeExpensesIntent = new Intent(MainActivity.this, SeeExpensesActivity.class);
                startActivity(seeExpensesIntent);
            }
        });

        analyseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent analyseIntent = new Intent(MainActivity.this, AnalyseExpensesActivity.class);
                startActivity(analyseIntent);
            }
        });

    }

}
