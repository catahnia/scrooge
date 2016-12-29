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

    private ExpenseLocation expenseExpenseLocation;
    private Category expenseCategory;

    public Expense(Date expenseDate, String expenseDescription, float expenseAmount, ExpenseLocation expenseExpenseLocation, Category expenseCategory) {
        this.expenseDate = expenseDate;
        this.expenseDescription = expenseDescription;
        this.expenseAmount = expenseAmount;
        this.expenseExpenseLocation = expenseExpenseLocation;
        this.expenseCategory = expenseCategory;
    }

    public Long getExpenseDate() {
        return  expenseDate.getTime();
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public float getExpenseAmount() {
        return expenseAmount;
    }

    public ExpenseLocation getExpenseExpenseLocation() {
        return expenseExpenseLocation;
    }

    public Category getExpenseCategory() {
        return expenseCategory;
    }
}
