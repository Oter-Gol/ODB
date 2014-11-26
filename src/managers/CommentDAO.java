package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.CashDAO;
import tableClasses.Comment;

import java.sql.*;
import java.util.HashSet;

/**
 * Created by oleh on 26.11.14.
 */
public class CommentDAO extends DAO<Comment> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private CashDAO<Integer, Comment> cash;

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
            this.cash = new CashDAO<>(getFromDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashSet<Comment> get(Condition<Comment> condition)
            throws SQLException {
        return cash.get(condition);
    }

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

    @Override
    public Comment getById(Object id) throws SQLException {
        return cash.getById(id);
    }

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
