package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class CommandListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_typicalAddressBookUnFilteredList_success() {
        AddressBook addressBookOfCurrentModel = (AddressBook) model.getAddressBook();
        String commandList = ParserUtil.parseCommandList(addressBookOfCurrentModel);
        assertCommandSuccess(new CommandListCommand(), model,
                CommandListCommand.MESSAGE_SUCCESS + commandList, expectedModel);
    }

    @Test
    public void execute_typicalAddressBookFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AddressBook addressBookOfCurrentModel = (AddressBook) model.getAddressBook();
        String commandList = ParserUtil.parseCommandList(addressBookOfCurrentModel);

        assertCommandSuccess(new CommandListCommand(), model,
                CommandListCommand.MESSAGE_SUCCESS + commandList, expectedModel);
    }

}

