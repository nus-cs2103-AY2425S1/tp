package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.ReverseSortedTypicalPersons.getReverseSortedTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.UnsortedTypicalPersons.getUnsortedTypicalAddressBook;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class SortCommandTest {
    private Model sortedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model unSortedmodel = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs());
    private Model reverseSortedModel = new ModelManager(getReverseSortedTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortListWithoutModifyingModel_success() {
        SortCommand sortCommand = new SortCommand();

        Model expectedModel = new ModelManager(new AddressBook(unSortedmodel.getAddressBook()), new UserPrefs());
        expectedModel.updateSortedPersonList();

        assertCommandSuccess(sortCommand, unSortedmodel, SortCommand.MESSAGE_SORT_LIST_SUCCESS, expectedModel);

        // Checks that model remains unchanged
        assertEquals(unSortedmodel, expectedModel);

        List<Person> sortedList = unSortedmodel.getFilteredPersonList().stream()
                .sorted(Comparator.comparing(x -> x.getName().fullName))
                .toList();

        assertEquals(sortedList, unSortedmodel.getFilteredPersonList());
    }

    /**
     * Checks that function does not fail with an empty address book.
     */
    @Test
    public void execute_sortEmptyList_success() {
        SortCommand sortCommand = new SortCommand();

        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());

        // Expected model: empty list
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel.updateSortedPersonList();

        // checks that function does not fail when there is an empty address book
        assertCommandSuccess(sortCommand, emptyModel, SortCommand.MESSAGE_SORT_LIST_SUCCESS, expectedModel);
        assertEquals(emptyModel.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    /**
     * Checks if already sorted list remains sorted.
     */
    @Test
    public void execute_alreadySortedList_success() {
        SortCommand sortCommand = new SortCommand();

        // Expected model: already sorted
        Model expectedModel = new ModelManager(sortedModel.getAddressBook(), new UserPrefs());
        expectedModel.updateSortedPersonList();

        assertCommandSuccess(sortCommand, sortedModel, SortCommand.MESSAGE_SORT_LIST_SUCCESS, expectedModel);
        assertEquals(sortedModel.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    /**
     * Checks if a reverse-sorted list is correctly sorted by SortCommand.
     */
    @Test
    public void execute_reverseSortedList_success() {
        SortCommand sortCommand = new SortCommand();

        // Expected model: already sorted (desired result)
        Model expectedModel = new ModelManager(reverseSortedModel.getAddressBook(), new UserPrefs());
        expectedModel.updateSortedPersonList();

        List<Person> sortedList = reverseSortedModel.getFilteredPersonList().stream()
                .sorted(Comparator.comparing(x -> x.getName().fullName))
                .toList();

        // Apply sorting to reverseSortedModel via SortCommand
        assertCommandSuccess(sortCommand, reverseSortedModel, SortCommand.MESSAGE_SORT_LIST_SUCCESS, expectedModel);
        
        assertEquals(reverseSortedModel.getFilteredPersonList(), sortedList);
    }
}
