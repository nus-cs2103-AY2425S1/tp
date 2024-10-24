package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showWeddingAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WEDDING;
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
public class ListWeddingCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalWeddingBook());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), getTypicalWeddingBook());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        int numberOfWeddings = model.getWeddingBook().getWeddingList().size();
        String numberOfEvents = ", there are currently " + numberOfWeddings + " weddings in your address book";
        if (numberOfWeddings == 0) {
            assertCommandSuccess(new ListWeddingCommand(), model, ListWeddingCommand.MESSAGE_EMPTY, expectedModel);
        } else {
            assertCommandSuccess(new ListWeddingCommand(), model, ListWeddingCommand.MESSAGE_SUCCESS
                    + numberOfEvents, expectedModel);
        }
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showWeddingAtIndex(model, INDEX_FIRST_WEDDING);
        int numberOfWeddings = model.getWeddingBook().getWeddingList().size();
        String numberOfEvents = ", there are currently " + numberOfWeddings + " weddings in your address book";
        if (numberOfWeddings == 0) {
            assertCommandSuccess(new ListWeddingCommand(), model, ListWeddingCommand.MESSAGE_EMPTY, expectedModel);
        } else {
            assertCommandSuccess(new ListWeddingCommand(), model, ListWeddingCommand.MESSAGE_SUCCESS
                    + numberOfEvents, expectedModel);
        }
    }

}
