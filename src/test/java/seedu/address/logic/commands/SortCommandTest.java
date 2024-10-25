package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new ArrayList<>(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new ArrayList<>(), new UserPrefs());

    @Test
    public void execute_name_sortInAlphabeticalOrder() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name");

        SortCommand command = new SortCommand("name");
        expectedModel.updateSortingOrder(Comparator.comparing(person -> person.getName().fullName));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_address_sortInAlphaNumericalOrder() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "address");

        SortCommand command = new SortCommand("address");
        expectedModel.updateSortingOrder(Comparator.comparing(person -> person.getAddress().value));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, ALICE, BENSON, GEORGE, FIONA, ELLE, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_priority_highToLow() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "priority");

        SortCommand command = new SortCommand("priority");
        expectedModel.updateSortingOrder(Comparator.comparing(Person::getPriority));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_income_sortInAscendingOrder() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "income");

        SortCommand command = new SortCommand("income");
        expectedModel.updateSortingOrder(Comparator.comparing(person -> person.getIncome().getValue()));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, ALICE, GEORGE, FIONA, DANIEL, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void equals() {
        SortCommand firstCommand = new SortCommand("name");
        SortCommand secondCommand = new SortCommand("address");

        // same object returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values returns true
        SortCommand firstCommandCopy = new SortCommand("name");
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types returns false
        assertFalse(firstCommand.equals(1));

        // null returns false
        assertFalse(firstCommand.equals(null));

        // different sorting criteria returns false
        assertFalse(firstCommand.equals(secondCommand));
    }
}
