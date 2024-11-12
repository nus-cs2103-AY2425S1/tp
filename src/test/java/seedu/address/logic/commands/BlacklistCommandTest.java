package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_STATUS_BLACKLISTED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class BlacklistCommandTest {

    private Model model;
    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        model.setArchivedListMode(false);
    }

    @Test
    public void blacklist_firstPerson_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        BlacklistCommand blacklistCommand = new BlacklistCommand(INDEX_FIRST_PERSON);

        Person blacklistedPerson = new PersonBuilder(firstPerson)
                .withClientStatus(VALID_CLIENT_STATUS_BLACKLISTED).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new ArchivedAddressBook());

        String expectedMessage = String.format(
                BlacklistCommand.MESSAGE_BLACKLIST_PERSON_SUCCESS, Messages.format(blacklistedPerson));

        expectedModel.setPerson(expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                blacklistedPerson);

        assertCommandSuccess(blacklistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void blacklist_lastPerson_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        BlacklistCommand blacklistCommand = new BlacklistCommand(indexLastPerson);

        Person blacklistedPerson = new PersonBuilder(lastPerson)
                .withClientStatus(VALID_CLIENT_STATUS_BLACKLISTED).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new ArchivedAddressBook());

        String expectedMessage = String.format(
                BlacklistCommand.MESSAGE_BLACKLIST_PERSON_SUCCESS, Messages.format(blacklistedPerson));

        expectedModel.setPerson(expectedModel.getFilteredPersonList().get(indexLastPerson.getZeroBased()),
                blacklistedPerson);

        assertCommandSuccess(blacklistCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void blacklist_indexOutsideRange_failure() {
        Index outOfBoundIndex = Index.fromZeroBased(model.getFilteredPersonList().size());
        BlacklistCommand blacklistCommand = new BlacklistCommand(outOfBoundIndex);

        assertCommandFailure(blacklistCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexArchivedList_throwsCommandException() {
        Person personToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.setArchivedListMode(true);
        model.addArchivedPerson(personToAdd);
        BlacklistCommand blacklistCommand = new BlacklistCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(blacklistCommand, model, Messages.MESSAGE_NOT_IN_MAIN_LIST);
    }
}
