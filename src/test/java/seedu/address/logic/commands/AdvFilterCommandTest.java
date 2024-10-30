package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import seedu.address.logic.commands.AdvFilterCommand.Operator;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

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
        AdvFilterCommand sortFirstCommand = new AdvFilterCommand("friend", Operator.GREATER_THAN_OR_EQUAL, "1");
        AdvFilterCommand sortSecondCommand = new AdvFilterCommand("friend", Operator.NOT_EQUAL, "1");

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        AdvFilterCommand sortFirstCommandCopy = new AdvFilterCommand("friend", Operator.GREATER_THAN_OR_EQUAL, "1");
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
        AdvFilterCommand command = new AdvFilterCommand("friend", Operator.GREATER_THAN_OR_EQUAL, "1");
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(BOB_CLONE), model.getFilteredPersonList());
    }

    //Test case for sorting using tag value equals (string comparison)
    @Test
    public void execute_tagValue_equalsHigh() {
        String expectedMessage = AdvFilterCommand.constructSuccessMessage("priority", "=", "high");
        AdvFilterCommand command = new AdvFilterCommand("priority", Operator.EQUAL, "high");
        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(Arrays.asList(AMY_CLONE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        AdvFilterCommand command = new AdvFilterCommand("priority", Operator.EQUAL, "high");
        String expected = AdvFilterCommand.class.getCanonicalName() + "{tagName=priority, operator==, tagValue=high}";
        assertEquals(expected, command.toString());
    }

    @Test
    public void compareTest() {
        Tag numericTag = new Tag("priority", "5");
        Tag stringTag = new Tag("priority", "high");
        AdvFilterCommand command = new AdvFilterCommand("priority", Operator.EQUAL, "high");

        // Test for '='
        assertTrue(command.compare(Operator.EQUAL, stringTag, "high"));
        assertFalse(command.compare(Operator.EQUAL, stringTag, "low"));

        // Test for '!='
        assertTrue(command.compare(Operator.NOT_EQUAL, stringTag, "low"));

        // Test for '>'
        assertTrue(command.compare(Operator.GREATER_THAN, numericTag, "4"));
        assertFalse(command.compare(Operator.GREATER_THAN, numericTag, "6"));

        // Test for '<'
        assertTrue(command.compare(Operator.LESS_THAN, numericTag, "6"));
        assertFalse(command.compare(Operator.LESS_THAN, numericTag, "4"));

        // Test for '>='
        assertTrue(command.compare(Operator.GREATER_THAN_OR_EQUAL, numericTag, "5"));
        assertFalse(command.compare(Operator.GREATER_THAN_OR_EQUAL, numericTag, "6"));

        // Test for '<='
        assertTrue(command.compare(Operator.LESS_THAN_OR_EQUAL, numericTag, "5"));
        assertFalse(command.compare(Operator.LESS_THAN_OR_EQUAL, numericTag, "4"));
    }

    @Test
    public void tryParseDoubleTest() {
        AdvFilterCommand command = new AdvFilterCommand("priority", Operator.EQUAL, "high");

        // Valid double
        assertEquals(5.0, command.tryParseDouble("5"));
        // assertNull is used because tryParseDouble either returns a Double or null
        // Invalid double
        assertNull(command.tryParseDouble("high"));

        // Edge case: Empty string
        assertNull(command.tryParseDouble(""));
    }

    @Test
    public void execute_noContactsFound() {
        AdvFilterCommand command = new AdvFilterCommand("priority", Operator.EQUAL, "nonexistent");
        CommandResult result = command.execute(model);

        assertEquals(AdvFilterCommand.MESSAGE_NO_CONTACT_FOUND, result.getFeedbackToUser());
        assertTrue(model.getFilteredPersonList().isEmpty());
    }



}
