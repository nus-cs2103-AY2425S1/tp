package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;

/**
 * A utility class to help with building Assignment objects.
 */
public class AssignmentBuilder {
    public static final String DEFAULT_NAME = "2103 TP";
    public static final String DEFAULT_DEADLINE = "2024-12-01";

    private AssignmentName name;
    private Deadline deadline;
    private Status submissionStatus;
    private Status gradingStatus;
    private Grade grade;

    /**
     * Creates a {@code AssignmentBuilder} with the default details.
     */
    public AssignmentBuilder() {
        this.name = new AssignmentName(DEFAULT_NAME);
        this.deadline = new Deadline(DEFAULT_DEADLINE);
        this.submissionStatus = Status.getDefault();
        this.gradingStatus = Status.getDefault();
        this.grade = Grade.getDefault();
    }

    /**
     * Creates a {@code AssignmentBuilder} from an existing assignment.
     */
    public AssignmentBuilder(Assignment assignment) {
        this.name = assignment.getAssignmentName();
        this.deadline = assignment.getDeadline();
        this.submissionStatus = assignment.getSubmissionStatus();
        this.gradingStatus = assignment.getGradingStatus();
        this.grade = assignment.getGrade();
    }

    /**
     * Sets the {@code Name} of the {@code Assignment} that we are building.
     *
     * @param name The assignment name to set.
     * @return The updated {@code AssignmentBuilder} object.
     */
    public AssignmentBuilder withName(String name) {
        this.name = new AssignmentName(name);
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Assignment} that we are building.
     *
     * @param deadline The deadline to set.
     * @return The updated {@code AssignmentBuilder} object.
     */
    public AssignmentBuilder withDeadline(String deadline) {
        this.deadline = new Deadline(deadline);
        return this;
    }

    /**
     * Sets the {@code Submission Status} of the {@code Assignment} that we are building.
     *
     * @param submissionStatus The submission status to set.
     * @return The updated {@code AssignmentBuilder} object.
     */
    public AssignmentBuilder withSubmissionStatus(String submissionStatus) {
        this.submissionStatus = new Status(submissionStatus);
        return this;
    }

    /**
     * Sets the {@code Grading Status} of the {@code Assignment} that we are building.
     *
     * @param gradingStatus The grading status to set.
     * @return The updated {@code AssignmentBuilder} object.
     */
    public AssignmentBuilder withGradingStatus(String gradingStatus) {
        this.gradingStatus = new Status(gradingStatus);
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code Assignment} that we are building.
     *
     * @param grade The grade to set.
     * @return The updated {@code AssignmentBuilder} object.
     */
    public AssignmentBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
        return this;
    }

    public Assignment build() {
        return new Assignment(name, deadline, submissionStatus, gradingStatus, grade);
    }


}
