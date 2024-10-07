package seedu.address.testutil;

import seedu.address.model.student.Student;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_NUMBER_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACT_NUMBER_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_DIDDY;

public class TypicalStudents {

    public static final Student DIDDY = new StudentBuilder().withName(VALID_NAME_DIDDY)
            .withContactNumber(VALID_CONTACT_NUMBER_DIDDY).withTutorialGroup(VALID_TUTORIAL_GROUP_DIDDY)
            .withStudentNumber(VALID_STUDENT_NUMBER_DIDDY).build();

    public static final Student HUGH = new StudentBuilder().withName(VALID_NAME_HUGH)
            .withContactNumber(VALID_CONTACT_NUMBER_HUGH).withTutorialGroup(VALID_TUTORIAL_GROUP_HUGH)
            .withStudentNumber(VALID_STUDENT_NUMBER_HUGH).build();

    private TypicalStudents() {} // prevents instantiation
}
