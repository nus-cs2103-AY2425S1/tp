package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignments.TUTORIAL_HOMEWORK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalStudents.HUGH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class AddAssignmentCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }
    @Test
    public void execute_newAssignment_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student hughCopy = new StudentBuilder(HUGH).build();
        hughCopy.addAssignment(TUTORIAL_HOMEWORK);
        expectedModel.addStudent(hughCopy);

        model.addStudent(HUGH);
        assertCommandSuccess(new AddAssignmentCommand(HUGH.getName(), TUTORIAL_HOMEWORK), model,
                String.format(AddAssignmentCommand.MESSAGE_SUCCESS,
                        TUTORIAL_HOMEWORK.getAssignmentName(), HUGH.getName()),
                expectedModel);
    }
}
