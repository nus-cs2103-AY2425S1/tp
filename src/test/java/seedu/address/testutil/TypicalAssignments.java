package seedu.address.testutil;

import seedu.address.model.assignment.Assignment;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {

    public static final Assignment TUTORIAL_HOMEWORK = new AssignmentBuilder()
            .withName("2103 Tutorial")
            .withDeadline("2100-12-05")
            .withSubmissionStatus("N")
            .withGradingStatus("N")
            .withGrade("NULL")
            .build();

    public static final Assignment EXAM_PREP = new AssignmentBuilder()
            .withName("2103 Finals")
            .withDeadline("2026-12-14")
            .withSubmissionStatus("Y")
            .withGradingStatus("Y")
            .withGrade("0.0")
            .build();

}
