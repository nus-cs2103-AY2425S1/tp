package seedu.sellsavvy.logic.commands.personcommands;

import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.showPersonAtIndex;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.sellsavvy.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListPersonCommand.
 */
public class ListPersonCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListPersonCommand(), model, ListPersonCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListPersonCommand(), model, ListPersonCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
