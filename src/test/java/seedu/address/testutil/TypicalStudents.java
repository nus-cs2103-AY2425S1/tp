package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_HUGH;

import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student DIDDY = new StudentBuilder().withName(VALID_NAME_DIDDY)
            .withPhone(VALID_PHONE_DIDDY).withTutorialGroup(VALID_TUTORIAL_GROUP_DIDDY)
            .withStudentNumber(VALID_STUDENT_NUMBER_DIDDY).build();

    public static final Student HUGH = new StudentBuilder().withName(VALID_NAME_HUGH)
            .withPhone(VALID_PHONE_HUGH).withTutorialGroup(VALID_TUTORIAL_GROUP_HUGH)
            .withStudentNumber(VALID_STUDENT_NUMBER_HUGH).build();

    private TypicalStudents() {} // prevents instantiation
}
