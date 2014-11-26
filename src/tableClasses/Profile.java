package tableClasses;

import managers.AbstractEntity;

/**
 * represent profile table in the database
 * Created by oleh on 25.11.14.
 */
public class Profile extends AbstractEntity<Integer>{
    private String login;
    private String password;
    private Category category;

    /**
     * constructor
     *
     * @param login of the user
     * @param password of the user
     * @param category investor or contact-manager
     */
    public Profile(String login, String password, Category category) {
        this.login = login;
        this.password = password;
        this.category = category;
    }

    /**
     * getter for login
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * setter for login
     * @param login which to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * getter for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for password
     * @param password which to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter for category
     * @return category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * setter for category
     * @param category which to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }
}
