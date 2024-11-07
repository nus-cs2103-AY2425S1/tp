package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN;

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
import seedu.address.model.addresses.PublicAddressesComposition;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddPublicAddressCommand}.
 */
public class AddPublicAddressCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // EP: valid index, valid public address (BTC main)
    @Test
    public void execute_validIndexValidPublicAddress_success() {
        Person personToAddAddress = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        PublicAddressesComposition publicAddresses = new PublicAddressesComposition()
                .addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        editPersonDescriptor.setPublicAddresses(publicAddresses);

        AddPublicAddressCommand addPublicAddressCommand =
            new AddPublicAddressCommand(INDEX_FIRST_PERSON, editPersonDescriptor);

        // Edit the person
        Person editedPerson = personToAddAddress.withAddedPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);

        // Expected message
        String expectedMessage =
                String.format(AddPublicAddressCommand.MESSAGE_ADDPA_SUCCESS, Messages.format(editedPerson));

        // Expected model
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToAddAddress, editedPerson);

        // Execute the command and compare the result
        assertCommandSuccess(addPublicAddressCommand, model, expectedMessage, expectedModel);
    }

    // EP: valid index, invalid label (duplicate)
    @Test
    public void execute_duplicateAddressLabel_throwsCommandException() {
        Person personToAddAddress = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Create the first descriptor with a valid address
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        PublicAddressesComposition publicAddresses = new PublicAddressesComposition()
                .addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        editPersonDescriptor.setPublicAddresses(publicAddresses);

        // Create the second descriptor with the same address
        EditPersonDescriptor duplicateDescriptor = new EditPersonDescriptor();
        PublicAddressesComposition duplicatePublicAddresses = new PublicAddressesComposition()
                .addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
        duplicateDescriptor.setPublicAddresses(duplicatePublicAddresses);

        // Create the first command
        AddPublicAddressCommand firstCommand =
            new AddPublicAddressCommand(INDEX_FIRST_PERSON, editPersonDescriptor);

        // Create the second command
        AddPublicAddressCommand duplicateCommand =
            new AddPublicAddressCommand(INDEX_FIRST_PERSON, duplicateDescriptor);

        try {
            firstCommand.execute(model);
        } catch (CommandException ce) {
            // Ignore any exceptions from first command
        }

        // Expected message
        String expectedMessage = String.format(AddPublicAddressCommand.MESSAGE_DUPLICATE_PUBLIC_ADDRESS,
                String.format(
                        PublicAddressesComposition.MESSAGE_DUPLICATE_LABEL,
                        VALID_PUBLIC_ADDRESS_BTC_MAIN.getLabel(),
                        Network.BTC
                )
        );

        assertCommandFailure(duplicateCommand, model, expectedMessage);
    }

    // EP: invalid index, valid public address (BTC main)
    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        PublicAddressesComposition publicAddresses = new PublicAddressesComposition()
                .addPublicAddress(VALID_PUBLIC_ADDRESS_BTC_MAIN);
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

        PublicAddressesComposition publicAddresses1 = new PublicAddressesComposition().addPublicAddress(address1);
        PublicAddressesComposition publicAddresses2 = new PublicAddressesComposition().addPublicAddress(address2);

        EditPersonDescriptor descriptor1 = new EditPersonDescriptor();
        EditPersonDescriptor descriptor2 = new EditPersonDescriptor();
        descriptor1.setPublicAddresses(publicAddresses1);
        descriptor2.setPublicAddresses(publicAddresses2);

        AddPublicAddressCommand addCommand1 = new AddPublicAddressCommand(INDEX_FIRST_PERSON, descriptor1);
        AddPublicAddressCommand addCommand2 = new AddPublicAddressCommand(INDEX_FIRST_PERSON, descriptor2);
        AddPublicAddressCommand addCommand1Copy = new AddPublicAddressCommand(INDEX_FIRST_PERSON, descriptor1);
        AddPublicAddressCommand differentIndexCommand = new AddPublicAddressCommand(INDEX_SECOND_PERSON, descriptor1);

        AddPublicAddressCommand addCommand1Ref = addCommand1;
        // same object -> returns true
        assertEquals(addCommand1, addCommand1Ref);

        // same values -> returns true
        assertEquals(addCommand1, addCommand1Copy);

        // different types -> returns false
        assertNotEquals(1, addCommand1);

        // null -> returns false
        assertNotEquals(null, addCommand1);

        // different address -> returns false
        assertNotEquals(addCommand1, addCommand2);

        // different index -> returns false
        assertNotEquals(addCommand1, differentIndexCommand);
    }
}
