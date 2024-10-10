package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class FilterCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        FilterCommand filterFirstCommand = new FilterCommand("first", null);
        FilterCommand filterSecondCommand = new FilterCommand(null, "second");

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand("first", null);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }
    //Test case for filtering using name only
    @Test
    public void execute_nameKeyword_personsFound() {
        String expectedMessage = FilterCommand.constructSuccessMessage("Meier", null);
        FilterCommand command = new FilterCommand("Meier", null);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }
    //Test case for filtering using tag only
    @Test
    public void execute_tagKeyword_personsFound() {
        String expectedMessage = FilterCommand.constructSuccessMessage(null, "owesMoney");
        FilterCommand command = new FilterCommand(null, "owesMoney");
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }
    //Test case for fitlering usign both
    @Test
    public void execute_nameAndTagKeywords_personFound() {
        String expectedMessage = FilterCommand.constructSuccessMessage("Meier", "friends");
        FilterCommand command = new FilterCommand("Meier", "friends");
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }
    //Test case for when there is no one after filtering
    @Test
    public void execute_noMatchingNameOrTag_allPersonsReturned() {
        String expectedMessage = FilterCommand.MESSAGE_NO_CONTACT_FOUND;
        FilterCommand command = new FilterCommand("Damith", "2103");
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }
    //Test case to test parital string fitlering
    @Test
    public void execute_partialNameMatch_personsFound() {
        String expectedMessage = FilterCommand.constructSuccessMessage("er", null);
        FilterCommand command = new FilterCommand("er", null);
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BENSON, DANIEL, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        FilterCommand filterCommand = new FilterCommand("John", "client");
        String expected = FilterCommand.class.getCanonicalName() + "{name=John, tagName=client}";
        assertEquals(expected, filterCommand.toString());
    }
}
