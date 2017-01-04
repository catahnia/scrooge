package gr.teilar.scrooge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gr.teilar.scrooge.Core.Category;
import gr.teilar.scrooge.Database.CategoryDb;

public class AddCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        //Σ αυτή τη δραστηριότητα προσθέτει ο χρήστης μια κατηγορία. Έχει δυο πεδία εισαγωγής κειμένου κι ένα κουμπί.
        //Το κουμπί έχει έναν listener για την αποθήκευση της κατηγορίας.

        final EditText categoryNameEditText = (EditText) findViewById(R.id.categoryNameEditText);
        final EditText categoryDescriptionEditText = (EditText) findViewById(R.id.categoryDescriptionEditText);
        Button addCategoryButton = (Button) findViewById(R.id.addCategoryButton);

        //Η ηλοποίηση του listener.
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Μεταβλητή αποθήκευσης αποτελέσματος που επιστράφηκε απ τη βάση.
                Long result;

                //Χρησιμοποιούμε την παρακάτω συνάρτηση για την αποθήκευση της κατηγορίας.
                result = CategoryDb.insertCategory(AddCategoryActivity.this , new Category(categoryNameEditText.getText().toString(),categoryDescriptionEditText.getText().toString()));

                //Αν αυτό που επέστρψε η συνάρτηση αποθήκευσης στην βάση είναι -1, τότε δεν έγινε η αποθήκευση
                //και εμφανίζεται μήνυμα λάθους. Αλλίως εμφανίζεται μήνυμα επιτυχίας και καθαρίζονται τα πεδία.
                if(result == -1) {
                    Toast.makeText(getApplicationContext() , R.string.wrongAddCategory, Toast.LENGTH_LONG).show();
                } else {
                    categoryNameEditText.setText("");
                    categoryDescriptionEditText.setText("");
                    Toast.makeText(getApplicationContext() , R.string.successAddCategory, Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}
