package gr.teilar.scrooge.Fragments;



import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gr.teilar.scrooge.AddCategoryActivity;
import gr.teilar.scrooge.Core.Category;
import gr.teilar.scrooge.Database.CategoryDb;
import gr.teilar.scrooge.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends Fragment {

    private Calendar myCalendar = Calendar.getInstance();

    private TextView editDate;

    private List<Category> categories = new ArrayList<>();

    private Category selectedCategory;

    OnAddExpenseListener listener;





    public AddExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_expense, container, false);

        Date today = new Date();


        editDate = (TextView) rootView.findViewById(R.id.dateFrag);
        editDate.setText(new SimpleDateFormat("dd/MM/YYYY").format(today));

        Button addCategoryButton = (Button) rootView.findViewById(R.id.addCategoryButton);

        Button addExpenseButton = (Button) rootView.findViewById(R.id.addExpenseButton);

        final EditText amount = (EditText) rootView.findViewById(R.id.amountfrag);

        final EditText description = (EditText) rootView.findViewById(R.id.descriptionFrag);

        final EditText locationName = (EditText) rootView.findViewById(R.id.locationNameEditText);




        final Spinner category = (Spinner) rootView.findViewById(R.id.categoryFrag);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
        category.setAdapter(adapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = categories.get(i);
                Log.v("selected",Long.toString(selectedCategory.getCategoryId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCategory = new Intent(getActivity().getApplicationContext(), AddCategoryActivity.class);
                startActivity(addCategory);
            }
        });

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddExpense(selectedCategory,editDate.getText().toString(),
                        amount.getText().toString(), description.getText().toString(), locationName.getText().toString());
            }
        });



        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        categories = CategoryDb.getCategories(context);

        try {
            listener = (OnAddExpenseListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }



    }

    private void updateLabel() {


        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        editDate.setText(sdf.format(myCalendar.getTime()));
    }

    public interface OnAddExpenseListener {
        public void onAddExpense(Category selectedCategory, String expenseDate,
                                 String expenseAmount, String expenseDescription, String expenseLocation);
    }

}
