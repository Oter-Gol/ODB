package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.СacheDAO;
import tableClasses.Operation;

import java.sql.*;
import java.util.HashSet;

/**
 * implements pattern DAO to Operation Class
 * Created by OTER on 11/26/2014.
 */
public class OperationDAO extends DAO<Operation> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private СacheDAO<Integer, Operation> cash;

    private ProfileDAO profileDAO;

    /**
     * Constructor for operation
     * @param connection is a pointer to connection to DB
     * @param profileDAO points to ProfileDAO of the user
     */
    public OperationDAO(Connection connection, ProfileDAO profileDAO) {
        super();

        this.profileDAO = profileDAO;
        this.connection = connection;
        try {
            this.statement = connection.createStatement();
            this.create = connection.prepareStatement("INSERT INTO operation"
                    + "(typeOfOperation,sum,dateOfOperation,profile)"
                    + "VALUES(?,?,?,?)");
            this.update = connection.prepareStatement("UPDATE operation SET "
                    + "typeOfOperation = ?,sum = ?,dateOfOperation = ?,profile = ?"
                    + "WHERE id = ?");
            this.delete = connection.prepareStatement("DELETE FROM operation WHERE id = ?");
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
    public HashSet<Operation> get(Condition<Operation> condition)
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
    public Operation create(Operation object) throws SQLException {
        create.setString(4, object.getTypeOfOperation());
        create.setDouble(1, object.getSum());
        create.setDate(2, object.getDateOfOperation());
        create.setInt(3, object.getProfile().getId());

        if (create.executeUpdate() != 1) {
            throw new IllegalStateException();
        }
        ResultSet rs = statement.executeQuery("SELECT Max(id) FROM operation");
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
    public Operation update(Operation object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        update.setString(2, object.getTypeOfOperation());
        update.setDouble(3, object.getSum());
        update.setDate(4, object.getDateOfOperation());
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
    public boolean delete(Operation object) throws SQLException {
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
    public Operation getById(Object id) throws SQLException {
        return cash.getById(id);
    }

    /**
     * get the elements from the data base
     *
     * @return HashSet of the certain class
     * @throws SQLException
     */
    @Override
    protected HashSet<Operation> getFromDatabase() throws SQLException {
        HashSet<Operation> objects = new HashSet<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM object");
        while (rs.next()) {
            Operation d = new Operation(rs.getString(2), rs.getInt(3), rs.getDate(4),  profileDAO.getById( rs.getInt(5)) );
            d.setId(rs.getInt(1));
            objects.add(d);
        }
        return objects;
    }
}