package tableClasses;

/**
 * represent the comment table
 * Created by oleh on 25.11.14.
 */
public class Comment {
    private String branchOfForum;
    private String dateOfChange;
    private String timeOfChange;
    private String textOfComment;

    public Comment(String branchOfForum, String dateOfChange, String timeOfChange, String textOfComment) {
        this.branchOfForum = branchOfForum;
        this.dateOfChange = dateOfChange;
        this.timeOfChange = timeOfChange;
        this.textOfComment = textOfComment;
    }

    /**
     * get the String value of branchOfForum
     * @return branchOfForum
     */
    public String getBranchOfForum() {
        return branchOfForum;
    }

    /**
     * set the String value for field branchOfForum
     * @param branchOfForum to add
     */
    public void setBranchOfForum(String branchOfForum) {
        this.branchOfForum = branchOfForum;
    }

    /**
     * get the String value of dateOfChange
     * @return dateOfChange
     */
    public String getDateOfChange() {
        return dateOfChange;
    }

    /**
     * set the String value of dateOfChange
     * @param dateOfChange
     */
    public void setDateOfChange(String dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public String getTimeOfChange() {
        return timeOfChange;
    }

    public void setTimeOfChange(String timeOfChange) {
        this.timeOfChange = timeOfChange;
    }

    public String getTextOfComment() {
        return textOfComment;
    }

    public void setTextOfComment(String textOfComment) {
        this.textOfComment = textOfComment;
    }
}
