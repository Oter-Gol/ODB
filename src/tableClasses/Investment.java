package tableClasses;

import managers.AbstractEntity;

/**
 * Created by oleh on 25.11.14.
 */
public class Investment extends AbstractEntity<Integer>{
    private Profile profile;
    private OObject oobject;
    private String contractNumber;

    /**
     * constructor
     *
     * @param profile for which investment belongs to
     * @param oobject which object
     * @param contractNumber number of contract
     */
    public Investment(Profile profile, OObject oobject, String contractNumber) {
        this.profile = profile;
        this.oobject = oobject;
        this.contractNumber = contractNumber;
    }

    /**
     * get the Profile object
     * @return profile field
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * set profile to the field
     * @param profile which field to set
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * getter for oobject
     * @return oobject
     */
    public OObject getOObject() {
        return oobject;
    }

    /**
     * setter for oobject
     * @param oobject which oobject to set
     */
    public void setOObject(OObject oobject) {
        this.oobject = oobject;
    }

    /**
     * getter for contract number
     * @return contractNumber
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * setter for contract number
     * @param contractNumber which to set
     */
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
}
