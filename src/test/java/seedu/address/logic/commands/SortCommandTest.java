package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    // Helper method to convert a list of typical persons into a ReadOnlyAddressBook
    private ReadOnlyAddressBook getTypicalAddressBook() {
        AddressBook addressBook = new AddressBook();
        for (Person person : getTypicalPersons()) {
            addressBook.addPerson(person);
        }
        return addressBook;
    }

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortAsc_success() throws CommandException {
        SortCommand sortCommand = new SortCommand("asc");
        CommandResult commandResult = sortCommand.execute(model);

        // Check if the result message is correct
        assertEquals(SortCommand.MESSAGE_SUCCESS_ASC, commandResult.getFeedbackToUser());

        // Add more assertions as necessary to compare the sorted order in model and expectedModel
    }

    @Test
    public void execute_sortDesc_success() throws CommandException {
        SortCommand sortCommand = new SortCommand("desc");
        CommandResult commandResult = sortCommand.execute(model);

        // Check if the result message is correct
        assertEquals(SortCommand.MESSAGE_SUCCESS_DESC, commandResult.getFeedbackToUser());

        // Add more assertions as necessary to compare the sorted order in model and expectedModel
    }

    @Test
    public void execute_invalidOrder_failure() throws CommandException {
        SortCommand sortCommand = new SortCommand("invalidOrder");
        CommandResult commandResult = sortCommand.execute(model);

        // Check if the result message is correct
        assertEquals(SortCommand.MESSAGE_INVALID_ORDER, commandResult.getFeedbackToUser());
    }
}
