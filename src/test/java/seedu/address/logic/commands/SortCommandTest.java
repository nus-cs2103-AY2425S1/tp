package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY_CLONE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB_CLONE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        AddressBook ab = new AddressBook();
        for (Person person : new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE,
                FIONA, GEORGE, AMY_CLONE, BOB_CLONE))) {
            ab.addPerson(person);
        }
        model = new ModelManager(ab, new UserPrefs());
    }

    @Test
    public void equals() {
        SortCommand sortFirstCommand = new SortCommand("friend", ">=", "1");
        SortCommand sortSecondCommand = new SortCommand("friend", "!=", "1");

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand("friend", ">=", "1");
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
    }

    //Test case for sorting using tag value more than (numeric)
    public void execute_tagValue_moreThanOne() {
        String expectedMessage = SortCommand.constructSuccessMessage("friend", ">=", "1");
        SortCommand command = new SortCommand("friend", ">=", "1");
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BOB_CLONE), model.getFilteredPersonList());
    }

    //Test case for sorting using tag value equals (string comparison)
    @Test
    public void execute_tagValue_equalsHigh() {
        String expectedMessage = SortCommand.constructSuccessMessage("priority", "=", "high");
        SortCommand command = new SortCommand("priority", "=", "high");
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(AMY_CLONE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        SortCommand sortCommand = new SortCommand("priority", "=", "high");
        String expected = SortCommand.class.getCanonicalName() + "{tagName=priority, operator==, tagValue=high}";
        assertEquals(expected, sortCommand.toString());
    }
}
