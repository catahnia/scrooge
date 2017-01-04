package gr.teilar.scrooge.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gr.teilar.scrooge.Core.Expense;
import gr.teilar.scrooge.R;

/**
 * Created by Mitsos on 30/12/16.
 */

//Custom ArrayAdapter που δέχεται σαν είσοδο λίστα απο έξοδα και τα εμφανίζει σύμφωνα με το layout
//που έχουμε ορίσει. Κάνουμε υλοοποίηση της getView που ειναι η συναρτηση που καλείεται απο κάθε arrayAdapter
public class ExpenseAdapter extends ArrayAdapter<Expense> {

    public ExpenseAdapter (Context context, List<Expense> expenses) {
        super(context,0,expenses);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Expense currentExpense = getItem(position);

        TextView expenseDateTextView = (TextView) listItemView.findViewById(R.id.expenseDate);

        Date expenseDate = new Date(currentExpense.getExpenseDate());
        expenseDateTextView.setText(new SimpleDateFormat("dd/MM/yyyy").format(expenseDate));

        TextView expenseDescriptionTextView = (TextView) listItemView.findViewById(R.id.expenseDescription);

        expenseDescriptionTextView.setText(currentExpense.getExpenseDescription());

        TextView expenseAmountTextView = (TextView) listItemView.findViewById(R.id.expenseAmount);

        expenseAmountTextView.setText(Float.toString(currentExpense.getExpenseAmount()));

        TextView expenseLocationTextView = (TextView) listItemView.findViewById(R.id.expenseLocationName);

        expenseLocationTextView.setText(currentExpense.getExpenseExpenseLocation().getLocationName());

        TextView expenseCategoryTextView = (TextView) listItemView.findViewById(R.id.expenseCategoryName);


        expenseCategoryTextView.setText(currentExpense.getExpenseCategory().getCategoryName());



        return listItemView;
    }
}
