package managers;


/**
 * This class should be inherited by everyone entity
 * @author oleh
 * @param <T> type of keys which uses with concrete entity
 */
public abstract class AbstractEntity<T> {

    /**
     * Key of concrete entity object
     */
    private T id;

    /**
     * @return key
     */
    public T getId() {
        return id;
    }

    /**
     * @param id key
     */
    protected void setId(T id) {
        this.id = id;
    }


}
