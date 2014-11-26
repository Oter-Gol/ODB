package tableClasses;

import managers.AbstractEntity;

/**
 * Created by oleh on 25.11.14.
 */
public class Investment extends AbstractEntity<Integer>{
    private Profile profile;
    private OObject oobject;
    private String contractNumber;

    public Investment(Profile profile, OObject oobject, String contractNumber) {
        this.profile = profile;
        this.oobject = oobject;
        this.contractNumber = contractNumber;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public OObject getOObject() {
        return oobject;
    }

    public void setOObject(OObject oobject) {
        this.oobject = oobject;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
}
