package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_BTC_SUB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BTC_MAIN_ADDRESS;
import static seedu.address.testutil.TypicalPersons.ETH_MAIN_ADDRESS;
import static seedu.address.testutil.TypicalPersons.ETH_SUB_ADDRESS;
import static seedu.address.testutil.TypicalPersons.JOE;
import static seedu.address.testutil.TypicalPersons.SOL_MAIN_ADDRESS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.addresses.BtcAddress;
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

    @Test
    public void execute_validIndexValidPublicAddress_success() {
        PublicAddress updatedPublicAddress =
                new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_SUB, BTC_MAIN_ADDRESS.getLabel());
        EditPublicAddressCommand editCommand =
                new EditPublicAddressCommand(Index.fromOneBased(1), updatedPublicAddress);

        Person personToEdit = model.getFilteredPersonList().get(0);
        String expectedMessage = String.format(EditPublicAddressCommand.MESSAGE_SUCCESS,
                Messages.format(personToEdit.withUpdatedPublicAddress(updatedPublicAddress)));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, personToEdit.withUpdatedPublicAddress(updatedPublicAddress));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexValidPublicAddressDifferentCaseLabel_success() {
        PublicAddress updatedPublicAddress =
                new BtcAddress(VALID_PUBLIC_ADDRESS_BTC_SUB, BTC_MAIN_ADDRESS.getLabel().toUpperCase());
        EditPublicAddressCommand editCommand =
                new EditPublicAddressCommand(Index.fromOneBased(1), updatedPublicAddress);

        Person personToEdit = model.getFilteredPersonList().get(0);
        String expectedMessage = String.format(EditPublicAddressCommand.MESSAGE_SUCCESS,
                Messages.format(personToEdit.withUpdatedPublicAddress(updatedPublicAddress)));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, personToEdit.withUpdatedPublicAddress(updatedPublicAddress));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPublicAddressCommand editCommand = new EditPublicAddressCommand(outOfBoundIndex, BTC_MAIN_ADDRESS);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_nonMatchingLabel_throwsCommandException() {
        EditPublicAddressCommand editCommand =
                new EditPublicAddressCommand(Index.fromOneBased(1), ETH_SUB_ADDRESS);

        assertCommandFailure(editCommand, model, String.format(
                EditPublicAddressCommand.MESSAGE_NON_MATCHING_LABEL, ETH_SUB_ADDRESS.getNetwork(), JOE.getName()));
    }

    @Test
    public void execute_noNetworkAddresses_throwsCommandException() {
        EditPublicAddressCommand editCommand =
                new EditPublicAddressCommand(Index.fromOneBased(1), SOL_MAIN_ADDRESS);

        assertCommandFailure(editCommand, model, String.format(
                EditPublicAddressCommand.MESSAGE_NON_MATCHING_LABEL, SOL_MAIN_ADDRESS.getNetwork(), JOE.getName()));
    }

    @Test
    public void equals() {
        EditPublicAddressCommand editCommand1 = new EditPublicAddressCommand(Index.fromOneBased(1), BTC_MAIN_ADDRESS);
        EditPublicAddressCommand editCommand2 = new EditPublicAddressCommand(Index.fromOneBased(2), BTC_MAIN_ADDRESS);
        EditPublicAddressCommand editCommand3 = new EditPublicAddressCommand(Index.fromOneBased(1), ETH_MAIN_ADDRESS);

        // same object -> returns true
        assertTrue(editCommand1.equals(editCommand1));

        // same values -> returns true
        EditPublicAddressCommand editCommand1Copy =
                new EditPublicAddressCommand(Index.fromOneBased(1), BTC_MAIN_ADDRESS);
        assertTrue(editCommand1.equals(editCommand1Copy));

        // different types -> returns false
        assertFalse(editCommand1.equals(1));

        // null -> returns false
        assertFalse(editCommand1.equals(null));

        // different index -> returns false
        assertFalse(editCommand1.equals(editCommand2));

        // different public address -> returns false
        assertFalse(editCommand1.equals(editCommand3));
    }

    @Test
    public void toString_returnsCorrectString() {
        EditPublicAddressCommand command = new EditPublicAddressCommand(Index.fromOneBased(1), BTC_MAIN_ADDRESS);

        String expectedString = new ToStringBuilder(command)
                .add("index", Index.fromOneBased(1))
                .add("publicAddress", BTC_MAIN_ADDRESS)
                .toString();

        assertEquals(expectedString, command.toString());
    }

}
