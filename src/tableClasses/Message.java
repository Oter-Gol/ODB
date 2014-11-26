package tableClasses;

/**
 * Created by oleh on 25.11.14.
 */
public class Message {
    private String themeOfMessage;
    private String dateOfSending;
    private String textOfMesssage;
    private Profile sender;
    private Profile recipient;

    public String getThemeOfMessage() {
        return themeOfMessage;
    }

    public void setThemeOfMessage(String themeOfMessage) {
        this.themeOfMessage = themeOfMessage;
    }

    public String getDateOfSending() {
        return dateOfSending;
    }

    public void setDateOfSending(String dateOfSending) {
        this.dateOfSending = dateOfSending;
    }

    public String getTextOfMesssage() {
        return textOfMesssage;
    }

    public void setTextOfMesssage(String textOfMesssage) {
        this.textOfMesssage = textOfMesssage;
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
