package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.СacheDAO;
import tableClasses.Profile;

import java.sql.*;
import java.util.HashSet;

/**
 * implements pattern DAO to Profile Class
 * Created by OTER on 11/26/2014.
 */
public class ProfileDAO extends DAO<Profile> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private СacheDAO<Integer, Profile> cash;

    private CategoryDAO  categoryDAO;

    /**
     * Constructor for object
     * @param connection is a pointer to connection to DB
     *
     * @param connection to which we connect
     * @param categoryDAO refers to the line in the table
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
    public HashSet<Profile> get(Condition<Profile> condition)
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

    /**
     * update the certain line in the table
     *
     * @param object what we add to the table instead of what we have
     * @return improved object
     * @throws SQLException
     */
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

    /**
     * delete the object from the table
     *
     * @param object what to delete
     * @return true if deleted
     * @throws SQLException
     */
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

    /**
     * get element by id
     *
     * @param id of the object
     * @return the element due to it id
     * @throws SQLException
     */
    @Override
    public Profile getById(Object id) throws SQLException {
        return cash.getById(id);
    }

    /**
     * get the elements from the data base
     *
     * @return HashSet of the certain class
     * @throws SQLException
     */
    @Override
    protected HashSet<Profile> getFromDatabase() throws SQLException {
        HashSet<Profile> objects = new HashSet<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM object");
        while (rs.next()) {
            Profile d = new Profile( rs.getString(2),rs.getString(3), categoryDAO.getById( rs.getInt(4) ) );
            d.setId(rs.getInt(1));
            objects.add(d);
        }
        return objects;
    }
}