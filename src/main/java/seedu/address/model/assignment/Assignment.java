package seedu.address.model.assignment;

public class Assignment {
    public final String assignmentName;
    public final float score;

    public Assignment(String assignmentName, float score) {
        this.assignmentName = assignmentName;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Assignment:" + assignmentName + " " + score;
    }
}
