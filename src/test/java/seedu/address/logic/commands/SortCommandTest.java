package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BETTY;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.CLAIRE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getShuffledTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getShuffledTypicalAddressBookWithTutorials;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL_ONE;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL_TWO;

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

    private Model getShuffledModelWithTutorials() {
        return new ModelManager(getShuffledTypicalAddressBookWithTutorials(), new UserPrefs());
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
        String expectedMessage = "Sorted by Student ID in Ascending order.";

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
        String expectedMessage = "Sorted by Student ID in Descending order.";

        List<Person> sortedList = model.getFilteredPersonList();
        List<Person> expectedList = List.of(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedList, sortedList);
    }

    @Test
    public void execute_sortByTutorialAttendanceAscending_success() {
        Model model = getShuffledModelWithTutorials();
        SortCommand command = SortCommand.sortByTutorialAttendance(SortCommand.ASCENDING, TUTORIAL_ONE);
        CommandResult result = command.execute(model);
        String expectedMessage = "Sorted by Tutorial Attendance in Ascending order.";

        List<Person> sortedList = model.getFilteredPersonList();
        List<Person> expectedList = List.of(ADAM, BETTY, CLAIRE);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedList, sortedList);
    }

    @Test
    public void execute_sortByTutorialAttendanceDescending_success() {
        Model model = getShuffledModelWithTutorials();
        SortCommand command = SortCommand.sortByTutorialAttendance(SortCommand.DESCENDING, TUTORIAL_ONE);
        CommandResult result = command.execute(model);
        String expectedMessage = "Sorted by Tutorial Attendance in Descending order.";

        List<Person> sortedList = model.getFilteredPersonList();
        List<Person> expectedList = List.of(BETTY, ADAM, CLAIRE);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedList, sortedList);
    }

    @Test
    public void equals() {
        SortCommand sortByNameAsc = SortCommand.sortByName(SortCommand.ASCENDING);
        SortCommand sortByNameDesc = SortCommand.sortByName(SortCommand.DESCENDING);
        SortCommand sortByIdAsc = SortCommand.sortByStudentId(SortCommand.ASCENDING);
        SortCommand sortByTutorialOneAsc = SortCommand.sortByTutorialAttendance(SortCommand.ASCENDING, TUTORIAL_ONE);
        SortCommand sortByTutorialTwoAsc = SortCommand.sortByTutorialAttendance(SortCommand.ASCENDING, TUTORIAL_TWO);

        // same object -> returns true
        assertTrue(sortByNameAsc.equals(sortByNameAsc));

        // same values -> returns true
        SortCommand sortByNameAscCopy = SortCommand.sortByName(SortCommand.ASCENDING);
        assertTrue(sortByNameAsc.equals(sortByNameAscCopy));

        SortCommand sortByIdAscCopy = SortCommand.sortByStudentId(SortCommand.ASCENDING);
        assertTrue(sortByIdAsc.equals(sortByIdAscCopy));

        SortCommand sortByTutorialOneAscCopy =
                SortCommand.sortByTutorialAttendance(SortCommand.ASCENDING, TUTORIAL_ONE);
        assertTrue(sortByTutorialOneAsc.equals(sortByTutorialOneAscCopy));

        // different types -> returns false
        assertFalse(sortByNameAsc.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(sortByNameAsc.equals(null));

        // different order -> returns false
        assertFalse(sortByNameAsc.equals(sortByNameDesc));

        // different field -> returns false
        assertFalse(sortByNameAsc.equals(sortByIdAsc));

        // different tutorial -> returns false
        assertFalse(sortByTutorialOneAsc.equals(sortByTutorialTwoAsc));
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
