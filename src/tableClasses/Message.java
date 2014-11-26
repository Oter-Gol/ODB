package tableClasses;

import managers.AbstractEntity;

import java.sql.Date;

/**
 * represent message table in the database
 * Created by oleh on 25.11.14.
 */
public class Message extends AbstractEntity<Integer> {
    private String themeOfMessage;
    private Date dateOfSending;
    private String textOfMessage;
    private Profile sender;
    private Profile recipient;

    /**
     * constructor for Message
     *
     * @param themeOfMessage the theme of the message
     * @param dateOfSending date of sending
     * @param textOfMessage the text of the message
     * @param sender profile of a sender
     * @param recipient profile of a recipient
     */
    public Message(String themeOfMessage, Date dateOfSending, String textOfMessage, Profile sender, Profile recipient) {
        this.themeOfMessage = themeOfMessage;
        this.dateOfSending = dateOfSending;
        this.textOfMessage = textOfMessage;
        this.sender = sender;
        this.recipient = recipient;
    }

    /**
     * getter for theme of the message
     * @return themeOfMessage
     */
    public String getThemeOfMessage() {
        return themeOfMessage;
    }

    /**
     * setter for theme of the message
     * @param themeOfMessage which to set for the field
     */
    public void setThemeOfMessage(String themeOfMessage) {
        this.themeOfMessage = themeOfMessage;
    }

    /**
     * getter for date of Sending of the message
     * @return dateOfSending
     */
    public Date getDateOfSending() {
        return dateOfSending;
    }

    /**
     * setter for date of sending
     * @param dateOfSending which to set for the field
     */
    public void setDateOfSending(Date dateOfSending) {
        this.dateOfSending = dateOfSending;
    }

    /**
     * getter for text of message
     * @return textOfMessage
     */
    public String getTextOfMesssage() {
        return textOfMessage;
    }

    /**
     * setter for text of the message
     * @param textOfMesssage which to set for the field
     */
    public void setTextOfMesssage(String textOfMesssage) {
        this.textOfMessage = textOfMesssage;
    }

    /**
     * getter for Profile sender
     * @return sender
     */
    public Profile getSender() {
        return sender;
    }

    /**
     * setter for Profile field sender
     * @param sender who send a message
     */
    public void setSender(Profile sender) {
        this.sender = sender;
    }

    /**
     *
     * @return
     */
    public Profile getRecipient() {
        return recipient;
    }
    /**
     * setter for Profile field recipient
     * @param recipient who receive a message
     */
    public void setRecipient(Profile recipient) {
        this.recipient = recipient;
    }
}
