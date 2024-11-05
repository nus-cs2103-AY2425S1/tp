package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.ArchivedAddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @BeforeEach
    public void setUp() {
        Model model = new ModelManager();
        model.setArchivedListMode(false);
    }
    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_archivedList_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        model.setArchivedListMode(true);
        assertCommandFailure(new ClearCommand(), model, Messages.MESSAGE_NOT_IN_MAIN_LIST);
    }

}
