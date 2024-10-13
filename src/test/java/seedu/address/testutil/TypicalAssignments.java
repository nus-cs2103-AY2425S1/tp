package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.assignment.Assignment;

/**
 * A utility class containing a list of {@code Assignments} objects to be used in tests.
 */
public class TypicalAssignments {
    public static final Assignment ASSIGNMENT_1 = new Assignment("Assignment 1",
            LocalDateTime.of(2024, 10, 10, 23, 59));
    public static final Assignment ASSIGNMENT_2 = new Assignment("Assignment 2",
            LocalDateTime.of(2024, 10, 10, 23, 59));
    public static final Assignment ASSIGNMENT_3 = new Assignment("Assignment 3",
            LocalDateTime.of(1000, 10, 10, 10, 10));
    public static final Assignment ASSIGNMENT_4 = new Assignment("Assignment 4",
            LocalDateTime.of(2000, 10, 10, 20, 20));
}
