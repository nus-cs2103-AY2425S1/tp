package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.student.Student;

public class AssignmentBuilder {

    public static final String DEFAULT_ASSIGNMENT_NAME = "Math Homework";
    public static final int DEFAULT_MAX_SCORE = 100;
    public static final int DEFAULT_SCORE = 0;
    public static final boolean DEFAULT_SUBMISSION_STATUS = false;

    private Student student;
    private AssignmentName assignmentName;
    private int maxScore;
    private int score;
    private boolean hasSubmitted;

    public AssignmentBuilder() {
        student = new StudentBuilder().build();
        assignmentName = new AssignmentName(DEFAULT_ASSIGNMENT_NAME);
        maxScore = DEFAULT_MAX_SCORE;
        score = DEFAULT_SCORE;
        hasSubmitted = DEFAULT_SUBMISSION_STATUS;
    }

    public AssignmentBuilder(Assignment assignmentToCopy) {
        student = assignmentToCopy.getStudent();
        assignmentName = new AssignmentName(assignmentToCopy.getName());
        maxScore = assignmentToCopy.getMaxScore();
        score = assignmentToCopy.getScore();
        hasSubmitted = assignmentToCopy.getHasSubmitted();
    }

    /**
     * Sets the {@code student} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withStudent(Student student) {
        this.student = student;
        return this;
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
        Assignment assignment = new Assignment(student, assignmentName, maxScore);
        assignment.setScore(this.score);
        assignment.setHasSubmitted(this.hasSubmitted);
        return assignment;
    }
}
