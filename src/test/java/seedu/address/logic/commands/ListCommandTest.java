package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWeddings.getTypicalWeddingBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), getTypicalWeddingBook());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        int numberOfPersons = model.getAddressBook().getPersonList().size();
        String numberOfContacts = ", there are currently " + numberOfPersons + " contacts in your address book";
        if (numberOfPersons == 0) {
            assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_EMPTY, expectedModel);
        } else {
            assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS
                    + numberOfContacts, expectedModel);
        }
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        int numberOfPersons = model.getAddressBook().getPersonList().size();
        String numberOfContacts = ", there are currently " + numberOfPersons + " contacts in your address book";
        if (numberOfPersons == 0) {
            assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_EMPTY, expectedModel);
        } else {
            assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS
                    + numberOfContacts, expectedModel);
        }
    }
}
