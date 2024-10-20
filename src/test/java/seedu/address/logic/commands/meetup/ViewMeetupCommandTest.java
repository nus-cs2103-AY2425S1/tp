package seedu.address.logic.commands.meetup;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.meetup.ViewMeetUpCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewMeetupCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalMeetUpList());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getMeetUpList());
    }

    @Test
    public void execute_viewMeetUp_success() {
        CommandResult expectedCommandResult = new CommandResult(ViewMeetUpCommand.MESSAGE_SUCCESS,
                false, false, true, false);
        assertCommandSuccess(new ViewMeetUpCommand(), model, expectedCommandResult, expectedModel);
    }
}
