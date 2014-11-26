package tableClasses;

import managers.AbstractEntity;

/**
 * Created by oleh on 25.11.14.
 */
public class OObject extends AbstractEntity<Integer> {
    private String name;

    public OObject(String name) {
        super();
        setId(-1);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
