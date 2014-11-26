package tableClasses;

import managers.AbstractEntity;

/**
 * Created by oleh on 25.11.14.
 */
public class Profile extends AbstractEntity<Integer>{
    private String login;
    private String password;
    private Category category;

    public Profile(String login, String password, Category category) {
        this.login = login;
        this.password = password;
        this.category = category;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
