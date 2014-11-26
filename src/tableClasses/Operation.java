package tableClasses;

import managers.AbstractEntity;

import java.sql.Date;

/**
 * Created by oleh on 25.11.14.
 */
public class Operation extends AbstractEntity<Integer> {
    private Profile profile;
    private String typeOfOperation;
    private double sum;
    private Date dateOfOperation;

    public Operation(String typeOfOperation, double sum, Date dateOfOperation, Profile profile ) {
        this.profile = profile;
        this.typeOfOperation = typeOfOperation;
        this.sum = sum;
        this.dateOfOperation = dateOfOperation;
    }

    public Date getDateOfOperation() {
        return dateOfOperation;
    }

    public void setDateOfOperation(Date dateOfOperation) {
        this.dateOfOperation = dateOfOperation;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getTypeOfOperation() {
        return typeOfOperation;
    }

    public void setTypeOfOperation(String typeOfOperation) {
        this.typeOfOperation = typeOfOperation;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
