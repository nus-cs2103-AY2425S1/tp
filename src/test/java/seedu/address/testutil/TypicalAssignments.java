package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {

    // Constants for common values
    public static final AssignmentName ASSIGNMENT_NAME_A = new AssignmentName("Math Homework");
    public static final AssignmentName ASSIGNMENT_NAME_B = new AssignmentName("Science Project");
    public static final AssignmentName ASSIGNMENT_NAME_C = new AssignmentName("English Essay");

    public static final Deadline DEADLINE_A = new Deadline("2024-10-20");
    public static final Deadline DEADLINE_B = new Deadline("2024-11-15");
    public static final Deadline DEADLINE_C = new Deadline("2024-12-01");

    public static final Status STATUS_Y = new Status("Y"); // Represents completed/graded status
    public static final Status STATUS_N = new Status("N"); // Represents not completed/not graded status

    public static final Grade GRADE_80 = new Grade("80");
    public static final Grade GRADE_90 = new Grade("90");
    public static final Grade GRADE_NULL = Grade.getDefault(); // Represents NULL or ungraded

    // Typical Assignments for testing
    public static final Assignment MATH_ASSIGNMENT_SUBMITTED = new Assignment(
            ASSIGNMENT_NAME_A, DEADLINE_A, STATUS_Y, GRADE_NULL
    );

    public static final Assignment SCIENCE_ASSIGNMENT_GRADED = new Assignment(
            ASSIGNMENT_NAME_B, DEADLINE_B, STATUS_Y, GRADE_90
    );

    public static final Assignment ENGLISH_ASSIGNMENT_NOT_SUBMITTED = new Assignment(
            ASSIGNMENT_NAME_C, DEADLINE_C, STATUS_N, GRADE_NULL
    );

}
