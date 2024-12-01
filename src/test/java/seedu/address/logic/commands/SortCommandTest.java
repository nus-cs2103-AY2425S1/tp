package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import javafx.collections.transformation.SortedList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SortCommand sortByNameCommand = new SortCommand(SortCommand.SortType.NAME);
        SortCommand sortByAppointmentCommand = new SortCommand(SortCommand.SortType.APPOINTMENT);

        // same object -> returns true
        assertTrue(sortByNameCommand.equals(sortByNameCommand));

        // same values -> returns true
        SortCommand sortByNameCommandCopy = new SortCommand(SortCommand.SortType.NAME);
        assertTrue(sortByNameCommand.equals(sortByNameCommandCopy));

        // different types -> returns false
        assertFalse(sortByNameCommand.equals(1));

        // null -> returns false
        assertFalse(sortByNameCommand.equals(null));

        // different sort type -> returns false
        assertFalse(sortByNameCommand.equals(sortByAppointmentCommand));
    }

    @Test
    public void execute_sortByName_success() throws Exception {
        SortCommand sortByNameCommand = new SortCommand(SortCommand.SortType.NAME);
        CommandResult result = sortByNameCommand.execute(model);

        // Expected result message
        assertEquals(SortCommand.MESSAGE_SORTED_BY_NAME, result.getFeedbackToUser());

        // Verify that the list is sorted by name
        SortedList<Person> actualSortedList = model.getSortedPersonList();
        List<Person> expectedSortedList = model.getFilteredPersonList().stream()
                .sorted(Comparator.comparing(person -> person.getName().toString()))
                .collect(Collectors.toList());

        // Assert that the actual sorted list matches the expected sorted list
        assertEquals(expectedSortedList, actualSortedList);
    }

    @Test
    public void execute_sortByAppointment_success() throws Exception {
        SortCommand sortByAppointmentCommand = new SortCommand(SortCommand.SortType.APPOINTMENT);
        CommandResult result = sortByAppointmentCommand.execute(model);

        // Expected result message
        assertEquals(SortCommand.MESSAGE_SORTED_BY_APPOINTMENT, result.getFeedbackToUser());

        // Verify that the list is sorted by appointment
        SortedList<Person> actualSortedList = model.getSortedPersonList();
        List<Person> expectedSortedList = model.getFilteredPersonList().stream()
                .sorted(Comparator.comparing(
                        person -> person.getAppointment() == null ? null : person.getAppointment().value,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        // Assert that the actual sorted list matches the expected sorted list
        assertEquals(expectedSortedList, actualSortedList);
    }
}
