package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showConcertContactAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBookConcertContacts;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERTCONTACT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListConcertContactCommand.
 */
public class ListConcertContactCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookConcertContacts(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(ListConcertContactCommand.MESSAGE_SUCCESS,
                false, false, true);
        assertCommandSuccess(new ListConcertContactCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandResult expectedCommandResult = new CommandResult(ListConcertContactCommand.MESSAGE_SUCCESS,
                false, false, true);
        showConcertContactAtIndex(model, INDEX_FIRST_CONCERTCONTACT);
        assertCommandSuccess(new ListConcertContactCommand(), model, expectedCommandResult, expectedModel);
    }
}
