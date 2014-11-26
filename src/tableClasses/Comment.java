package tableClasses;

import managers.AbstractEntity;

/**
 * represent the comment table
 * Created by oleh on 25.11.14.
 */
public class Comment extends AbstractEntity<Integer> {
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

    /**
     * get the time of change of the comment
     * @return timeOfChange
     */
    public String getTimeOfChange() {
        return timeOfChange;
    }

    /**
     * set the time of change of the comment
     * @param timeOfChange which to set
     */
    public void setTimeOfChange(String timeOfChange) {
        this.timeOfChange = timeOfChange;
    }

    /**
     * get the text in the comment
     * @return textOfComment
     */
    public String getTextOfComment() {
        return textOfComment;
    }

    /**
     * set the text of the comment
     * @param textOfComment which to set to the field
     */
    public void setTextOfComment(String textOfComment) {
        this.textOfComment = textOfComment;
    }
}
