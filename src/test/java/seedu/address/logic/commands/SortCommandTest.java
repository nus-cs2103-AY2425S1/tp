package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SORT_BY_ROLE_CRITERIA_NONE_FOUND;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SORT_SUCCESS;
import static seedu.address.testutil.testdata.TypicalDonors.DONOR_ALEX_20;
import static seedu.address.testutil.testdata.TypicalDonors.DONOR_BEN_50;
import static seedu.address.testutil.testdata.TypicalDonors.DONOR_CHARLIE_100;
import static seedu.address.testutil.testdata.TypicalPartners.PARTNER_BILL_UNTIL_20240315;
import static seedu.address.testutil.testdata.TypicalPartners.PARTNER_JANE_UNTIL_20220630;
import static seedu.address.testutil.testdata.TypicalPartners.PARTNER_JOHN_UNTIL_20231231;
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
    public void assertSortByName_success() {
        SortOption sortOption = SortOption.NAME;
        AddressBook unsortedAddressBook = new AddressBookBuilder()
                .withPerson(ALICE)
                .withPerson(BENSON)
                .withPerson(CARL)
                .build();
        assertSortCommandSuccess(sortOption, unsortedAddressBook);
    }

    @Test
    public void assertSortByRole_noPersonsOfRole_displaysNoRoleMessage() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).build();
        // EP: no Volunteers
        assertSortByRole_noPersonsOfRole_displaysNoRoleMessage(SortOption.HOURS, addressBook);

        // EP: no Donors
        assertSortByRole_noPersonsOfRole_displaysNoRoleMessage(SortOption.DONATION, addressBook);

        // EP: no Partners
        assertSortByRole_noPersonsOfRole_displaysNoRoleMessage(SortOption.DONATION, addressBook);
    }

    /**
     * Executes the {@code SortCommand} for the given {@code sortOption} on the model initialized with
     * {@code addressBook}, and asserts that the appropriate 'no persons of role' message is displayed.
     *
     * @param sortOption The {@code SortOption} representing the role to sort by.
     * @param addressBook The {@code AddressBook} to initialize the models with.
     */
    private void assertSortByRole_noPersonsOfRole_displaysNoRoleMessage(SortOption sortOption,
                                                                        AddressBook addressBook) {
        String expectedMessage = String.format(MESSAGE_SORT_BY_ROLE_CRITERIA_NONE_FOUND,
                sortOption.getRoleAsString());
        assertSortCommandSuccess(sortOption, expectedMessage, addressBook);
    }

    @Test
    public void assertSortByRole_onlyPersonsOfRole_success() {
        // EP: Only Volunteers
        AddressBook addressBookOnlyVolunteersUnsorted = new AddressBookBuilder()
                .withPerson(VOLUNTEER_BEN_10HOURS)
                .withPerson(VOLUNTEER_ALICE_5HOURS)
                .withPerson(VOLUNTEER_CHARLIE_15HOURS)
                .build();
        assertSortCommandSuccess(SortOption.HOURS, addressBookOnlyVolunteersUnsorted);

        // EP: Only Donors
        AddressBook addressBookOnlyDonorsUnsorted = new AddressBookBuilder()
                .withPerson(DONOR_ALEX_20)
                .withPerson(DONOR_BEN_50)
                .withPerson(DONOR_CHARLIE_100)
                .build();
        assertSortCommandSuccess(SortOption.DONATION, addressBookOnlyDonorsUnsorted);

        // EP: Only Partners
        AddressBook addressBookOnlyPartnersUnsorted = new AddressBookBuilder()
                .withPerson(PARTNER_JOHN_UNTIL_20231231)
                .withPerson(PARTNER_BILL_UNTIL_20240315)
                .withPerson(PARTNER_JANE_UNTIL_20220630)
                .build();
        assertSortCommandSuccess(SortOption.PARTNERSHIP_END_DATE, addressBookOnlyPartnersUnsorted);
    }

    @Test
    public void assertSortByRole_mixedRoles_success() {
        // AddressBook with a mix of Roles
        AddressBook addressBook = new AddressBookBuilder()
                .withPerson(PARTNER_JANE_UNTIL_20220630)
                .withPerson(VOLUNTEER_BEN_10HOURS)
                .withPerson(BENSON)
                .withPerson(DONOR_CHARLIE_100)
                .withPerson(PARTNER_BILL_UNTIL_20240315)
                .withPerson(VOLUNTEER_ALICE_5HOURS)
                .withPerson(DONOR_ALEX_20)
                .withPerson(ALICE)
                .withPerson(VOLUNTEER_CHARLIE_15HOURS)
                .build();

        // EP: Volunteers sorted correctly
        assertSortCommandSuccess(SortOption.HOURS, addressBook);

        // EP: Donors sorted correctly
        assertSortCommandSuccess(SortOption.DONATION, addressBook);

        // EP Partners sorted correctly
        assertSortCommandSuccess(SortOption.PARTNERSHIP_END_DATE, addressBook);
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
