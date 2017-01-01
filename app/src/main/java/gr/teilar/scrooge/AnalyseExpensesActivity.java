package gr.teilar.scrooge;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import gr.teilar.scrooge.Core.Expense;
import gr.teilar.scrooge.Database.ExpenseDb;
import gr.teilar.scrooge.Fragments.AnalyseDatesFragment;
import gr.teilar.scrooge.Fragments.AnalysedListFragment;

public class AnalyseExpensesActivity extends AppCompatActivity implements AnalyseDatesFragment.OnAnalyseButtonListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse_expenses);

        AnalyseDatesFragment analyseDatesFragment = new AnalyseDatesFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.setDatesFragment, analyseDatesFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void OnAnalyse(long from, long to) {

        AnalysedListFragment analysedListFragment = new AnalysedListFragment();

        Bundle bundle = new Bundle();
        bundle.putLong("From", from);
        bundle.putLong("To", to);
        analysedListFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.seeAnalysisFragment, analysedListFragment);
        fragmentTransaction.commit();

    }
}
