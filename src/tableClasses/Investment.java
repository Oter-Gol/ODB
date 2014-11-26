package tableClasses;

/**
 * Created by oleh on 25.11.14.
 */
public class Investment {
    private Profile profile;
    private OObject OObject;
    private String contractNumber;

    public Investment(Profile profile, OObject OObject, String contractNumber) {
        this.profile = profile;
        this.OObject = OObject;
        this.contractNumber = contractNumber;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public OObject getOObject() {
        return OObject;
    }

    public void setOObject(OObject OObject) {
        this.OObject = OObject;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
}
