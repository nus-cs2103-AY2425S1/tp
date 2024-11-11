package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListPersonsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs());
    }

    @Test
    public void execute_personListIsNotFiltered_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(
                ListPersonsCommand.MESSAGE_SUCCESS, false, false, true, false);

        assertCommandSuccess(new ListPersonsCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_personListIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        CommandResult expectedCommandResult = new CommandResult(
                ListPersonsCommand.MESSAGE_SUCCESS, false, false, true, false);

        assertCommandSuccess(new ListPersonsCommand(), model, expectedCommandResult, expectedModel);
    }
}
