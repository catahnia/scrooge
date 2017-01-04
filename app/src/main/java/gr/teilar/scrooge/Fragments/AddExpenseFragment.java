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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gr.teilar.scrooge.AddCategoryActivity;
import gr.teilar.scrooge.AddExpenseActivity;
import gr.teilar.scrooge.Core.Category;
import gr.teilar.scrooge.Database.CategoryDb;
import gr.teilar.scrooge.Database.LocationDb;
import gr.teilar.scrooge.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddExpenseFragment extends Fragment {

    private Calendar myCalendar = Calendar.getInstance();

    private Button editDate;

    private List<Category> categories = new ArrayList<>();

    private Category selectedCategory;

    private Button addExpenseButton;

    EditText locationName;

    OnAddExpenseListener listener;


    public AddExpenseFragment() {
        // Required empty public constructor
    }

    //Σ αυτό το fragment υπάρχει η φόρμα για την εισαγωγή των στοιχείων του εξόδου.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment με σκοπό να αποκτήσουμε πρόσβαση στα γραφηκά στοιχεία του fragment
        // και αφού τα επεξεργαστούμε, στο τέλος της createview θα επιστρέψουμε το view
        View rootView = inflater.inflate(R.layout.fragment_add_expense, container, false);

        Date today = new Date();

        Bundle b = this.getArguments();

        editDate = (Button) rootView.findViewById(R.id.dateFrag);
        editDate.setText(new SimpleDateFormat("dd/MM/yy").format(today));

        Button addCategoryButton = (Button) rootView.findViewById(R.id.addCategoryButton);

        addExpenseButton = (Button) rootView.findViewById(R.id.addExpenseButton);

        //Εδώ ελέγχουμε αν έχει εισαχθεί κάποια κατηγορία. αν ναι ενεργοποιούμε το κουμπί για την εισαγωγή εξόδου,
        //αν δεν υπάρχουν έξοδα τότε εμφανίζουμε μήνυμα για εισαγωγή κατηγορίας.
        if (categories.size()<1){
            addExpenseButton.setEnabled(false);
            Toast.makeText(getActivity().getApplicationContext(), R.string.addCategoryFirst, Toast.LENGTH_LONG).show();
        } else {
            addExpenseButton.setEnabled(true);
        }

        final EditText amount = (EditText) rootView.findViewById(R.id.amountfrag);

        final EditText description = (EditText) rootView.findViewById(R.id.descriptionFrag);

        locationName = (EditText) rootView.findViewById(R.id.locationNameEditText);

        //Εδώ παίρνουμε την παράμετρο απ το bundle που αφορά το όνομα της τοποθεσίας (αν έχει βρεθεί) και το εμφανίζουμε
        if(!(b.getString("locationName").equals(""))){
            locationName.setText(b.getString("locationName"));
        }

        final Spinner category = (Spinner) rootView.findViewById(R.id.categoryFrag);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, categories);
        category.setAdapter(adapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = categories.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //O ορισμός της DatePickerDialog και η υλοποίηση του OnDateSetListener
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

        //Listener που καλείται οταν πατήσουμε το κουμπή της ημερομηνίας.
        //και υλοποιεί ενα αντικείμενο τύπου datePickerDialog
        editDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Ο listener για όταν πατάμε το κουμπί για την προσθήκη κατηγορίας, όπου ξεκινάει την δραστηριότητα για την
        //προσθήκη κατηγορίας
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCategory = new Intent(getActivity().getApplicationContext(), AddCategoryActivity.class);
                startActivity(addCategory);
            }
        });

        //Ο listener για όταν πατάμε το κουμπί για την προσθήκη του εξόδου
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

    @Override
    public void onResume() {
        super.onResume();
    }

    //Βοηθητική συνάρτηση για την ενημέρωση του κειμένου στο κουμπί που επιλέγουμε ημερομηνία
    private void updateLabel() {


        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        editDate.setText(sdf.format(myCalendar.getTime()));
    }

    //Δηλώνουμε ενα interface με μια μοναδική συνάρτηση την onAddExpense. Την onAddExpense θα την υλοποιησουμε
    //στη δραστηρίοτητα που ξεκίνησε το συγκεκριμένο fragment με σκοπό να περάσουμε τα στοιχεία που εισήγαγε ο χρήστης.
    //Δεν προσθέτουμε το έξοδο εδώ, γιατί μας λείπουν οι συντεταγμένες του εξόδου.
    //
    //
    //Στην παρούσα έκδοση της εφαρμογής, τα δύο fragment ξεκινάν αφού αποκτήσουμε πρόσβση στην τοποθέσια, λογώ του ότι
    //κοιτάμε στη βάση για το όνομα βάση των συντεταγμένων. Πριν την υλοποίηση αυτης της δυνατότητας, το συγκεκριμένο
    //fragment ξεκινούσε πριν αποκτήσουμε πρόσβαση στην τοποθεσία κι άρα δεν μπορούσε να εχει τις συντεταγμένες κι ετσι
    // δεν μπορούσε να κάνει την αποθήκευση. Θα μπορούσε να κάνει το fragment την αποθήκευση, αλλα το άφησα ως είχε.
    public interface OnAddExpenseListener {
        void onAddExpense(Category selectedCategory, String expenseDate,
                                 String expenseAmount, String expenseDescription, String expenseLocation);
    }

}
