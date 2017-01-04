package gr.teilar.scrooge.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import gr.teilar.scrooge.Adapter.AnalyseExpenseAdapter;
import gr.teilar.scrooge.Core.Expense;
import gr.teilar.scrooge.Database.ExpenseDb;
import gr.teilar.scrooge.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalysedListFragment extends Fragment {



    public AnalysedListFragment() {
        // Required empty public constructor
    }

    //Αυτό το fragment δείχνει μια λίστα με την ανάλυση των εξόδων.
    //Για την εμφάνιση χρησιμοποιουμε έναν δικό μας adapter που επεκτείνει τον ArrayAdapter

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_analysed_list, container, false);

        Bundle bundle = getArguments();

        List<Expense> analysedExpenses = ExpenseDb.getAnalysedExpenses(getActivity().getApplicationContext(),
                bundle.getLong("From"), bundle.getLong("To"));


        AnalyseExpenseAdapter adapter = new AnalyseExpenseAdapter(getActivity(), analysedExpenses);

        ListView listView = (ListView) rootView.findViewById(R.id.analysedList);

        listView.setAdapter(adapter);


        return rootView;
    }

}
