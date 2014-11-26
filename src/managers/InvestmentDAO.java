package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.СacheDAO;
import tableClasses.Investment;

import java.sql.*;
import java.util.HashSet;

/**
 * implements pattern DAO to Investment Class
 * Created by oleh on 26.11.14.
 */
public class InvestmentDAO extends DAO<Investment> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private ObjectDAO oObjectDAO;
    private ProfileDAO profileDAO;
    private СacheDAO<Integer, Investment> cash;

    /**
     * Constructor for investment
     * @param connection is a pointer for our connection
     */
    public InvestmentDAO(Connection connection, ObjectDAO objectDAO, ProfileDAO profileDAO ) {
        super();
        this.connection = connection;
        try {
            this.statement = connection.createStatement();
            this.create = connection.prepareStatement("INSERT INTO investment"
                    + "(contractNumber, oobject, profile)"
                    + "VALUES(?,?,?)");
            this.update = connection.prepareStatement("UPDATE investment SET "
                    + "contractNumber = ?, oobject = ?, profile = ?"
                    + "WHERE id = ?");
            this.delete = connection.prepareStatement("DELETE FROM investment WHERE id = ?");
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
    public HashSet<Investment> get(Condition<Investment> condition)
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
    public Investment create(Investment object) throws SQLException {
        create.setString(2, object.getContractNumber());
        create.setInt(3, object.getOObject().getId());
        create.setInt(4, object.getProfile().getId());
        if (create.executeUpdate() != 1) {
            throw new IllegalStateException();
        }
        ResultSet rs = statement.executeQuery("SELECT Max(id) FROM investment");
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
    public Investment update(Investment object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        update.setString(2, object.getContractNumber() );
        update.setInt(3, object.getOObject().getId());
        update.setInt(4, object.getProfile().getId());
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
    public boolean delete(Investment object) throws SQLException {
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
    public Investment getById(Object id) throws SQLException {
        return cash.getById(id);
    }

    /**
     * get the elements from the data base
     *
     * @return HashSet of the certain class
     * @throws SQLException
     */
    @Override
    protected HashSet<Investment> getFromDatabase() throws SQLException {
        HashSet<Investment> investments = new HashSet<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM investment");
        while (rs.next()) {
            Investment d = new Investment(profileDAO.getById(rs.getInt(4)), oObjectDAO.getById(rs.getInt(3)),  rs.getString(2));
            d.setId(rs.getInt(1));
            investments.add(d);
        }
        return investments;
    }
}
