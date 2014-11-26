package tableClasses;

/**
 * Created by oleh on 25.11.14.
 */
public class Operation {
    private Profile profile;
    private String typeOfOperation;
    private double sum;
    private String dateOfOperation;

    public Operation(Profile profile, String typeOfOperation, double sum, String dateOfOperation) {
        this.profile = profile;
        this.typeOfOperation = typeOfOperation;
        this.sum = sum;
        this.dateOfOperation = dateOfOperation;
    }

    public String getDateOfOperation() {
        return dateOfOperation;
    }

    public void setDateOfOperation(String dateOfOperation) {
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
