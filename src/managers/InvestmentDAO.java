package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.CashDAO;
import tableClasses.Comment;
import tableClasses.Investment;

import java.sql.*;
import java.util.HashSet;

/**
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
    private CashDAO<Integer, Investment> cash;

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
            this.cash = new CashDAO<>(getFromDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashSet<Investment> get(Condition<Investment> condition)
            throws SQLException {
        return cash.get(condition);
    }

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

    @Override
    public Investment getById(Object id) throws SQLException {
        return cash.getById(id);
    }

    @Override
    protected HashSet<Investment> getFromDatabase() throws SQLException {
        HashSet<Investment> investments = new HashSet<Investment>();
        ResultSet rs = statement.executeQuery("SELECT * FROM investment");
        while (rs.next()) {
            Investment d = new Investment(rs.getString(2), oObjectDAO.getById(rs.getInt(3)), profileDAO.getById(rs.getInt(4)) );
            d.setId(rs.getInt(1));
            investments.add(d);
        }
        return investments;
    }
}
