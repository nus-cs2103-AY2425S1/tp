package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AbstractEditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
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
 * {@code AddPublicAddressCommand}.
 */
public class AddPublicAddressCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_validIndexValidNetwork_success() throws Exception {
        Person personToAddAddress = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Network network = Network.BTC;
        PublicAddress address = new BtcAddress("12345", "test");

        Map<Network, Set<PublicAddress>> publicAddresses = Map.of(network, Set.of(address));
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        editPersonDescriptor.setPublicAddresses(publicAddresses);

        AddPublicAddressCommand addPublicAddressCommand =
                new AddPublicAddressCommand(INDEX_FIRST_PERSON, editPersonDescriptor);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person updatedPerson = new PersonBuilder(personToAddAddress).build();
        Set<PublicAddress> addresses = new HashSet<>(updatedPerson.getPublicAddressesByNetwork(network));
        addresses.add(address);
        updatedPerson.setPublicAddressesByNetwork(network, addresses);
        expectedModel.setPerson(personToAddAddress, updatedPerson);

        String expectedMessage = String.format(AddPublicAddressCommand.MESSAGE_ADDPA_SUCCESS,
                Messages.format(updatedPerson));

        assertCommandSuccess(addPublicAddressCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_duplicateAddressLabel_throwsCommandException() {
        Person personToAddAddress = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // First add an address
        Network network = Network.BTC;
        PublicAddress address = new BtcAddress("12345", "wallet1");

        Map<Network, Set<PublicAddress>> publicAddresses = Map.of(network, Set.of(address));
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        editPersonDescriptor.setPublicAddresses(publicAddresses);

        AddPublicAddressCommand firstCommand =
                new AddPublicAddressCommand(INDEX_FIRST_PERSON, editPersonDescriptor);

        // Try to add another address with the same label
        PublicAddress duplicateAddress = new BtcAddress("12345", "wallet1");
        Map<Network, Set<PublicAddress>> duplicateAddresses = Map.of(network, Set.of(duplicateAddress));
        EditPersonDescriptor duplicateDescriptor = new EditPersonDescriptor();
        duplicateDescriptor.setPublicAddresses(duplicateAddresses);

        AddPublicAddressCommand duplicateCommand =
                new AddPublicAddressCommand(INDEX_FIRST_PERSON, duplicateDescriptor);

        // Execute first command
        try {
            firstCommand.execute(model);
        } catch (CommandException ce) {
            // Ignore any exceptions from first command
        }

        String expectedMessage = String.format(AddPublicAddressCommand.MESSAGE_DUPLICATE_PUBLIC_ADDRESS,
                personToAddAddress.getName(), duplicateAddress, Network.BTC);

        assertThrows(
                IllegalArgumentException.class, expectedMessage, () -> duplicateCommand.execute(model)
        );
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Network network = Network.BTC;
        PublicAddress address = new BtcAddress("12345", "test");

        Map<Network, Set<PublicAddress>> publicAddresses = Map.of(network, Set.of(address));
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        editPersonDescriptor.setPublicAddresses(publicAddresses);

        AddPublicAddressCommand addPublicAddressCommand =
                new AddPublicAddressCommand(outOfBoundIndex, editPersonDescriptor);

        assertCommandFailure(addPublicAddressCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Network network = Network.BTC;
        PublicAddress address1 = new BtcAddress("12345", "wallet1");
        PublicAddress address2 = new BtcAddress("67890", "wallet2");

        Map<Network, Set<PublicAddress>> publicAddresses1 = Map.of(network, Set.of(address1));
        Map<Network, Set<PublicAddress>> publicAddresses2 = Map.of(network, Set.of(address2));

        EditPersonDescriptor descriptor1 = new EditPersonDescriptor();
        EditPersonDescriptor descriptor2 = new EditPersonDescriptor();
        descriptor1.setPublicAddresses(publicAddresses1);
        descriptor2.setPublicAddresses(publicAddresses2);

        AddPublicAddressCommand addCommand1 = new AddPublicAddressCommand(INDEX_FIRST_PERSON, descriptor1);
        AddPublicAddressCommand addCommand2 = new AddPublicAddressCommand(INDEX_FIRST_PERSON, descriptor2);
        AddPublicAddressCommand addCommand1Copy = new AddPublicAddressCommand(INDEX_FIRST_PERSON, descriptor1);
        AddPublicAddressCommand differentIndexCommand = new AddPublicAddressCommand(INDEX_SECOND_PERSON, descriptor1);

        // same object -> returns true
        assertTrue(addCommand1.equals(addCommand1));

        // same values -> returns true
        assertTrue(addCommand1.equals(addCommand1Copy));

        // different types -> returns false
        assertFalse(addCommand1.equals(1));

        // null -> returns false
        assertFalse(addCommand1.equals(null));

        // different address -> returns false
        assertFalse(addCommand1.equals(addCommand2));

        // different index -> returns false
        assertFalse(addCommand1.equals(differentIndexCommand));
    }
}
