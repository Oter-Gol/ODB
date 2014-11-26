package tableClasses;

import managers.AbstractEntity;

import java.sql.Date;

/**
 * represent operation table in the database
 * Created by oleh on 25.11.14.
 */
public class Operation extends AbstractEntity<Integer> {
    private Profile profile;
    private String typeOfOperation;
    private double sum;
    private Date dateOfOperation;

    /**
     * constructor
     *
     * @param typeOfOperation name of the type of the operation
     * @param sum sum of money for operation
     * @param dateOfOperation date the operation was completed
     * @param profile to which the operation belongs to
     */
    public Operation(String typeOfOperation, double sum, Date dateOfOperation, Profile profile ) {
        this.profile = profile;
        this.typeOfOperation = typeOfOperation;
        this.sum = sum;
        this.dateOfOperation = dateOfOperation;
    }

    /**
     * getter for dateOfOperation
     * @return value of field dateOfOperation
     */
    public Date getDateOfOperation() {
        return dateOfOperation;
    }

    /**
     * setter for dateOfOperation
     * @param dateOfOperation which to set
     */
    public void setDateOfOperation(Date dateOfOperation) {
        this.dateOfOperation = dateOfOperation;
    }

    /**
     * getter for profile
     * @return profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * setter for profile
     * @param profile which to set
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * getter for type of the operation
     * @return typeOfOperation
     */
    public String getTypeOfOperation() {
        return typeOfOperation;
    }

    /**
     * setter for type of the operation
     * @param typeOfOperation which to set
     */
    public void setTypeOfOperation(String typeOfOperation) {
        this.typeOfOperation = typeOfOperation;
    }

    /**
     * getter for sum
     * @return sum
     */
    public double getSum() {
        return sum;
    }

    /**
     * setter for field sum
     * @param sum which to set
     */
    public void setSum(double sum) {
        this.sum = sum;
    }
}
