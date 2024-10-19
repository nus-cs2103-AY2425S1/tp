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

public class AdvFilterCommandTest {
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
        AdvFilterCommand sortFirstCommand = new AdvFilterCommand("friend", ">=", "1");
        AdvFilterCommand sortSecondCommand = new AdvFilterCommand("friend", "!=", "1");

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        AdvFilterCommand sortFirstCommandCopy = new AdvFilterCommand("friend", ">=", "1");
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
        String expectedMessage = AdvFilterCommand.constructSuccessMessage("friend", ">=", "1");
        AdvFilterCommand command = new AdvFilterCommand("friend", ">=", "1");
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BOB_CLONE), model.getFilteredPersonList());
    }

    //Test case for sorting using tag value equals (string comparison)
    @Test
    public void execute_tagValue_equalsHigh() {
        String expectedMessage = AdvFilterCommand.constructSuccessMessage("priority", "=", "high");
        AdvFilterCommand command = new AdvFilterCommand("priority", "=", "high");
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(AMY_CLONE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        AdvFilterCommand AdvFilterCommand = new AdvFilterCommand("priority", "=", "high");
        String expected = AdvFilterCommand.class.getCanonicalName() + "{tagName=priority, operator==, tagValue=high}";
        assertEquals(expected, AdvFilterCommand.toString());
    }
}
