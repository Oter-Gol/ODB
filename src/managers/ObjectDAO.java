package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.СacheDAO;
import tableClasses.OObject;

import java.sql.*;
import java.util.HashSet;

/**
 * implements pattern DAO to OObject Class
 * Created by oleh on 26.11.14.
 */
public class ObjectDAO extends DAO<OObject> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private СacheDAO<Integer, OObject> cash;

    /**
     * Constructor for object
     * @param connection is a pointer to connection to DB
     */
    public ObjectDAO(Connection connection) {
        super();
        this.connection = connection;
        try {
            this.statement = connection.createStatement();
            this.create = connection.prepareStatement("INSERT INTO object"
                    + "(name)"
                    + "VALUES(?)");
            this.update = connection.prepareStatement("UPDATE object SET "
                    + "name = ?"
                    + "WHERE id = ?");
            this.delete = connection.prepareStatement("DELETE FROM object WHERE id = ?");
            //load into cash
            this.cash = new СacheDAO<>(getFromDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getting method for already read categories
     * @param condition
     * @return HashSet of certain class
     * @throws SQLException if happened something wrong
     */
    @Override
    public HashSet<OObject> get(Condition<OObject> condition)
            throws SQLException {
        return cash.get(condition);
    }

    /**
     * create a line with certain name
     * @param object which to add to the table
     * @return improved object
     * @throws SQLException
     */
    @Override
    public OObject create(OObject object) throws SQLException {
        create.setInt(1, object.getId());
        create.setString(2, object.getName());
        if (create.executeUpdate() != 1) {
            throw new IllegalStateException();
        }
        ResultSet rs = statement.executeQuery("SELECT Max(id) FROM discount");
        rs.next();
        object.setId(rs.getInt(1));
        //create in cash
        cash.create(object);
        return object;
    }

    /**
     * update the certain line in the table
     *
     * @param object what we add to the table instead of what we have
     * @return improved object
     * @throws SQLException
     */
    @Override
    public OObject update(OObject object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        update.setString(2, object.getName());
        update.setInt(1, object.getId());
        if (update.executeUpdate() != 1)
            throw new IllegalStateException();
        //update in cash
        cash.update(object);
        return object;
    }

    /**
     * delete the object from the table
     *
     * @param object what to delete
     * @return true if deleted
     * @throws SQLException
     */
    @Override
    public boolean delete(OObject object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        delete.setInt(1, object.getId());
        if (delete.executeUpdate() != 1)
            throw new IllegalStateException();
        //delete from cash
        return cash.delete(object);
    }

    /**
     * get element by id
     *
     * @param id of the object
     * @return the element due to it id
     * @throws SQLException
     */
    @Override
    public OObject getById(Object id) throws SQLException {
        return cash.getById(id);
    }

    /**
     * get the elements from the data base
     *
     * @return HashSet of the certain class
     * @throws SQLException
     */
    @Override
    protected HashSet<OObject> getFromDatabase() throws SQLException {
        HashSet<OObject> objects = new HashSet<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM object");
        while (rs.next()) {
            OObject d = new OObject(rs.getString(2));
            d.setId(rs.getInt(1));
            objects.add(d);
        }
        return objects;
    }
}
