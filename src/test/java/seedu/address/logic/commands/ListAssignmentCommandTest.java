package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tut.TutorialList;
import seedu.address.testutil.TypicalAssignments;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAssignmentCommand.
 */
public class ListAssignmentCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                TypicalAssignments.getTypicalAssignmentList(), new TutorialList());
    }

    @Test
    public void execute_listAssignment_showsCorrectAssignmentList() {
        CommandResult commandResult = new ListAssignmentCommand().execute(model);
        String expectedMessage = "All current assignments: \n"
                + "1. Assignment 1 due on 10 Oct 2024 23:59 (5 students have completed!)\n"
                + "2. Assignment 2 due on 10 Oct 2024 23:59 (4 students have completed!)\n";
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        ListAssignmentCommand listAssignmentCommand = new ListAssignmentCommand();
        ListAssignmentCommand listAssignmentCommandCopy = new ListAssignmentCommand();
        assertEquals(listAssignmentCommand, listAssignmentCommandCopy);
    }
}
