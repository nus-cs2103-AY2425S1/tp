package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.CommandHistory;
import seedu.address.model.HistoricalAddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Deque<ReadOnlyAddressBook> expectedHistoryDeque = new ArrayDeque<>();
        expectedHistoryDeque.add(new HistoricalAddressBook());
        HistoricalAddressBook expectedHistoricalAddressBook = new HistoricalAddressBook(
                expectedHistoryDeque, new ArrayDeque<>(), new AddressBook()
        );
        Model expectedModel = new ModelManager();
        expectedModel.setAddressBook(expectedHistoricalAddressBook);

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new CommandHistory());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
