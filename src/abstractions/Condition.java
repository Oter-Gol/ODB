package abstractions;

/**
 * Created by oleh on 26.11.14.
 */
public interface Condition<T> {
    public boolean satisfyTo(T object);
}