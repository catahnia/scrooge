package gr.teilar.scrooge.Core;

/**
 * Created by Mitsos on 18/12/16.
 */

public class Category {
    private long categoryId;
    private String categoryName;
    private String categoryDescription;

    public Category() {
    }

    public Category(String categoryName, String categoryDescription) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public Category(long id, String categoryName, String categoryDescription) {
        this.categoryId=id;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public long getCategoryId() {
        return categoryId;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    @Override
    public String toString() {
        return this.categoryName;
    }
}
