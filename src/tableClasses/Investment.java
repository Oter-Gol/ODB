package tableClasses;

import managers.AbstractEntity;

/**
 * Created by oleh on 25.11.14.
 */
public class Investment extends AbstractEntity<Integer>{
    private Profile profile;
    private Object object;
    private String contractNumber;

    public Investment(Profile profile, Object object, String contractNumber) {
        this.profile = profile;
        this.object = object;
        this.contractNumber = contractNumber;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
}
