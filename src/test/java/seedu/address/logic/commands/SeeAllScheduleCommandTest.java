package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetings;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SeeAllScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(),
            new UserPrefs(), getTypicalMeetings());

    @Test
    public void execute_success() {
        SeeAllScheduleCommand command = new SeeAllScheduleCommand();
        CommandResult expectedCommandResult = new CommandResult(
                SeeAllScheduleCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(command, model, expectedCommandResult, model);
        assertEquals(3, model.getScheduleList().getMeetingList().size());
    }

    @Test
    public void equals() {
        SeeAllScheduleCommand firstCommand = new SeeAllScheduleCommand();
        SeeAllScheduleCommand secondCommand = new SeeAllScheduleCommand();
        // same object -> returns true
        assertEquals(firstCommand, firstCommand);
        // same values -> returns true
        assertEquals(firstCommand, secondCommand);
        // different types -> returns false
        assertNotEquals(1, firstCommand);
        // null -> returns false
        assertNotEquals(null, firstCommand);
        // completely different command -> returns false
        assertNotEquals(firstCommand, new ListCommand());
    }
}
