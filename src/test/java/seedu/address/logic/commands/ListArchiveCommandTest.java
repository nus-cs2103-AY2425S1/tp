package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ListArchiveCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs(), getTypicalAddressBook());
        expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), model.getArchivedAddressBook());
    }

    @Test
    public void execute_archivedList_showsSameList() {
        assertCommandSuccess(new ListArchiveCommand(), model, ListArchiveCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
