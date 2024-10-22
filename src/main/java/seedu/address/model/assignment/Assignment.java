package seedu.address.model.assignment;

/**
 * Represents an Assignment in the address book.
 */
public class Assignment {
    public final String assignmentName;
    public final float score;

    /**
     * Constructs a {@code Assignment}.
     *
     * @param assignmentName A valid assignment name.
     * @param score          A score for the assignment.
     */
    public Assignment(String assignmentName, float score) {
        this.assignmentName = assignmentName;
        this.score = score;
    }


    @Override
    public String toString() {
        return "Assignment:" + assignmentName + " " + score;
    }

    public float getScore() {
        return score;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

}
