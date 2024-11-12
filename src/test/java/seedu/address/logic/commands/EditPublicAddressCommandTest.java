package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.addresses.PublicAddressesComposition.MESSAGE_DUPLICATE_PUBLIC_ADDRESS;
import static seedu.address.testutil.TypicalPersons.JOE;
import static seedu.address.testutil.TypicalPersons.MOE;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ETH_MAIN;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ETH_SUB;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ETH_SUB_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_SOL_MAIN;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.addresses.EthAddress;
import seedu.address.model.addresses.PublicAddress;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class EditPublicAddressCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
        model.addPerson(new PersonBuilder(JOE).build());
    }

    // EP: All valid
    @Test
    public void execute_validIndexValidPublicAddress_success() {
        PublicAddress updatedPublicAddress =
            new EthAddress(VALID_PUBLIC_ADDRESS_ETH_SUB_STRING, VALID_PUBLIC_ADDRESS_ETH_MAIN.getLabel());
        EditPublicAddressCommand editCommand =
            new EditPublicAddressCommand(Index.fromOneBased(1), updatedPublicAddress);


        Person personToEdit = model.getFilteredPersonList().get(0);
        String expectedMessage = String.format(EditPublicAddressCommand.MESSAGE_SUCCESS,
            Messages.format(personToEdit.withUpdatedPublicAddress(updatedPublicAddress)));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, personToEdit.withUpdatedPublicAddress(updatedPublicAddress));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    // EP: Different label casing
    @Test
    public void execute_validIndexValidPublicAddressDifferentCaseLabel_success() {
        PublicAddress updatedPublicAddress =
            new EthAddress(VALID_PUBLIC_ADDRESS_ETH_SUB_STRING,
                VALID_PUBLIC_ADDRESS_ETH_MAIN.getLabel().toUpperCase());
        EditPublicAddressCommand editCommand =
            new EditPublicAddressCommand(Index.fromOneBased(1), updatedPublicAddress);


        Person personToEdit = model.getFilteredPersonList().get(0);
        String expectedMessage = String.format(EditPublicAddressCommand.MESSAGE_SUCCESS,
            Messages.format(personToEdit.withUpdatedPublicAddress(updatedPublicAddress)));


        Model expectedModel = new ModelManager(new AddressBook(
            model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, personToEdit
            .withUpdatedPublicAddress(updatedPublicAddress));


        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    // EP: Index out of bounds
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        EditPublicAddressCommand editCommand = new EditPublicAddressCommand(outOfBoundIndex,
            VALID_PUBLIC_ADDRESS_BTC_MAIN);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    // EP: Non-matching label
    @Test
    public void execute_nonMatchingLabel_throwsCommandException() {
        EditPublicAddressCommand editCommand =
            new EditPublicAddressCommand(Index.fromOneBased(1),
                VALID_PUBLIC_ADDRESS_ETH_SUB);

        assertCommandFailure(editCommand, model, String.format(
            EditPublicAddressCommand.MESSAGE_NON_MATCHING_LABEL, VALID_PUBLIC_ADDRESS_ETH_SUB.getNetwork(),
            JOE.getName()));
    }

    // EP: Non-matching network
    @Test
    public void execute_noNetworkAddresses_throwsCommandException() {
        EditPublicAddressCommand editCommand =
            new EditPublicAddressCommand(Index.fromOneBased(1),
                VALID_PUBLIC_ADDRESS_SOL_MAIN);

        assertCommandFailure(editCommand, model, String.format(
            EditPublicAddressCommand.MESSAGE_NON_MATCHING_LABEL, VALID_PUBLIC_ADDRESS_SOL_MAIN.getNetwork(),
            JOE.getName()));
    }

    @Test
    public void execute_duplicatePublicAddress_throwsCommandException() {
        model.addPerson(new PersonBuilder(MOE).build());

        // Attempt to edit the same public address to another person
        PublicAddress duplicatePublicAddress = JOE.getPublicAddressesComposition().getOnePublicAddress();
        EditPublicAddressCommand editCommand = new EditPublicAddressCommand(Index.fromOneBased(2),
            duplicatePublicAddress);

        assertCommandFailure(editCommand, model, String.format(
                MESSAGE_DUPLICATE_PUBLIC_ADDRESS,
                duplicatePublicAddress.getPublicAddressString()));
    }

    @Test
    public void equals() {
        EditPublicAddressCommand editCommand1 = new EditPublicAddressCommand(Index.fromOneBased(1),
            VALID_PUBLIC_ADDRESS_BTC_MAIN);
        EditPublicAddressCommand editCommand2 = new EditPublicAddressCommand(Index.fromOneBased(2),
            VALID_PUBLIC_ADDRESS_BTC_MAIN);
        EditPublicAddressCommand editCommand3 = new EditPublicAddressCommand(Index.fromOneBased(1),
            VALID_PUBLIC_ADDRESS_ETH_MAIN);

        // EP: Same object
        assertEquals(editCommand1, editCommand1);

        // EP: Same values
        EditPublicAddressCommand editCommand1Copy =
            new EditPublicAddressCommand(Index.fromOneBased(1),
                VALID_PUBLIC_ADDRESS_BTC_MAIN);
        assertEquals(editCommand1, editCommand1Copy);

        // EP: Different type
        assertNotEquals(1, editCommand1);

        // EP: null
        assertNotEquals(null, editCommand1);

        // EP: Different index
        assertNotEquals(editCommand1, editCommand2);

        // EP: Different public address
        assertNotEquals(editCommand1, editCommand3);
    }

    @Test
    public void toString_returnsCorrectString() {
        EditPublicAddressCommand command = new EditPublicAddressCommand(Index.fromOneBased(1),
            VALID_PUBLIC_ADDRESS_BTC_MAIN);

        String expectedString = new ToStringBuilder(command)
            .add("index", Index.fromOneBased(1))
            .add("publicAddress", VALID_PUBLIC_ADDRESS_BTC_MAIN)
            .toString();

        assertEquals(expectedString, command.toString());
    }

}
