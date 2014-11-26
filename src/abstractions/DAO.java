package abstractions;

import java.sql.SQLException;
import java.util.HashSet;

/**
 * Created by oleh on 26.11.14.
 */
public abstract class DAO<T> {

    public abstract HashSet<T> get(Condition<T> condition) throws SQLException;
    public abstract T create(T object) throws SQLException;
    public abstract T update(T object) throws SQLException;
    public abstract boolean delete(T object) throws SQLException;
    public abstract T getById(Object id) throws SQLException;
    protected abstract HashSet<T> getFromDatabase() throws SQLException;

}