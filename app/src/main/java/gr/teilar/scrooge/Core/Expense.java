package gr.teilar.scrooge.Core;

import java.util.Date;

/**
 * Created by Mitsos on 18/12/16.
 */

public class Expense {
    private long expenseId;
    private Date expenseDate;
    private String expenseDescription;
    private float expenseAmount;

    private Location expenseLocation;
    private Category expenseCategory;

    public Long getExpenseDate() {
        return  expenseDate.getTime();
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public float getExpenseAmount() {
        return expenseAmount;
    }

    public Location getExpenseLocation() {
        return expenseLocation;
    }

    public Category getExpenseCategory() {
        return expenseCategory;
    }
}
