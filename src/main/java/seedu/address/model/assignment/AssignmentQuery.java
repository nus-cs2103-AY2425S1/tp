package seedu.address.model.assignment;

import java.util.Optional;

/**
 * Contains a set of parameters. Used to match assignments that fit a specific query.
 */
public class AssignmentQuery {

    private final Optional<AssignmentName> queryName;
    private final Optional<Deadline> queryDeadline;
    private final Optional<Status> querySubmissionStatus;
    private final Optional<Status> queryGradingStatus;
    private final Optional<Grade> queryGrade;

    /**
     * Constructs an {@code AssignmentQuery}
     */
    public AssignmentQuery(AssignmentName assignmentName, Deadline deadline, Status submissionStatus,
                           Status gradingStatus, Grade grade) {
        this.queryName = Optional.ofNullable(assignmentName);
        this.queryDeadline = Optional.ofNullable(deadline);
        this.querySubmissionStatus = Optional.ofNullable(submissionStatus);
        this.queryGradingStatus = Optional.ofNullable(gradingStatus);
        this.queryGrade = Optional.ofNullable(grade);
    }

    /**
     * Constructs an {@code AssignmentQuery}
     */
    public AssignmentQuery(Assignment assignment) {
        this.queryName = Optional.ofNullable(assignment.getAssignmentName());
        this.queryDeadline = Optional.ofNullable(assignment.getDeadline());
        this.querySubmissionStatus = Optional.ofNullable(assignment.getSubmissionStatus());
        this.queryGradingStatus = Optional.ofNullable(assignment.getGradingStatus());
        this.queryGrade = Optional.ofNullable(assignment.getGrade());
    }


    /**
     * Generic function that matches a query to a variable.
     * Returns true if the field matches the query OR query is empty.
     *
     * @param query An object T or NULL
     * @param field The object T to test
     */
    private <T> boolean matchQuery(Optional<T> query, T field) {
        return query.map(t -> t.equals(field)).orElse(true);
    }

    /**
     * Checks if the query matches the given assignment
     *
     * @param assignment A valid assignment
     * @return
     */
    public boolean match(Assignment assignment) {
        return matchQuery(queryName, assignment.getAssignmentName())
                && matchQuery(queryDeadline, assignment.getDeadline())
                && matchQuery(querySubmissionStatus, assignment.getSubmissionStatus())
                && matchQuery(queryGradingStatus, assignment.getGradingStatus())
                && matchQuery(queryGrade, assignment.getGrade());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AssignmentQuery)) {
            return false;
        }

        AssignmentQuery otherQuery = (AssignmentQuery) other;
        return otherQuery.queryName.equals(this.queryName)
                && otherQuery.queryDeadline.equals(this.queryDeadline)
                && otherQuery.querySubmissionStatus.equals(this.querySubmissionStatus)
                && otherQuery.queryGradingStatus.equals(this.queryGradingStatus)
                && otherQuery.queryGrade.equals(this.queryGrade);
    }


}
