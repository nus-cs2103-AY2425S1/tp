package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_STATUS_ACTIVE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_STATUS_BLACKLISTED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_STATUS_OLD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_STATUS_POTENTIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_STATUS_UNRESPONSIVE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.WhitelistCommand.MESSAGE_CANNOT_BLACKLIST;
import static seedu.address.logic.commands.WhitelistCommand.MESSAGE_CANNOT_WHITELIST;
import static seedu.address.testutil.BlacklistedPersons.getBlacklistedAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.ArchivedAddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ClientStatus;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class WhitelistCommandTest {
    private Model normalModel;
    private Model blacklistedModel;
    @BeforeEach
    public void setUp() {
        normalModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        blacklistedModel = new ModelManager(getBlacklistedAddressBook(), new UserPrefs(),
                new ArchivedAddressBook());
        blacklistedModel.setArchivedListMode(false);
    }


    @Test
    public void whitelist_validPersonSetClientStatusActive_success() {
        Person firstPerson = blacklistedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        WhitelistCommand whitelistCommand = new WhitelistCommand(INDEX_FIRST_PERSON,
                new ClientStatus(VALID_CLIENT_STATUS_ACTIVE));

        Person whitelistedPerson = new PersonBuilder(firstPerson)
                .withClientStatus(VALID_CLIENT_STATUS_ACTIVE).build();

        Model expectedModel = new ModelManager(new AddressBook(blacklistedModel.getAddressBook()), new UserPrefs(),
                new ArchivedAddressBook());

        String expectedMessage = String.format(
                WhitelistCommand.MESSAGE_WHITELIST_PERSON_SUCCESS, Messages.format(whitelistedPerson));

        expectedModel.setPerson(expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                whitelistedPerson);

        assertCommandSuccess(whitelistCommand, blacklistedModel, expectedMessage, expectedModel);
    }

    @Test
    public void whitelist_validPersonSetClientStatusOld_success() {
        Person firstPerson = blacklistedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        WhitelistCommand whitelistCommand = new WhitelistCommand(INDEX_FIRST_PERSON,
                new ClientStatus(VALID_CLIENT_STATUS_OLD));

        Person whitelistedPerson = new PersonBuilder(firstPerson)
                .withClientStatus(VALID_CLIENT_STATUS_OLD).build();

        Model expectedModel = new ModelManager(new AddressBook(blacklistedModel.getAddressBook()), new UserPrefs(),
                new ArchivedAddressBook());

        String expectedMessage = String.format(
                WhitelistCommand.MESSAGE_WHITELIST_PERSON_SUCCESS, Messages.format(whitelistedPerson));

        expectedModel.setPerson(expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                whitelistedPerson);

        assertCommandSuccess(whitelistCommand, blacklistedModel, expectedMessage, expectedModel);
    }

    @Test
    public void whitelist_validPersonSetClientStatusUnresponsive_success() {
        Person firstPerson = blacklistedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        WhitelistCommand whitelistCommand = new WhitelistCommand(INDEX_FIRST_PERSON,
                new ClientStatus(VALID_CLIENT_STATUS_UNRESPONSIVE));

        Person whitelistedPerson = new PersonBuilder(firstPerson)
                .withClientStatus(VALID_CLIENT_STATUS_UNRESPONSIVE).build();

        Model expectedModel = new ModelManager(new AddressBook(blacklistedModel.getAddressBook()), new UserPrefs(),
                new ArchivedAddressBook());

        String expectedMessage = String.format(
                WhitelistCommand.MESSAGE_WHITELIST_PERSON_SUCCESS, Messages.format(whitelistedPerson));

        expectedModel.setPerson(expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                whitelistedPerson);

        assertCommandSuccess(whitelistCommand, blacklistedModel, expectedMessage, expectedModel);
    }

    @Test
    public void whitelist_validPersonSetClientStatusPotential_success() {
        Person firstPerson = blacklistedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        WhitelistCommand whitelistCommand = new WhitelistCommand(INDEX_FIRST_PERSON,
                new ClientStatus(VALID_CLIENT_STATUS_POTENTIAL));

        Person whitelistedPerson = new PersonBuilder(firstPerson)
                .withClientStatus(VALID_CLIENT_STATUS_POTENTIAL).build();

        Model expectedModel = new ModelManager(new AddressBook(blacklistedModel.getAddressBook()), new UserPrefs(),
                new ArchivedAddressBook());

        String expectedMessage = String.format(
                WhitelistCommand.MESSAGE_WHITELIST_PERSON_SUCCESS, Messages.format(whitelistedPerson));

        expectedModel.setPerson(expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                whitelistedPerson);

        assertCommandSuccess(whitelistCommand, blacklistedModel, expectedMessage, expectedModel);
    }

    @Test
    public void whitelist_validPersonSetClientStatusBlacklisted_failure() {
        WhitelistCommand whitelistCommand = new WhitelistCommand(INDEX_FIRST_PERSON,
                new ClientStatus(VALID_CLIENT_STATUS_BLACKLISTED));

        String expectedMessage = MESSAGE_CANNOT_BLACKLIST;

        assertCommandFailure(whitelistCommand, blacklistedModel, expectedMessage);
    }

    @Test
    public void whitelist_indexOutsideRange_failure() {
        Index outOfBoundIndex = Index.fromZeroBased(blacklistedModel.getFilteredPersonList().size());
        WhitelistCommand whitelistCommand = new WhitelistCommand(
                outOfBoundIndex, new ClientStatus(VALID_CLIENT_STATUS_ACTIVE));

        assertCommandFailure(whitelistCommand, blacklistedModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void whitelist_nonBlacklistedPerson_failure() {
        Person firstPerson = normalModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        WhitelistCommand whitelistCommand = new WhitelistCommand(INDEX_FIRST_PERSON,
                new ClientStatus(VALID_CLIENT_STATUS_ACTIVE));

        assertCommandFailure(whitelistCommand, normalModel,
                String.format(MESSAGE_CANNOT_WHITELIST, Messages.format(firstPerson)));
    }

    @Test
    public void execute_validIndexArchivedList_throwsCommandException() {
        Person personToAdd = blacklistedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        blacklistedModel.setArchivedListMode(true);
        WhitelistCommand whitelistCommand = new WhitelistCommand(INDEX_FIRST_PERSON,
                new ClientStatus(VALID_CLIENT_STATUS_POTENTIAL));

        assertCommandFailure(whitelistCommand, blacklistedModel, Messages.MESSAGE_NOT_IN_MAIN_LIST);
    }
}
