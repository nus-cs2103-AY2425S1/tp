package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = model;

    @Test
    public void execute_sortByNameAsc_success() {
        SortCommand sortCommand = new SortCommand("n/", "asc");
        Comparator<Person> comparator = Person.getNameComparator();
        expectedModel.sortPersonList(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name", "asc");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByNameDesc_success() {
        SortCommand sortCommand = new SortCommand("n/", "desc");
        Comparator<Person> comparator = Person.getNameComparator().reversed();
        expectedModel.sortPersonList(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name", "desc");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByAppointmentDateAsc_success() {
        SortCommand sortCommand = new SortCommand("appt/", "asc");
        Comparator<Person> comparator = Person.getAppointmentDateComparator();
        expectedModel.sortPersonList(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "appointment date", "asc");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByAppointmentDateDesc_success() {
        SortCommand sortCommand = new SortCommand("appt/", "desc");
        Comparator<Person> comparator = Person.getAppointmentDateComparator().reversed();
        expectedModel.sortPersonList(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "appointment date", "desc");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByBirthdayAsc_success() {
        SortCommand sortCommand = new SortCommand("b/", "asc");
        Comparator<Person> comparator = Person.getBirthdayComparator();
        expectedModel.sortPersonList(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "birthday", "asc");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByBirthdayDesc_success() {
        SortCommand sortCommand = new SortCommand("b/", "desc");
        Comparator<Person> comparator = Person.getBirthdayComparator().reversed();
        expectedModel.sortPersonList(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "birthday", "desc");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByPayDateAsc_success() {
        SortCommand sortCommand = new SortCommand("paydate/", "asc");
        Comparator<Person> comparator = Person.getPayDateComparator();
        expectedModel.sortPersonList(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "next payment date", "asc");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByPayDateDesc_success() {
        SortCommand sortCommand = new SortCommand("paydate/", "desc");
        Comparator<Person> comparator = Person.getReversedPayDateComparator();
        expectedModel.sortPersonList(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "next payment date", "desc");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidParameter_throwsCommandException() {
        SortCommand sortCommand = new SortCommand("invalid", "asc");
        assertCommandFailure(sortCommand, model, SortCommand.MESSAGE_INVALID_PARAMETER);
    }

    @Test
    public void execute_invalidOrder_throwsCommandException() {
        SortCommand sortCommand = new SortCommand("n/", "invalid");
        assertCommandFailure(sortCommand, model, SortCommand.MESSAGE_INVALID_ORDER);
    }

    @Test
    public void equals() {
        SortCommand sortByNameAsc = new SortCommand("n/", "asc");
        SortCommand sortByNameDesc = new SortCommand("n/", "desc");

        // same object -> returns true
        assertTrue(sortByNameAsc.equals(sortByNameAsc));

        // same values -> returns true
        SortCommand sortByNameAscCopy = new SortCommand("n/", "asc");
        assertTrue(sortByNameAsc.equals(sortByNameAscCopy));

        // different types -> returns false
        assertFalse(sortByNameAsc.equals(1));

        // null -> returns false
        assertFalse(sortByNameAsc.equals(null));

        // different order -> returns false
        assertFalse(sortByNameAsc.equals(sortByNameDesc));
    }

    @Test
    public void toStringMethod() {
        SortCommand sortCommand = new SortCommand("n/", "asc");
        String expected = SortCommand.class.getCanonicalName() + "{parameter=n/, order=asc}";
        assertEquals(expected, sortCommand.toString());
    }
}
