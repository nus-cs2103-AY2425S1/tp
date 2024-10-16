package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_SCORE_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_SCORE_PHYSICS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.assignment.Assignment;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {

    public static final Assignment MATH_HOMEWORK = new AssignmentBuilder()
            .withAssignmentName(VALID_ASSIGNMENT_NAME_MATH)
            .withMaxScore(VALID_MAX_SCORE_MATH).build();
    public static final Assignment PHYSICS_HOMEWORK = new AssignmentBuilder()
            .withAssignmentName(VALID_ASSIGNMENT_NAME_PHYSICS)
            .withMaxScore(VALID_MAX_SCORE_PHYSICS).build();

    public static List<Assignment> getTypicalAssignments() {
        return new ArrayList<>(Arrays.asList(MATH_HOMEWORK, PHYSICS_HOMEWORK));
    }
}
