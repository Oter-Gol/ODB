package access;

import managers.*;

/**
 * Factory for data base
 * Created by oter on 26.11.14.
 */

public class DAOFactory {

    private static Connector connection = new Connector();

    private static CategoryDAO categoryDAO;
    private static CommentDAO commentDAO;
    private static InvestmentDAO investmentDAO;
    private static MessageDAO messageDAO;
    private static ObjectDAO objectDAO;
    private static OperationDAO operationDAO;
    private static ProfileDAO profileDAO;

    /**
     * Getter for CategoryDAO
     * @return CategoryDAO
     */
    public static CategoryDAO getCategoryDAO() {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO(connection.getConnection());
        }
        return categoryDAO;
    }

    /**
     * Getter for CommentDAO
     * @return CommentDAO
     */
    public static CommentDAO getCommentDAO() {
        if (commentDAO == null) {
            commentDAO = new CommentDAO(connection.getConnection());
        }
        return commentDAO;
    }

    /**
     * Getter for InvestmentDAO
     * @return InvestmentDAO
     */
    public static InvestmentDAO getInvestmentDAO() {
        if (investmentDAO == null) {
            investmentDAO = new InvestmentDAO(connection.getConnection(), objectDAO, profileDAO);
        }
        return investmentDAO;
    }

    /**
     * Getter for MessageDAO
     * @return MessageDAO
     */
    public static MessageDAO getMessageDAO() {
        if (messageDAO == null) {
            messageDAO = new MessageDAO(connection.getConnection(), profileDAO, profileDAO);
        }
        return messageDAO;
    }

    /**
     * Getter for ObjectDAO
     * @return ObjectDAO
     */
    public static ObjectDAO getObjectDAO() {
        if (objectDAO == null) {
            objectDAO = new ObjectDAO(connection.getConnection());
        }
        return objectDAO;
    }

    /**
     * Getter for OperationDAO
     * @return OperationDAO
     */
    public static OperationDAO getOperationDAO() {
        if (operationDAO == null) {
            operationDAO = new OperationDAO(connection.getConnection(), profileDAO);
        }
        return operationDAO;
    }

    /**
     * Getter for ProfileDAO
     * @return ProfileDAO
     */
    public static ProfileDAO getProfileDAO() {
        if (profileDAO == null) {
            profileDAO = new ProfileDAO(connection.getConnection(), categoryDAO);
        }
        return profileDAO;
    }
}
