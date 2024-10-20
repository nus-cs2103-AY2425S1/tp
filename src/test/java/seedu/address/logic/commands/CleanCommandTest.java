package seedu.address.logic.commands;

import static seedu.address.logic.commands.CleanCommand.MESSAGE_CLEAN_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code CleanCommand}.
 */
public class CleanCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_clean_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_CLEAN_SUCCESS);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<Person> lastShownList = expectedModel.getFilteredPersonList();
        for (int i = lastShownList.size() - 1; i >= 0; i--) {
            expectedModel.deletePerson(lastShownList.get(i));
        }

        assertCommandSuccess(new CleanCommand(), model, expectedCommandResult, expectedModel);
    }
}
