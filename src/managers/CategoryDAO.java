package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.СacheDAO;
import tableClasses.Category;

import java.sql.*;
import java.util.HashSet;

/**
 * implements pattern DAO to Category Class
 * Created by oleh on 26.11.14.
 */
public class CategoryDAO extends DAO<Category> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private СacheDAO<Integer, Category> cash;

    /**
     * Constructor for category
     * @param connection is a pointer for our connection
     */
    public CategoryDAO(Connection connection ) {
        super();
        this.connection = connection;
        try {
            this.statement = connection.createStatement();
            this.create = connection.prepareStatement("INSERT INTO category"
                    + "(nameOfCategory)"
                    + "VALUES(?)");
            this.update = connection.prepareStatement("UPDATE category SET "
                    + "nameOfCategory = ? "
                    + "WHERE id = ?");
            this.delete = connection.prepareStatement("DELETE FROM category WHERE id = ?");
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
    public HashSet<Category> get(Condition<Category> condition)
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
    public Category create(Category object) throws SQLException {
        create.setString(2, object.getNameOfCategory());
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
    public Category update(Category object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        update.setString(2, object.getNameOfCategory());
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
    public boolean delete(Category object) throws SQLException {
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
    public Category getById(Object id) throws SQLException {
        return cash.getById(id);
    }

    /**
     * get the elements from the data base
     *
     * @return HashSet of the certain class
     * @throws SQLException
     */
    @Override
    protected HashSet<Category> getFromDatabase() throws SQLException {
        HashSet<Category> categories = new HashSet<Category>();
        ResultSet rs = statement.executeQuery("SELECT * FROM category");
        while (rs.next()) {
            Category d = new Category(rs.getString(2));
            d.setId(rs.getInt(1));
            categories.add(d);
        }
        return categories;
    }
}
