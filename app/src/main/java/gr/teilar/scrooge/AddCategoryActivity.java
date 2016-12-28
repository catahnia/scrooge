package gr.teilar.scrooge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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



        final EditText categoryNameEditText = (EditText) findViewById(R.id.categoryNameEditText);
        final EditText categoryDescriptionEditText = (EditText) findViewById(R.id.categoryDescriptionEditText);
        Button addCategoryButton = (Button) findViewById(R.id.addCategoryButton);

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long result;
                result = CategoryDb.insertCategory(AddCategoryActivity.this , new Category(categoryNameEditText.getText().toString(),categoryDescriptionEditText.getText().toString()));

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
