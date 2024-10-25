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


    private final Comparator<Person> comparatorForName = Comparator.comparing(person
            -> person.getName().fullName);

    private final Comparator<Person> comparatorForRole = Comparator.comparing(person
            -> person.getRole().roleName);

    private final Comparator<Person> comparatorForPhone = Comparator.comparing(person
            -> person.getPhone().value);

    private final Comparator<Person> comparatorForEmail = Comparator.comparing(person
            -> person.getEmail().value);

    private final Comparator<Person> comparatorForAddress = Comparator.comparing(person
            -> person.getAddress().value);

    @Test
    public void execute_sortListWithoutModifyingModel_success() {
        SortCommand sortCommand = new SortCommand("name", comparatorForName);

        Model expectedModel = new ModelManager(new AddressBook(unSortedmodel.getAddressBook()), new UserPrefs());
        expectedModel.updateSortedPersonList(comparatorForName);

        assertCommandSuccess(sortCommand, unSortedmodel, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, "name"),
                expectedModel);

        // Checks that model remains unchanged
        assertEquals(unSortedmodel, expectedModel);

        List<Person> sortedList = unSortedmodel.getFilteredPersonList().stream()
                .sorted(Comparator.comparing(x -> x.getName().fullName))
                .toList();

        assertEquals(sortedList, unSortedmodel.getFilteredPersonList());
    }
    @Test
    public void execute_sortListByName_success() {
        SortCommand sortCommand = new SortCommand("name", comparatorForName);

        Model expectedModel = new ModelManager(new AddressBook(unSortedmodel.getAddressBook()), new UserPrefs());
        expectedModel.updateSortedPersonList(comparatorForName);

        assertCommandSuccess(sortCommand, unSortedmodel, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, "name"),
                expectedModel);

        // Checks that model remains unchanged
        assertEquals(unSortedmodel, expectedModel);

        List<Person> sortedList = unSortedmodel.getFilteredPersonList().stream()
                .sorted(comparatorForName)
                .toList();

        assertEquals(sortedList, unSortedmodel.getFilteredPersonList());
    }

    @Test
    public void execute_sortListByRole_success() {
        SortCommand sortCommand = new SortCommand("role", comparatorForRole);

        Model expectedModel = new ModelManager(new AddressBook(unSortedmodel.getAddressBook()), new UserPrefs());
        expectedModel.updateSortedPersonList(comparatorForRole);

        assertCommandSuccess(sortCommand, unSortedmodel, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, "role"),
                expectedModel);

        // Checks that model remains unchanged
        assertEquals(unSortedmodel, expectedModel);

        List<Person> sortedList = unSortedmodel.getFilteredPersonList().stream()
                .sorted(comparatorForRole)
                .toList();

        assertEquals(sortedList, unSortedmodel.getFilteredPersonList());
    }

    @Test
    public void execute_sortListByPhone_success() {
        SortCommand sortCommand = new SortCommand("phone", comparatorForPhone);

        Model expectedModel = new ModelManager(new AddressBook(unSortedmodel.getAddressBook()), new UserPrefs());
        expectedModel.updateSortedPersonList(comparatorForPhone);

        assertCommandSuccess(sortCommand, unSortedmodel, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, "phone"),
                expectedModel);

        // Checks that model remains unchanged
        assertEquals(unSortedmodel, expectedModel);

        List<Person> sortedList = unSortedmodel.getFilteredPersonList().stream()
                .sorted(comparatorForPhone)
                .toList();

        assertEquals(sortedList, unSortedmodel.getFilteredPersonList());
    }

    @Test
    public void execute_sortListByEmail_success() {
        SortCommand sortCommand = new SortCommand("email", comparatorForEmail);

        Model expectedModel = new ModelManager(new AddressBook(unSortedmodel.getAddressBook()), new UserPrefs());
        expectedModel.updateSortedPersonList(comparatorForEmail);

        assertCommandSuccess(sortCommand, unSortedmodel, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, "email"),
                expectedModel);

        // Checks that model remains unchanged
        assertEquals(unSortedmodel, expectedModel);

        List<Person> sortedList = unSortedmodel.getFilteredPersonList().stream()
                .sorted(comparatorForEmail)
                .toList();

        assertEquals(sortedList, unSortedmodel.getFilteredPersonList());
    }

    @Test
    public void execute_sortListByAddress_success() {
        SortCommand sortCommand = new SortCommand("address", comparatorForAddress);

        Model expectedModel = new ModelManager(new AddressBook(unSortedmodel.getAddressBook()), new UserPrefs());
        expectedModel.updateSortedPersonList(comparatorForAddress);

        assertCommandSuccess(sortCommand, unSortedmodel, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, "address"),
                expectedModel);

        // Checks that model remains unchanged
        assertEquals(unSortedmodel, expectedModel);

        List<Person> sortedList = unSortedmodel.getFilteredPersonList().stream()
                .sorted(comparatorForAddress)
                .toList();

        assertEquals(sortedList, unSortedmodel.getFilteredPersonList());
    }

    /**
     * Checks that function does not fail with an empty address book.
     */
    @Test
    public void execute_sortEmptyList_success() {
        SortCommand sortCommand = new SortCommand("name", comparatorForName);

        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());

        // Expected model: empty list
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel.updateSortedPersonList(comparatorForName);

        // checks that function does not fail when there is an empty address book
        assertCommandSuccess(sortCommand, emptyModel, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, "name"),
                expectedModel);
        assertEquals(emptyModel.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    /**
     * Checks if already sorted list remains sorted.
     */
    @Test
    public void execute_alreadySortedList_success() {
        SortCommand sortCommand = new SortCommand("name", comparatorForName);

        // Expected model: already sorted
        Model expectedModel = new ModelManager(sortedModel.getAddressBook(), new UserPrefs());
        expectedModel.updateSortedPersonList(comparatorForName);

        assertCommandSuccess(sortCommand, sortedModel, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, "name"),
                expectedModel);
        assertEquals(sortedModel.getFilteredPersonList(), expectedModel.getFilteredPersonList());
    }

    /**
     * Checks if a reverse-sorted list is correctly sorted by SortCommand.
     */
    @Test
    public void execute_reverseSortedList_success() {
        SortCommand sortCommand = new SortCommand("name", comparatorForName);

        // Expected model: already sorted (desired result)
        Model expectedModel = new ModelManager(reverseSortedModel.getAddressBook(), new UserPrefs());
        expectedModel.updateSortedPersonList(comparatorForName);

        List<Person> sortedList = reverseSortedModel.getFilteredPersonList().stream()
                .sorted(comparatorForName)
                .toList();

        // Apply sorting to reverseSortedModel via SortCommand
        assertCommandSuccess(sortCommand, reverseSortedModel, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, "name"),
                expectedModel);
        assertEquals(reverseSortedModel.getFilteredPersonList(), sortedList);
    }
}