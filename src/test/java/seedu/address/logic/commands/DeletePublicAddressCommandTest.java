package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeletePublicAddressCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.addresses.PublicAddressFactory;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeletePublicAddressCommand}.
 */
public class DeletePublicAddressCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @BeforeEach
    public void setUp() {
        model.setAddressBook(getTypicalAddressBook());
    }

    //---------------- Tests for execute method ----------------

    // EP: valid index, valid network
    @Test
    public void execute_validIndexValidNetwork_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        // Create and add the public address
        Network network = Network.BTC;
        String publicAddress = VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;
        PublicAddress publicAddressToAdd = PublicAddressFactory.createPublicAddress(network,
            publicAddress, PublicAddress.DEFAULT_LABEL);

        // Update actual models with the new address
        Person personWithAddress = personToDelete.withAddedPublicAddress(publicAddressToAdd);
        model.setPerson(personToDelete, personWithAddress);
        Person personWithoutAddress = personWithAddress.withoutPublicAddressesByNetwork(network);
        expectedModel.setPerson(personToDelete, personWithoutAddress);

        // Create and execute delete command
        DeletePublicAddressCommand deletePublicAddressCommand =
            new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC);

        String expectedMessage = String.format(
            MESSAGE_DELETE_PERSON_SUCCESS, personWithAddress.getName(),
            personWithAddress.getPublicAddressesByNetwork(network)
        );
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(deletePublicAddressCommand, model, expectedCommandResult, expectedModel);
    }

    // EP: valid index, valid network, valid label
    @Test
    public void execute_validIndexValidNetworkValidLabel_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Create and add the public address
        Network network = Network.BTC;
        String publicAddress = VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;
        String label = "default";
        PublicAddress publicAddressToAdd = PublicAddressFactory.createPublicAddress(network, publicAddress, label);

        Person personWithAddress = personToDelete.withAddedPublicAddress(publicAddressToAdd);
        model.setPerson(personToDelete, personWithAddress);

        // Create and execute delete command
        DeletePublicAddressCommand deletePublicAddressCommand =
            new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC, label);

        String expectedMessage = String.format(
            MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName(),
            personWithAddress.getPublicAddressesByNetworkAndLabel(network, label)
        );
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);

        assertCommandSuccess(deletePublicAddressCommand, model, expectedCommandResult, expectedModel);
    }


    // EP: valid index, valid network, invalid label
    @Test
    public void execute_validIndexValidNetworkInvalidLabel_throwsCommandException() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // Create and add the public address
        Network network = Network.BTC;
        String publicAddress = VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;
        String label = "default";
        PublicAddress publicAddressToAdd = PublicAddressFactory.createPublicAddress(network, publicAddress, label);

        Person personWithAddress = personToDelete.withAddedPublicAddress(publicAddressToAdd);
        model.setPerson(personToDelete, personWithAddress);

        DeletePublicAddressCommand deletePublicAddressCommand =
            new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.BTC, "invalid");

        assertCommandFailure(deletePublicAddressCommand, model,
            String.format(DeletePublicAddressCommand.MESSAGE_NON_MATCHING_LABEL, "invalid",
                personToDelete.getName()));
    }

    // EP: valid index, invalid network
    @Test
    public void execute_validIndexInvalidNetwork_throwsCommandException() {
        DeletePublicAddressCommand deletePublicAddressCommand =
            new DeletePublicAddressCommand(INDEX_FIRST_PERSON, Network.ETH);

        assertCommandFailure(deletePublicAddressCommand, model,
            String.format(
                DeletePublicAddressCommand.MESSAGE_EMPTY_NETWORK,
                Network.ETH,
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName())
        );
    }

    // EP: invalid index
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePublicAddressCommand deletePublicAddressCommand =
            new DeletePublicAddressCommand(outOfBoundIndex, Network.BTC);

        assertCommandFailure(deletePublicAddressCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    //---------------- Tests for equals() method ----------------
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
