package tableClasses;

import managers.AbstractEntity;

import java.sql.Date;

/**
 * Created by oleh on 25.11.14.
 */
public class Message extends AbstractEntity<Integer> {
    private String themeOfMessage;
    private Date dateOfSending;
    private String textOfMessage;
    private Profile sender;
    private Profile recipient;

    public Message(String themeOfMessage, Date dateOfSending, String textOfMessage, Profile sender, Profile recipient) {
        this.themeOfMessage = themeOfMessage;
        this.dateOfSending = dateOfSending;
        this.textOfMessage = textOfMessage;
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getThemeOfMessage() {
        return themeOfMessage;
    }

    public void setThemeOfMessage(String themeOfMessage) {
        this.themeOfMessage = themeOfMessage;
    }

    public Date getDateOfSending() {
        return dateOfSending;
    }

    public void setDateOfSending(Date dateOfSending) {
        this.dateOfSending = dateOfSending;
    }

    public String getTextOfMesssage() {
        return textOfMessage;
    }

    public void setTextOfMesssage(String textOfMesssage) {
        this.textOfMessage = textOfMesssage;
    }

    public Profile getSender() {
        return sender;
    }

    public void setSender(Profile sender) {
        this.sender = sender;
    }

    public Profile getRecipient() {
        return recipient;
    }

    public void setRecipient(Profile recipient) {
        this.recipient = recipient;
    }
}
