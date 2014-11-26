package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.CashDAO;
import tableClasses.OObject;

import java.sql.*;
import java.util.HashSet;

/**
 * Created by oleh on 26.11.14.
 */
public class ObjectDAO extends DAO<OObject> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private CashDAO<Integer, OObject> cash;

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
            this.cash = new CashDAO<>(getFromDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashSet<OObject> get(Condition<OObject> condition)
            throws SQLException {
        return cash.get(condition);
    }

    @Override
    public OObject create(OObject object) throws SQLException {
        create.setString(1, object.getName());
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

    @Override
    public OObject update(OObject object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        update.setString(1, object.getName());
        update.setInt(5, object.getId());
        if (update.executeUpdate() != 1)
            throw new IllegalStateException();
        //update in cash
        cash.update(object);
        return object;
    }

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

    @Override
    public OObject getById(Object id) throws SQLException {
        return cash.getById(id);
    }

    @Override
    protected HashSet<OObject> getFromDatabase() throws SQLException {
        HashSet<OObject> objects = new HashSet<OObject>();
        ResultSet rs = statement.executeQuery("SELECT * FROM object");
        while (rs.next()) {
            OObject d = new OObject(rs.getString(2));
            d.setId(rs.getInt(1));
            objects.add(d);
        }
        return objects;
    }
}
