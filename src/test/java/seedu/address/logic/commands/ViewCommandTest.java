package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewCommand.
 */
public class ViewCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(String.format(ViewCommand.MESSAGE_VIEW_SUCCESS,
                model.getAddressBook().getPersonList().get(0).getName()),
                model.getAddressBook().getPersonList().get(0));
        assertCommandSuccess(new ViewCommand(model.getAddressBook().getPersonList().get(0).getName()),
                model, expectedCommandResult, expectedModel);
    }

}
