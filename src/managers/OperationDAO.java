package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.CashDAO;
import tableClasses.Operation;

import java.sql.*;
import java.util.HashSet;

/**
 * Created by OTER on 11/26/2014.
 */
public class OperationDAO extends DAO<Operation> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private CashDAO<Integer, Operation> cash;

    private ProfileDAO profileDAO;

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
            this.cash = new CashDAO<>(getFromDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashSet<Operation> get(Condition<Operation> condition)
            throws SQLException {
        return cash.get(condition);
    }

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

    @Override
    public Operation getById(Object id) throws SQLException {
        return cash.getById(id);
    }

    @Override
    protected HashSet<Operation> getFromDatabase() throws SQLException {
        HashSet<Operation> objects = new HashSet<Operation>();
        ResultSet rs = statement.executeQuery("SELECT * FROM object");
        while (rs.next()) {
            Operation d = new Operation(rs.getString(2), rs.getInt(3), rs.getDate(4),  profileDAO.getById( rs.getInt(5)) );
            d.setId(rs.getInt(1));
            objects.add(d);
        }
        return objects;
    }
}