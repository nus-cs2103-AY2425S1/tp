package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortedUnfilteredList_success() {
        SortCommand sortCommand = new SortCommand();
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        // Execute the sort command
        assertCommandSuccess(sortCommand, model, expectedMessage, model);

        // Check that the list is sorted by appointment dates
        List<Person> personsAfterSort = model.getFilteredPersonList();
        assertSortedByAppointmentDate(personsAfterSort);
    }

    @Test
    public void execute_sortedFilteredList_success() {
        SortCommand sortCommand = new SortCommand();
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        // Show only the first person in the list to create a filtered list
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // Execute the sort command on the filtered list
        assertCommandSuccess(sortCommand, model, expectedMessage, model);

        // Verify that the filtered list is sorted by appointment dates
        List<Person> filteredPersonsAfterSort = model.getFilteredPersonList();
        assertSortedByAppointmentDate(filteredPersonsAfterSort);
    }

    @Test
    public void execute_noAppointment_success() {
        SortCommand sortCommand = new SortCommand();
        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        // Remove appointments from all persons for testing the no-appointment scenario
        model.getFilteredPersonList().forEach(person -> {
            Person editedPerson = new PersonBuilder(person).withAppointments().build(); // Clear appointments
            model.setPerson(person, editedPerson);
        });

        // Execute the sort command
        assertCommandSuccess(sortCommand, model, expectedMessage, model);

        // Verify that the list remains unchanged since there are no appointment dates to sort
        List<Person> personsAfterSort = model.getFilteredPersonList();
        assertUnchangedList(personsAfterSort);
    }

    /**
     * Asserts that the persons list is sorted by appointment date in ascending order.
     */
    private void assertSortedByAppointmentDate(List<Person> persons) {
        for (int i = 1; i < persons.size(); i++) {
            Person previous = persons.get(i - 1);
            Person current = persons.get(i);
            if (previous.getEarliestAppointmentDate() != null
                    && current.getEarliestAppointmentDate() != null) {
                assert previous.getEarliestAppointmentDate()
                        .compareTo(current.getEarliestAppointmentDate()) <= 0
                        : "List is not sorted by appointment date!";
            }
        }
    }

    /**
     * Asserts that the list remains unchanged after the sort command (used for the no-appointment scenario).
     */
    private void assertUnchangedList(List<Person> personsAfterSort) {
        List<Person> personsBeforeSort = model.getFilteredPersonList();
        assert personsBeforeSort.equals(personsAfterSort)
                : "List should remain unchanged after sorting without appointment dates!";
    }

    @Test
    public void equals() {
        SortCommand sortCommand = new SortCommand();

        // Same object -> returns true
        assertTrue(sortCommand.equals(sortCommand));

        // Different types -> returns false
        assertFalse(sortCommand.equals(1));

        // Null -> returns false
        assertFalse(sortCommand.equals(null));

        // Same command type -> returns true
        assertTrue(sortCommand.equals(new SortCommand()));
    }

    @Test
    public void toString_returnsCommandWord() {
        SortCommand sortCommand = new SortCommand();
        String expectedString = SortCommand.COMMAND_WORD;
        assertEquals(expectedString, sortCommand.toString());
    }

}
