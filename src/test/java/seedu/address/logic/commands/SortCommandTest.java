package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getShuffledTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {
    private Model getShuffledModel() {
        return new ModelManager(getShuffledTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortByNameAscending_success() {
        Model model = getShuffledModel();
        SortCommand command = SortCommand.sortByName(SortCommand.ASCENDING);
        CommandResult result = command.execute(model);
        String expectedMessage = "Sorted by Name in Ascending order.";

        List<Person> sortedList = model.getFilteredPersonList();
        List<Person> expectedList = List.of(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedList, sortedList);
    }

    @Test
    public void execute_sortByNameDescending_success() {
        Model model = getShuffledModel();
        SortCommand command = SortCommand.sortByName(SortCommand.DESCENDING);
        CommandResult result = command.execute(model);
        String expectedMessage = "Sorted by Name in Descending order.";

        List<Person> sortedList = model.getFilteredPersonList();
        List<Person> expectedList = List.of(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedList, sortedList);
    }

    @Test
    public void execute_sortByStudentIdAscending_success() {
        Model model = getShuffledModel();
        SortCommand command = SortCommand.sortByStudentId(SortCommand.ASCENDING);
        CommandResult result = command.execute(model);
        String expectedMessage = "Sorted by Student Id in Ascending order.";

        List<Person> sortedList = model.getFilteredPersonList();
        List<Person> expectedList = List.of(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedList, sortedList);
    }

    @Test
    public void execute_sortByStudentIdDescending_success() {
        Model model = getShuffledModel();
        SortCommand command = SortCommand.sortByStudentId(SortCommand.DESCENDING);
        CommandResult result = command.execute(model);
        String expectedMessage = "Sorted by Student Id in Descending order.";

        List<Person> sortedList = model.getFilteredPersonList();
        List<Person> expectedList = List.of(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedList, sortedList);
    }

    @Test
    public void equals() {
        SortCommand sortByNameAsc = SortCommand.sortByName(SortCommand.ASCENDING);
        SortCommand sortByNameDesc = SortCommand.sortByName(SortCommand.DESCENDING);
        SortCommand sortByIdAsc = SortCommand.sortByStudentId(SortCommand.ASCENDING);

        // same object -> returns true
        assertTrue(sortByNameAsc.equals(sortByNameAsc));

        // same values -> returns true
        SortCommand sortByNameAscCopy = SortCommand.sortByName(SortCommand.ASCENDING);
        assertTrue(sortByNameAsc.equals(sortByNameAscCopy));

        // different types -> returns false
        assertFalse(sortByNameAsc.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(sortByNameAsc.equals(null));

        // different order -> returns false
        assertFalse(sortByNameAsc.equals(sortByNameDesc));

        // different field -> returns false
        assertFalse(sortByNameAsc.equals(sortByIdAsc));
    }

    @Test
    public void toStringMethod() {
        SortCommand sortCommand = SortCommand.sortByName(SortCommand.ASCENDING);
        String expectedString = "SortCommand[field=Name, order=Ascending]";
        assertEquals(expectedString, sortCommand.toString());

        sortCommand = SortCommand.sortByStudentId(SortCommand.DESCENDING);
        expectedString = "SortCommand[field=Student ID, order=Descending]";
        assertEquals(expectedString, sortCommand.toString());
    }
}
