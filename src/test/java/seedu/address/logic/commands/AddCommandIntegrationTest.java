package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TutUtil.TUTORIAL_ID;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTutorials.getTypicalTutorialList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.AssignmentList;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new AssignmentList(),
                getTypicalTutorialList());
    }

    @Test
    public void execute_newStudent_success() {
        Student validStudent = new StudentBuilder().withStudentId("A1234567U").build();


        Model expectedModel = new ModelManager(model.getAddressBook(),
                new UserPrefs(), new AssignmentList(), getTypicalTutorialList());
        expectedModel.addStudent(validStudent);

        assertCommandSuccess(new AddCommand(validStudent, TUTORIAL_ID), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validStudent)),
                expectedModel);
    }
}
