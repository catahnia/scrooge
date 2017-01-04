package gr.teilar.scrooge.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import gr.teilar.scrooge.Database.CategoryDb;
import gr.teilar.scrooge.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalyseDatesFragment extends Fragment {

    private Calendar myCalendar = Calendar.getInstance();

    private Button fromButton;

    private Button toButton;

    OnAnalyseButtonListener listener;

    //Το παρών fragment καλείται απ την δραστηριότητα AnalyseExpenses και περίεχει τρία κουμπιά,
    //δύο για την επιλογή ημερομηνιών κι ένα για την εμφάνιση των αποτελεσμάτων.
    //Δηλώνουμε κι ενα interface για να στείλουμε τις ημερομηνίες στο fragment που εμφανίζει την ανάλυση

    final DatePickerDialog.OnDateSetListener dateFrom = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

            fromButton.setText(sdf.format(myCalendar.getTime()));
        }

    };

    final DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

            toButton.setText(sdf.format(myCalendar.getTime()));
        }

    };


    public AnalyseDatesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_analyse_dates, container, false);

        Date today = new Date();

        fromButton = (Button) rootView.findViewById(R.id.from_date);
        toButton = (Button) rootView.findViewById(R.id.to_date);
        Button analyseButton = (Button) rootView.findViewById(R.id.analyseExpenses);

        fromButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(today));
        toButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(today));


        fromButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), dateFrom, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        toButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), dateTo, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        analyseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromDate = fromButton.getText().toString();
                String toDate = toButton.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date from = null;
                Date to = null;

                //Μετατροπή των δεθέντων ημερομηνιών σε Date
                try {
                    from = sdf.parse(fromDate);
                    to = sdf.parse(toDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Έλεγχος για το αν οι ημερομηνίες είναι σωστά χρονικά
                if( from.getTime()<=to.getTime()){
                    listener.OnAnalyse(from.getTime(), to.getTime());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.wrongDates, Toast.LENGTH_LONG);
                }

            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try {
            listener = (AnalyseDatesFragment.OnAnalyseButtonListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }


    //Η δήλωση του interface και η δήλωση της μεθόδου onAnalyse που θα πρέπει να υλοποιήσει η δραστηριότηα
    // που θα ξεκινήσει το fragment με την ανάλυση
    public interface OnAnalyseButtonListener {
        void OnAnalyse (long from, long to);
    }


}
