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
     * Returns assignment name
     */
    public String getName() {
        return assignmentName.fullName;
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
}
