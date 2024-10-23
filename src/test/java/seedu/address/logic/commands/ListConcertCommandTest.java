package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showConcertAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBookConcerts;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListConcertCommand.
 */
public class ListConcertCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookConcerts(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListConcertCommand(), model, ListConcertCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showConcertAtIndex(model, INDEX_FIRST_CONCERT);
        assertCommandSuccess(new ListConcertCommand(), model, ListConcertCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
