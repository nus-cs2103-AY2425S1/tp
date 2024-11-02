package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SORT_SUCCESS;
import static seedu.address.testutil.testdata.TypicalDonors.DONOR_ALEX_20;
import static seedu.address.testutil.testdata.TypicalPersons.ALICE;
import static seedu.address.testutil.testdata.TypicalPersons.BENSON;
import static seedu.address.testutil.testdata.TypicalPersons.CARL;
import static seedu.address.testutil.testdata.TypicalVolunteers.VOLUNTEER_ALICE_5HOURS;
import static seedu.address.testutil.testdata.TypicalVolunteers.VOLUNTEER_BEN_10HOURS;
import static seedu.address.testutil.testdata.TypicalVolunteers.VOLUNTEER_CHARLIE_15HOURS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.SortOption;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AddressBookBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    /**
     * Initializes {@code model} and {@code expectedModel} with the given {@code AddressBook}.
     *
     * @param addressBook The {@code AddressBook} to initialize the models with.
     */
    private void setUp(AddressBook addressBook) {
        model = new ModelManager(addressBook, new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    /**
     * Executes the {@code SortCommand} with the given {@code sortOption} on the model initialized with
     * {@code unsortedAddressBook}, and asserts that the command was successful and the model matches
     * the expected model. The expected message is computed as the default success message.
     *
     * @param sortOption The {@code SortOption} to sort by.
     * @param unsortedAddressBook The {@code AddressBook} to initialize the models with.
     */
    private void assertSortCommandSuccess(SortOption sortOption, AddressBook unsortedAddressBook) {
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, sortOption);
        assertSortCommandSuccess(sortOption, expectedMessage, unsortedAddressBook);
    }

    /**
     * Executes the {@code SortCommand} with the given {@code sortOption} on the model initialized with
     * {@code unsortedAddressBook}, and asserts that the command was successful and the model matches
     * the expected model.
     *
     * @param sortOption The {@code SortOption} to sort by.
     * @param expectedMessage The expected message after executing the command.
     * @param unsortedAddressBook The {@code AddressBook} to initialize the models with.
     */
    private void assertSortCommandSuccess(SortOption sortOption, String expectedMessage,
                                          AddressBook unsortedAddressBook) {
        // Set up models
        setUp(unsortedAddressBook);

        // Update expected model to the sorted state
        expectedModel.updatePersonListSort(sortOption.getComparator());

        // Assert command success
        assertCommandSuccess(new SortCommand(sortOption), model, expectedMessage, expectedModel);
    }

    @Test
    public void executeSortByName_success() {
        SortOption sortOption = SortOption.NAME;
        AddressBook unsortedAddressBook = new AddressBookBuilder()
                .withPerson(ALICE)
                .withPerson(BENSON)
                .withPerson(CARL)
                .build();
        assertSortCommandSuccess(sortOption, unsortedAddressBook);
    }

    @Test
    public void executeSortByHours_noVolunteers_displaysNoVolunteersMessage() {
        // EP: No Volunteers
        SortOption sortOption = SortOption.HOURS;
        AddressBook addressBookWithoutVolunteers = new AddressBookBuilder()
                .withPerson(ALICE)
                .build();
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_BY_ROLE_CRITERIA_NONE_FOUND,
                sortOption.getRoleAsString());
        assertSortCommandSuccess(sortOption, expectedMessage, addressBookWithoutVolunteers);
    }

    @Test
    public void executeSortByDonatedAmount_noDonors_displaysNoDonorsMessage() {
        // EP: No Donors
        SortOption sortOption = SortOption.DONATION;
        AddressBook addressBookWithoutDonors = new AddressBookBuilder()
                .withPerson(ALICE)
                .build();
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_BY_ROLE_CRITERIA_NONE_FOUND,
                sortOption.getRoleAsString());
        assertSortCommandSuccess(sortOption, expectedMessage, addressBookWithoutDonors);
    }

    @Test
    public void executeSortByHours_onlyVolunteers_success() {
        // EP: Only Volunteers
        SortOption sortOption = SortOption.HOURS;
        AddressBook addressBookOnlyVolunteersUnsorted = new AddressBookBuilder()
                .withPerson(VOLUNTEER_BEN_10HOURS)
                .withPerson(VOLUNTEER_ALICE_5HOURS)
                .withPerson(VOLUNTEER_CHARLIE_15HOURS)
                .build();
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, sortOption);
        assertSortCommandSuccess(SortOption.HOURS, addressBookOnlyVolunteersUnsorted);
    }

    @Test
    public void executeSortByHours_mixedRoles_success() {
        // EP: Mix of Roles and Persons
        SortOption sortOption = SortOption.HOURS;
        AddressBook addressBookOnlyVolunteersUnsorted = new AddressBookBuilder()
                .withPerson(VOLUNTEER_BEN_10HOURS)
                .withPerson(BENSON)
                .withPerson(VOLUNTEER_ALICE_5HOURS)
                .withPerson(DONOR_ALEX_20)
                .withPerson(ALICE)
                .withPerson(VOLUNTEER_CHARLIE_15HOURS)
                .build();
        assertSortCommandSuccess(SortOption.HOURS, addressBookOnlyVolunteersUnsorted);
    }

    @Test
    public void equals() {
        SortCommand sortCommand1 = new SortCommand();
        SortCommand sortCommand2 = new SortCommand();

        assertEquals(sortCommand1, sortCommand2);

        SortOption sortOptionName = SortOption.NAME;
        SortCommand sortByNameCommand1 = new SortCommand(sortOptionName);
        SortCommand sortByNameCommand2 = new SortCommand(sortOptionName);

        // Same sort option -> returns true
        assertEquals(sortByNameCommand1, sortByNameCommand2);

        // Null -> returns false
        assertNotEquals(sortByNameCommand1, null);
    }
}
