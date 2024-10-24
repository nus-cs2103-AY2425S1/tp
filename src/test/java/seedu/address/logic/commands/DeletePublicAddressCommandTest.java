package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.addresses.BtcAddress;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePublicAddressCommand}.
 */
public class DeletePublicAddressCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_validIndexValidNetwork_success() throws Exception {
        Person personToDeleteAddress = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeletePublicAddressCommand deletePublicAddressCommand =
                new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person updatedPerson = new PersonBuilder(personToDeleteAddress).build();
        updatedPerson.setPublicAddressesByNetwork(Network.BTC, new HashSet<>());
        expectedModel.setPerson(personToDeleteAddress, updatedPerson);

        String expectedMessage = String.format(DeletePublicAddressCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                updatedPerson.getPublicAddresses());

        assertCommandSuccess(deletePublicAddressCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexValidNetworkValidLabel_success() throws Exception {
        Person personToDeleteAddress = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        HashSet<PublicAddress> addresses = new HashSet<>(
                personToDeleteAddress.getPublicAddressesByNetwork(Network.BTC)
        );
        addresses.add(new BtcAddress("12345", "test2"));
        addresses.add(new BtcAddress("12345", "test"));

        personToDeleteAddress.setPublicAddressesByNetwork(Network.BTC, new HashSet<>(addresses));
        DeletePublicAddressCommand deletePublicAddressCommand =
                new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC, "test");

        addresses.remove(new BtcAddress("12345", "test"));
        Person updatedPerson = new PersonBuilder(personToDeleteAddress).build();
        updatedPerson.setPublicAddressesByNetwork(Network.BTC, new HashSet<>(addresses));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToDeleteAddress, updatedPerson);

        String expectedMessage = String.format(DeletePublicAddressCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                updatedPerson.getPublicAddresses());

        assertCommandSuccess(deletePublicAddressCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePublicAddressCommand deletePublicAddressCommand =
                new DeletePublicAddressCommand(outOfBoundIndex, Network.BTC);

        assertCommandFailure(deletePublicAddressCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeletePublicAddressCommand deleteFirstCommand = new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC);
        DeletePublicAddressCommand deleteSecondCommand =
                new DeletePublicAddressCommand(Index.fromOneBased(2), Network.BTC);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeletePublicAddressCommand deleteFirstCommandCopy =
                new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }


}
