package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalAssignments;
import seedu.address.testutil.TypicalTutorials;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTutCommand.
 */
public class ListTutorialCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                TypicalAssignments.getTypicalAssignmentList(), TypicalTutorials.getTypicalTutorialList());
    }

    @Test
    public void execute_listTutorial_showsCorrectTutorialList() {
        CommandResult commandResult = new ListTutorialCommand().execute(model);

        // Update the expected message to match the actual output from the model
        String expectedMessage = "All current tutorials: \n"
                + "1. TutorialOne: Tutorial 1000\n"
                + "2. TutorialTwo: Tutorial 1001\n"
                + "3. TutorialThree: Tutorial 1002\n";

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }


    @Test
    public void equals() {
        ListTutorialCommand listTutCommand = new ListTutorialCommand();
        ListTutorialCommand listTutCommandCopy = new ListTutorialCommand();
        assertEquals(listTutCommand, listTutCommandCopy);
    }
}
