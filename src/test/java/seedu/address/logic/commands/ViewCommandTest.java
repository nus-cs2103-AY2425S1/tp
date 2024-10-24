package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.ui.ContactDisplay;
import seedu.address.ui.TestContactDisplay;

public class ViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private TestContactDisplay contactDisplay = new TestContactDisplay();

    @Test
    public void execute_validIndex_updatesContactDisplay() throws CommandException {
        Person validPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        ViewCommand command = new ViewCommand(INDEX_FIRST_PERSON, contactDisplay);
        CommandResult commandResult = command.execute(model);
        assertEquals("Name: " + validPerson.getName().fullName, contactDisplay.getName());
        assertEquals("Category: " + validPerson.getCategoryDisplayName(), contactDisplay.getCategory());
        assertEquals("Phone: " + validPerson.getPhone().value, contactDisplay.getPhone());
        assertEquals("Email: " + validPerson.getEmail().value, contactDisplay.getEmail());
    }

    @Test
    public void execute_clearContactDisplay_resetsContactFields() {
        contactDisplay.clear();

        assertEquals("Name:", contactDisplay.getName());
        assertEquals("Category:", contactDisplay.getCategory());
        assertEquals("Phone:", contactDisplay.getPhone());
        assertEquals("Email:", contactDisplay.getEmail());
    }

    @Test
    public void execute_showHelpDisplay() {
        contactDisplay.showHelpDisplay();

        assertEquals(ContactDisplay.CONDENSED_HELP_MESSAGE, contactDisplay.getName());
        assertEquals(null, contactDisplay.getCategory());
        assertEquals(null, contactDisplay.getPhone());
        assertEquals(null, contactDisplay.getEmail());
    }

}
