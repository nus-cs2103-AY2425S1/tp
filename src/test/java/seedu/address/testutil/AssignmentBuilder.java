package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Student;

/**
 * A utility class to help build Assignment objects for testing purposes.
 */
public class AssignmentBuilder {

    public static final Student DEFAULT_STUDENT = new StudentBuilder().build();
    public static final String DEFAULT_ASSIGNMENT_NAME = "Math Homework";
    public static final int DEFAULT_MAX_SCORE = 100;
    public static final int DEFAULT_SCORE = 0;
    public static final boolean DEFAULT_SUBMISSION_STATUS = false;

    private AssignmentName assignmentName;
    private int maxScore;
    private int score;
    private boolean hasSubmitted;

    /**
     * Creates an AssignmentBuilder with default values for the Assignment.
     */
    public AssignmentBuilder() {
        assignmentName = new AssignmentName(DEFAULT_ASSIGNMENT_NAME);
        maxScore = DEFAULT_MAX_SCORE;
        score = DEFAULT_SCORE;
        hasSubmitted = DEFAULT_SUBMISSION_STATUS;
    }

    /**
     * Creates an AssignmentBuilder with values copied from the specified Assignment.
     *
     * @param assignmentToCopy the Assignment to copy values from
     */
    public AssignmentBuilder(Assignment assignmentToCopy) {
        assignmentName = new AssignmentName(assignmentToCopy.getName());
        maxScore = assignmentToCopy.getMaxScore();
        score = assignmentToCopy.getScore();
        hasSubmitted = assignmentToCopy.getHasSubmitted();
    }

    /**
     * Sets the {@code AssignmentName} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withAssignmentName(String assignmentName) {
        this.assignmentName = new AssignmentName(assignmentName);
        return this;
    }

    /**
     * Sets the {@code maxScore} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withMaxScore(int maxScore) {
        this.maxScore = maxScore;
        return this;
    }

    /**
     * Sets the {@code score} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withScore(int score) {
        this.score = score;
        return this;
    }

    /**
     * Sets the {@code hasSubmitted} status of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withHasSubmitted(boolean hasSubmitted) {
        this.hasSubmitted = hasSubmitted;
        return this;
    }

    /**
     * Builds and returns an {@code Assignment}.
     */
    public Assignment build() {
        Assignment assignment = new Assignment(assignmentName, maxScore);
        assignment.setScore(this.score);
        assignment.setHasSubmitted(this.hasSubmitted);
        return assignment;
    }
}
