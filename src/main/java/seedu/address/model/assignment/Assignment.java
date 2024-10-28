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
    private Status gradingStatus;
    private Grade grade;

    /**
     * Constructs a {@code Assignment}
     *
     * @param assignmentName A valid name
     * @param deadline A valid date
     * @param submissionStatus A valid subsmission status
     * @param gradingStatus A valid grading status
     * @param grade A valid grade
     */
    public Assignment(AssignmentName assignmentName, Deadline deadline, Status submissionStatus,
                      Status gradingStatus, Grade grade) {
        requireAllNonNull(assignmentName, deadline, submissionStatus, gradingStatus);
        this.assignmentName = assignmentName;
        this.deadline = deadline;
        this.submissionStatus = submissionStatus;
        this.gradingStatus = gradingStatus;
        this.grade = grade;
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
     * Returns the {@code Grading Status} of the assignment.
     *
     * @return The grading status of the assignment.
     */
    public Status getGradingStatus() {
        return gradingStatus;
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

    /**
     * Edits relevant fields
     *
     * @param assignmentQuery fields to edit
     */
    public void edit(AssignmentQuery assignmentQuery) {
        this.deadline = assignmentQuery.queryDeadline.orElse(this.deadline);
        this.submissionStatus = assignmentQuery.querySubmissionStatus.orElse(this.submissionStatus);
        this.gradingStatus = assignmentQuery.queryGradingStatus.orElse(this.gradingStatus);
        this.grade = assignmentQuery.queryGrade.orElse(this.grade);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", assignmentName)
                .add("deadline", deadline)
                .add("submission status", submissionStatus)
                .add("grading status", gradingStatus)
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
                && this.gradingStatus.equals(otherAssignment.gradingStatus)
                && this.grade.equals(otherAssignment.grade);
    }
}
