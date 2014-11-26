package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.CashDAO;
import tableClasses.Operation;
import tableClasses.Profile;

import java.sql.*;
import java.util.HashSet;

/**
 * Created by OTER on 11/26/2014.
 */
public class ProfileDAO extends DAO<Profile> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private CashDAO<Integer, Profile> cash;

    private CategoryDAO  categoryDAO;

    /**
     * Constructor for object
     * @param connection is a pointer to connection to DB
     *
     * @param connection
     * @param categoryDAO
     */
    public ProfileDAO(Connection connection, CategoryDAO categoryDAO) {
        super();

        this.categoryDAO = categoryDAO;
        //this.profileDAO = profileDAO;
        this.connection = connection;
        try {
            this.statement = connection.createStatement();
            this.create = connection.prepareStatement("INSERT INTO profile"
                    + "(login,password,category)"
                    + "VALUES(?,?,?)");
            this.update = connection.prepareStatement("UPDATE profile SET "
                    + "login = ?,password = ?,category = ?"
                    + "WHERE id = ?");
            this.delete = connection.prepareStatement("DELETE FROM profile WHERE id = ?");
            //load into cash
            this.cash = new CashDAO<>(getFromDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashSet<Profile> get(Condition<Profile> condition)
            throws SQLException {
        return cash.get(condition);
    }

    @Override
    public Profile create(Profile object) throws SQLException {
        create.setInt(1, object.getId());
        create.setString(2, object.getLogin());
        create.setString(3, object.getPassword());
        create.setInt(4, object.getCategory().getId());

        if (create.executeUpdate() != 1) {
            throw new IllegalStateException();
        }
        ResultSet rs = statement.executeQuery("SELECT Max(id) FROM profile");
        rs.next();
        object.setId(rs.getInt(1));
        //create in cash
        cash.create(object);
        return object;
    }

    @Override
    public Profile update(Profile object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        update.setInt(1, object.getId());
        update.setString(2, object.getLogin());
        update.setString(3, object.getPassword());
        update.setInt(4, object.getCategory().getId());
        if (update.executeUpdate() != 1)
            throw new IllegalStateException();
        //update in cash
        cash.update(object);
        return object;
    }

    @Override
    public boolean delete(Profile object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        delete.setInt(1, object.getId());
        if (delete.executeUpdate() != 1)
            throw new IllegalStateException();
        //delete from cash
        return cash.delete(object);
    }

    @Override
    public Profile getById(Object id) throws SQLException {
        return cash.getById(id);
    }

    @Override
    protected HashSet<Profile> getFromDatabase() throws SQLException {
        HashSet<Profile> objects = new HashSet<Profile>();
        ResultSet rs = statement.executeQuery("SELECT * FROM object");
        while (rs.next()) {
            Profile d = new Profile( rs.getString(2),rs.getString(3), categoryDAO.getById( rs.getInt(4) ) );
            d.setId(rs.getInt(1));
            objects.add(d);
        }
        return objects;
    }
}