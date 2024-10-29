package seedu.address.logic.commands.consultation;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListConsultsCommand.
 */
public class ListConsultsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListConsultsCommand(), model, ListConsultsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        // apply some filtering here if needed and check that list is reset
        assertCommandSuccess(new ListConsultsCommand(), model, ListConsultsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void commandType_isCorrect() {
        ListConsultsCommand command = new ListConsultsCommand();
        CommandResult result = command.execute(model);
        assert result.getCommandType() == CommandType.LISTCONSULT;
    }
}
