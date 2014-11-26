package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.CashDAO;
import tableClasses.Category;

import java.sql.*;
import java.util.HashSet;

/**
 * Created by oleh on 26.11.14.
 */
public class CategoryDAO extends DAO<Category> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private CashDAO<Integer, Category> cash;

    public CategoryDAO(Connection connection ) {
        super();
        this.connection = connection;
        try {
            this.statement = connection.createStatement();
            this.create = connection.prepareStatement("INSERT INTO category"
                    + "(nameOfCategory)"
                    + "VALUES(?)");
            this.update = connection.prepareStatement("UPDATE categoty SET "
                    + "nameOfCategory = ? "
                    + "WHERE id = ?");
            this.delete = connection.prepareStatement("DELETE FROM category WHERE id = ?");
            //load into cash
            this.cash = new CashDAO<>(getFromDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashSet<Category> get(Condition<Category> condition)
            throws SQLException {
        return cash.get(condition);
    }

    @Override
    public Category create(Category object) throws SQLException {
        create.setString(1, object.getNameOfCategory());
        if (create.executeUpdate() != 1) {
            throw new IllegalStateException();
        }
        ResultSet rs = statement.executeQuery("SELECT Max(id) FROM category");
        rs.next();
        object.setId(rs.getInt(1));
        //create in cash
        cash.create(object);
        return object;
    }

    @Override
    public Category update(Category object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        update.setString(1, object.getNameOfCategory());
        update.setInt(5, object.getId());
        if (update.executeUpdate() != 1)
            throw new IllegalStateException();
        //update in cash
        cash.update(object);
        return object;
    }

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

    @Override
    public Category getById(Object id) throws SQLException {
        return cash.getById(id);
    }

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
