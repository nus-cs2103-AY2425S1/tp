package seedu.academyassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.Messages;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.testutil.PersonBuilder;

public class DetailCommandTest {

    private Model model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());

    // Test with valid student ID
    @Test
    public void execute_validStudentId_success() throws Exception {
        Person personToView = new PersonBuilder("S10001").build();
        DetailCommand detailCommand = new DetailCommand(personToView.getStudentId());
        CommandResult commandResult = detailCommand.execute(model);
        String expectedMessage = String.format(commandResult.getFeedbackToUser());

        ModelManager expectedModel = new ModelManager(model.getAcademyAssist(), new UserPrefs());
        CommandResult expectedCommandResult = new CommandResult(
                expectedMessage, false, false, false, true, personToView);
        assertCommandSuccess(detailCommand, model, expectedCommandResult, expectedModel);
    }

    // Test with invalid student ID
    @Test
    public void execute_invalidStudentId_throwsCommandException() {
        StudentId invalidStudentId = new StudentId("S99999");
        DetailCommand detailCommand = new DetailCommand(invalidStudentId);

        assertCommandFailure(detailCommand, model, Messages.MESSAGE_NO_STUDENT_FOUND);
    }

    // Test for equals method
    @Test
    public void equals() {
        StudentId firstStudentId = new StudentId("S10001");
        StudentId secondStudentId = new StudentId("S10002");
        DetailCommand detailFirstCommand = new DetailCommand(firstStudentId);
        DetailCommand detailSecondCommand = new DetailCommand(secondStudentId);

        // same object -> returns true
        assertTrue(detailFirstCommand.equals(detailFirstCommand));

        // same values -> returns true
        DetailCommand detailFirstCommandCopy = new DetailCommand(firstStudentId);
        assertTrue(detailFirstCommand.equals(detailFirstCommandCopy));

        // different types -> returns false
        assertFalse(detailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(detailFirstCommand.equals(null));

        // different student ID -> returns false
        assertFalse(detailFirstCommand.equals(detailSecondCommand));

    }

    // Test for toString method
    @Test
    public void toStringTest() {
        StudentId studentId = new StudentId("S10001");
        DetailCommand detailCommand = new DetailCommand(studentId);
        String expectedString = "seedu.academyassist.logic.commands.DetailCommand{targetStudentId=S10001}";

        assertTrue(detailCommand.toString().equals(expectedString));
    }
}
