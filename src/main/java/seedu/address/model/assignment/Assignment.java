package seedu.address.model.assignment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a person's assignment
 * Guarantees: immutable; fields are non-null except for grade
 */
public class Assignment {

    private AssignmentName assignmentName;
    private Deadline deadline;
    private Status submissionStatus;
    private Grade grade;

    /**
     * Represents if the assignment has been graded and submitted.
     */
    public enum State {
        GRADED,
        SUBMITTED,
        PENDING
    }

    /**
     * Constructs a {@code Assignment}
     *
     * @param assignmentName A valid name
     * @param deadline A valid date
     * @param submissionStatus A valid subsmission status
     * @param grade A valid grade
     */
    public Assignment(AssignmentName assignmentName, Deadline deadline, Status submissionStatus,
                      Grade grade) {
        requireAllNonNull(assignmentName, deadline, submissionStatus);
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.submissionStatus = submissionStatus;
        this.grade = grade;
    }

    /**
     * Constructs an empty {@code Assignment}
     */
    private Assignment() {

    }

    /**
     * Returns the {@code AssignmentName} of the assignment.
     *
     * @return The name of the assignment.
     */
    public AssignmentName getAssignmentName() {
        return assignmentName;
    }

    /**
     * Returns the {@code Deadline} of the assignment.
     *
     * @return The deadline of the assignment.
     */
    public Deadline getDeadline() {
        return deadline;
    }

    /**
     * Returns the {@code Submission Status} of the assignment.
     *
     * @return The submission status of the assignment.
     */
    public Status getSubmissionStatus() {
        return submissionStatus;
    }

    /**
     * Returns the {@code Grade} of the assignment.
     *
     * @return The grade of the assignment.
     */
    public Grade getGrade() {
        return grade;
    }

    public boolean isSameAssignment(Assignment other) {
        return assignmentName.equals(other.assignmentName);
    }

    public boolean isValid() {
        return submissionStatus.isSubmitted() || !grade.isGraded();
    }

    /**
     * Edits relevant fields
     *
     * @param assignmentQuery fields to edit
     */
    public Assignment edit(AssignmentQuery assignmentQuery) {
        Assignment assignment = new Assignment();

        assignment.assignmentName = this.assignmentName;
        assignment.deadline = assignmentQuery.queryDeadline.orElse(this.deadline);
        assignment.submissionStatus = assignmentQuery.querySubmissionStatus.orElse(this.submissionStatus);
        assignment.grade = assignmentQuery.queryGrade.orElse(this.grade);
        return assignment;
    }

    public State getState() {
        return grade.isGraded()
                ? State.GRADED
                : submissionStatus.isSubmitted()
                    ? State.SUBMITTED
                    : State.PENDING;
    }

    public String getLabelName() {
        return assignmentName.fullName
                + (grade.isGraded() ? String.format(": %s", grade) : "");
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", assignmentName)
                .add("deadline", deadline)
                .add("submission status", submissionStatus)
                .add("grade", grade)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherAssignment = (Assignment) other;
        return this.assignmentName.equals(otherAssignment.assignmentName)
                && this.deadline.equals(otherAssignment.deadline)
                && this.submissionStatus.equals(otherAssignment.submissionStatus)
                && this.grade.equals(otherAssignment.grade);
    }
}
