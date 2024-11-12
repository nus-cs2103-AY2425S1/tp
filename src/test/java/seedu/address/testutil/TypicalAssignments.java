package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentList;

/**
 * A utility class containing a list of {@code Assignments} objects to be used in tests.
 */
public class TypicalAssignments {

    public static final Assignment ASSIGNMENT1 = new Assignment("Assignment 1",
            LocalDateTime.of(2024, 10, 10, 23, 59));
    public static final Assignment ASSIGNMENT2 = new Assignment("Assignment 2",
            LocalDateTime.of(2024, 10, 10, 23, 59));
    /**
     * Returns an {@code AssignmentList} with typical assignments that have statuses.
     */
    public static AssignmentList getTypicalAssignmentList() {
        Assignment assignment1 = new Assignment("Assignment 1",
                LocalDateTime.of(2024, 10, 10, 23, 59));
        Assignment assignment2 = new Assignment("Assignment 2",
                LocalDateTime.of(2024, 10, 10, 23, 59));
        for (int i = 1001; i < 1010; i++) {
            assignment1.markStatus(Integer.toString(i), i % 2 != 0);
            assignment2.markStatus(Integer.toString(i), i % 2 == 0);
        }
        AssignmentList assignmentList = new AssignmentList();
        assignmentList.addAssignment(assignment1);
        assignmentList.addAssignment(assignment2);
        return assignmentList;
    }

}
