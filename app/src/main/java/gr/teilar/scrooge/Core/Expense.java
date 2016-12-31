package gr.teilar.scrooge.Core;

import java.io.Serializable;
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

    public Expense(long expenseId, Date expenseDate, String expenseDescription, float expenseAmount, int locationId, int categoryId) {
        this.expenseExpenseLocation = new ExpenseLocation();
        this.expenseCategory = new Category();

        this.expenseId = expenseId;
        this.expenseDate = expenseDate;
        this.expenseDescription = expenseDescription;
        this.expenseAmount = expenseAmount;
        this.expenseExpenseLocation.setLocationId(locationId);
        this.expenseCategory.setCategoryId(categoryId);
    }

    public long getExpenseId() {
        return expenseId;
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

    public void setExpenseCategory(Category expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public void setExpenseExpenseLocation(ExpenseLocation expenseExpenseLocation) {
        this.expenseExpenseLocation = expenseExpenseLocation;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", expenseDate=" + expenseDate +
                ", expenseDescription='" + expenseDescription + '\'' +
                ", expenseAmount=" + expenseAmount +
                ", expenseExpenseLocation=" + expenseExpenseLocation.getLocationId() +
                ", expenseCategory=" + expenseCategory.getCategoryId() +
                '}';
    }
}
