package seedu.address.model.assignment;

/**
 * Represents an Assignment in the address book.
 */
public class Assignment {
    private String assignmentName;
    private float score;

    public Assignment() {
    }

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
        return "Assignment: " + assignmentName + " " + String.format("%.2f", score);
    }

    public float getScore() {
        return score;
    }

    public String getAssignmentName() {
        return assignmentName;

    }

    public String toCsvAdapted() {
        return assignmentName + " | " + score;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Assignment other) {
            return this.assignmentName.equals(other.assignmentName);
        }
        return false;
    }

}
