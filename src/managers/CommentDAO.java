package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.СacheDAO;
import tableClasses.Comment;

import java.sql.*;
import java.util.HashSet;

/**
 * implements pattern DAO to Comment Class
 * Created by oleh on 26.11.14.
 */
public class CommentDAO extends DAO<Comment> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private СacheDAO<Integer, Comment> cash;

    /**
     * Constructor for comment
     * @param connection is a pointer for our connection
     */
    public CommentDAO(Connection connection ) {
        super();
        this.connection = connection;
        try {
            this.statement = connection.createStatement();
            this.create = connection.prepareStatement("INSERT INTO comment"
                    + "(branchOfForum, dateOfChange, timeOfChange, textOfComment)"
                    + "VALUES(?,?,?,?)");
            this.update = connection.prepareStatement("UPDATE comment SET "
                    + "branchOfForum = ?, dateOfChange = ?, timeOfChange = ?, textOfComment = ? "
                    + "WHERE id = ?");
            this.delete = connection.prepareStatement("DELETE FROM comment WHERE id = ?");
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
    public HashSet<Comment> get(Condition<Comment> condition)
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
    public Comment create(Comment object) throws SQLException {
        create.setString(2, object.getBranchOfForum());
        create.setString(3, object.getDateOfChange());
        create.setString(4, object.getTextOfComment());
        create.setString(5, object.getTextOfComment());
        if (create.executeUpdate() != 1) {
            throw new IllegalStateException();
        }
        ResultSet rs = statement.executeQuery("SELECT Max(id) FROM comment");
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
    public Comment update(Comment object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        update.setString(2, object.getBranchOfForum());
        update.setString(3, object.getDateOfChange() );
        update.setString(4, object.getTextOfComment() );
        update.setString(5, object.getTextOfComment());
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
    public boolean delete(Comment object) throws SQLException {
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
    public Comment getById(Object id) throws SQLException {
        return cash.getById(id);
    }

    /**
     * get the elements from the data base
     *
     * @return HashSet of the certain class
     * @throws SQLException
     */
    @Override
    protected HashSet<Comment> getFromDatabase() throws SQLException {
        HashSet<Comment> comments = new HashSet<Comment>();
        ResultSet rs = statement.executeQuery("SELECT * FROM comment");
        while (rs.next()) {
            Comment d = new Comment(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            d.setId(rs.getInt(1));
            comments.add(d);
        }
        return comments;
    }
}
