package tableClasses;

import managers.AbstractEntity;

/**
 * represent the tableClasses.Category table
 * Created by oleh on 25.11.14.
 */
public class Category extends AbstractEntity<Integer> {
    private String nameOfCategory;

    public Category(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }

    /**
     * get name of category
     * @return field nameOfCategory
     */
    public String getNameOfCategory() {
        return nameOfCategory;
    }

    /**
     * set name of category
     * @param nameOfCategory what to set for the field
     */
    public void setNameOfCategory(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }
}
