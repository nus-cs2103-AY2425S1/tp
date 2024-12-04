package seedu.address.logic.commands.clientcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SEVENTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ShowClientsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ShowClientsCommand(), model, ShowClientsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ShowClientsCommand(), model, ShowClientsCommand.MESSAGE_SUCCESS, expectedModel);

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        assertCommandSuccess(new ShowClientsCommand(), model, ShowClientsCommand.MESSAGE_SUCCESS, expectedModel);

        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        assertCommandSuccess(new ShowClientsCommand(), model, ShowClientsCommand.MESSAGE_SUCCESS, expectedModel);

        showPersonAtIndex(model, INDEX_FOURTH_PERSON);
        assertCommandSuccess(new ShowClientsCommand(), model, ShowClientsCommand.MESSAGE_SUCCESS, expectedModel);

        showPersonAtIndex(model, INDEX_FIFTH_PERSON);
        assertCommandSuccess(new ShowClientsCommand(), model, ShowClientsCommand.MESSAGE_SUCCESS, expectedModel);

        showPersonAtIndex(model, INDEX_SIXTH_PERSON);
        assertCommandSuccess(new ShowClientsCommand(), model, ShowClientsCommand.MESSAGE_SUCCESS, expectedModel);

        showPersonAtIndex(model, INDEX_SEVENTH_PERSON);
        assertCommandSuccess(new ShowClientsCommand(), model, ShowClientsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        model = new ModelManager(new AddressBook(), new UserPrefs(), new Listings());
        assertCommandFailure(new ShowClientsCommand(), model, ShowClientsCommand.MESSAGE_NO_CLIENT_IN_LIST);
    }
}
