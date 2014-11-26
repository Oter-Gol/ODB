package managers;

import abstractions.Condition;
import abstractions.DAO;
import cash.CashDAO;
import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
import tableClasses.Investment;
import tableClasses.Message;

import java.sql.*;
import java.util.HashSet;

/**
 * Created by oleh on 26.11.14.
 */
public class MessageDAO extends DAO<Message> {
    private Connection connection;
    private Statement statement;
    private PreparedStatement update;
    private PreparedStatement create;
    private PreparedStatement delete;

    private ProfileDAO profileDAOsend;
    private ProfileDAO profileDAOreceip;
    private CashDAO<Integer, Message> cash;

    /**
     * Constructor for message
     * @param connection is a pointer to our connection
     * @param profileDAOsend points to ProfileDAO of sender
     * @param profileDAOreceip points to ProfileDAO of recipient
     */
    public MessageDAO(Connection connection, ProfileDAO profileDAOsend, ProfileDAO profileDAOreceip ) {
        super();
        this.connection = connection;
        try {
            this.statement = connection.createStatement();
            this.create = connection.prepareStatement("INSERT INTO message"
                    + "(themeOfMessage, dateOfSending, textOfSending, sender, recipient)"
                    + "VALUES(?,?,?,?,?)");
            this.update = connection.prepareStatement("UPDATE message SET "
                    + "themeOfMessage = ?, dateOfSending = ?, textOfSending = ?, sender = ?, recipient = ?"
                    + "WHERE id = ?");
            this.delete = connection.prepareStatement("DELETE FROM message WHERE id = ?");
            //load into cash
            this.cash = new CashDAO<>(getFromDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashSet<Message> get(Condition<Message> condition)
            throws SQLException {
        return cash.get(condition);
    }

    @Override
    public Message create(Message object) throws SQLException {
        create.setString(2, object.getThemeOfMessage());
        create.setDate(3, object.getDateOfSending());
        create.setString(4, object.getTextOfMesssage());
        create.setInt(5, object.getSender().getId());
        create.setInt(6, object.getRecipient().getId());
        if (create.executeUpdate() != 1) {
            throw new IllegalStateException();
        }
        ResultSet rs = statement.executeQuery("SELECT Max(id) FROM message");
        rs.next();
        object.setId(rs.getInt(1));
        //create in cash
        cash.create(object);
        return object;
    }

    @Override
    public Message update(Message object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        update.setString(2, object.getThemeOfMessage());
        update.setDate(3, object.getDateOfSending());
        update.setString(4, object.getTextOfMesssage());
        update.setInt(5, object.getSender().getId());
        update.setInt(6, object.getRecipient().getId());
        update.setInt(1, object.getId());
        if (update.executeUpdate() != 1)
            throw new IllegalStateException();
        //update in cash
        cash.update(object);
        return object;
    }

    @Override
    public boolean delete(Message object) throws SQLException {
        if (object.getId() == -1)
            throw new IllegalStateException();
        delete.setInt(1, object.getId());
        if (delete.executeUpdate() != 1)
            throw new IllegalStateException();
        //delete from cash
        return cash.delete(object);
    }

    @Override
    public Message getById(Object id) throws SQLException {
        return cash.getById(id);
    }

    @Override
    protected HashSet<Message> getFromDatabase() throws SQLException {
        HashSet<Message> messages = new HashSet<Message>();
        ResultSet rs = statement.executeQuery("SELECT * FROM message");
        while (rs.next()) {
            Message d = new Message(rs.getString(2), rs.getDate(3), rs.getString(4), profileDAOsend.getById(rs.getInt(5)),
                                    profileDAOreceip.getById(rs.getInt(6)));
            d.setId(rs.getInt(1));
            messages.add(d);
        }
        return messages;
    }
}
