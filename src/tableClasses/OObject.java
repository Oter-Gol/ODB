package tableClasses;

import managers.AbstractEntity;

/**
 * Created by oleh on 25.11.14.
 */
public class OObject extends AbstractEntity<Integer> {
    private String name;

    /**
     * constructor
     *
     * @param name name of the object
     */
    public OObject(String name) {
        super();
        setId(-1);
        this.name = name;
    }

    /**
     * getter for name
     * @return name of the object
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name
     * @param name which name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
