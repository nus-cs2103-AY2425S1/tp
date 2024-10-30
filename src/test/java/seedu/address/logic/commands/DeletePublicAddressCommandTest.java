package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.addresses.Network;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePublicAddressCommand}.
 */
public class DeletePublicAddressCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    //    @Test
    //    public void execute_validIndexValidNetwork_success() throws Exception {
    //        Person personToDeleteAddress = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        DeletePublicAddressCommand deletePublicAddressCommand =
    //            new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC);
    //
    //        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    //        Person updatedPerson = new PersonBuilder(personToDeleteAddress).build();
    //        updatedPerson.addPublicAddressToNetwork(Network.BTC, new HashSet<>());
    //        expectedModel.setPerson(personToDeleteAddress, updatedPerson);
    //
    //        String expectedMessage = String.format(DeletePublicAddressCommand.MESSAGE_DELETE_PERSON_SUCCESS,
    //            updatedPerson.getPublicAddressesComposition());
    //
    //        assertCommandSuccess(deletePublicAddressCommand, model, expectedMessage, expectedModel);
    //    }

    //    @Test
    //    public void execute_validIndexValidNetworkValidLabel1_success() throws Exception {
    //        //disabled this test as get filtered list returns a person that is modifies the original person in model
    //        Person personToDeleteAddress = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //        HashSet<PublicAddress> addresses = new HashSet<>(
    //            personToDeleteAddress.getPublicAddressesByNetwork(Network.BTC)
    //        );
    //        addresses.add(new BtcAddress("12345", "test2"));
    //        addresses.add(new BtcAddress("12345", "test"));
    //
    //        personToDeleteAddress.addPublicAddressToNetwork(Network.BTC, new HashSet<>(addresses));
    //        DeletePublicAddressCommand deletePublicAddressCommand =
    //            new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC, "test");
    //
    //        addresses.remove(new BtcAddress("12345", "test"));
    //        Person updatedPerson = new PersonBuilder(personToDeleteAddress).build();
    //        updatedPerson.addPublicAddressToNetwork(Network.BTC, new HashSet<>(addresses));
    //        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    //        expectedModel.setPerson(personToDeleteAddress, updatedPerson);
    //
    //        String expectedMessage = String.format(DeletePublicAddressCommand.MESSAGE_DELETE_PERSON_SUCCESS,
    //            updatedPerson.getPublicAddressesComposition());
    //
    //        assertCommandSuccess(deletePublicAddressCommand, model, expectedMessage, expectedModel);
    //    }


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
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeletePublicAddressCommand deleteFirstCommandCopy =
            new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different person -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }


}
