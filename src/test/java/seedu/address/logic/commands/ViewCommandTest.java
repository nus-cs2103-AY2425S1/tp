package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;

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

    @Test
    public void execute_invalidPersonName_failure() {
        Name invalidName = new Name("FancyPants");
        ViewCommand viewCommand = new ViewCommand(invalidName);
        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
    }

    @Test
    public void equals() {
        ViewCommand viewAmyCommand = new ViewCommand(AMY.getName());
        ViewCommand viewBobCommand = new ViewCommand(BOB.getName());

        // same object -> returns true
        assertTrue(viewAmyCommand.equals(viewAmyCommand));

        // same values -> returns true
        ViewCommand viewAmyCommandCopy = new ViewCommand(AMY.getName());
        assertTrue(viewAmyCommand.equals(viewAmyCommandCopy));

        // different types -> returns false
        assertFalse(viewAmyCommand.equals(1));

        // null -> returns false
        assertFalse(viewAmyCommand.equals(null));

        // different person -> returns false
        assertFalse(viewAmyCommand.equals(viewBobCommand));
    }

}
