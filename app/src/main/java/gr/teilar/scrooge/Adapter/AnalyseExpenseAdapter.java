package gr.teilar.scrooge.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import gr.teilar.scrooge.Core.Expense;
import gr.teilar.scrooge.R;

/**
 * Created by Mitsos on 01/01/17.
 */
//Custom ArrayAdapter που δέχεται σαν είσοδο λίστα απο έξοδα και τα εμφανίζει σύμφωνα με το layout
//που έχουμε ορίσει. Κάνουμε υλοοποίηση της getView που ειναι η συναρτηση που καλείεται απο κάθε arrayAdapter
public class AnalyseExpenseAdapter extends ArrayAdapter<Expense> {

    public AnalyseExpenseAdapter (Context context, List<Expense> analysedExpenses) {
        super(context,0,analysedExpenses);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Expense currentExpense = getItem(position);

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.analyse_list_item, parent, false);
        }

        TextView categoryTextView = (TextView) listItemView.findViewById(R.id.categoryNameTextView);

        categoryTextView.setText(currentExpense.getExpenseCategory().getCategoryName());

        TextView amountTextView = (TextView) listItemView.findViewById(R.id.amountfrag);

        amountTextView.setText(Float.toString(currentExpense.getExpenseAmount()));



        return listItemView;
    }
}
