package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.JOE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.addresses.Network;

public class FilterByNetworkCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_validNetwork_success() {
        model.addPerson(JOE);

        FilterByNetworkCommand retrieveCommand = new FilterByNetworkCommand(Network.BTC);
        String expectedMessage = String.format(FilterByNetworkCommand.MESSAGE_FILTER_SUCCESS,
                1, Network.BTC, "1. Joe Mama");

        assertCommandSuccess(retrieveCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_invalidNetwork_noPersonsFound() {
        model.addPerson(JOE);

        FilterByNetworkCommand command = new FilterByNetworkCommand(Network.SOL);
        String expectedMessage = String.format(FilterByNetworkCommand.MESSAGE_FILTER_FAIL, Network.SOL);

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_emptyAddressBook_noPersonsFound() {
        FilterByNetworkCommand command = new FilterByNetworkCommand(Network.BTC);
        String expectedMessage = String.format(FilterByNetworkCommand.MESSAGE_FILTER_FAIL, Network.BTC);

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {
        FilterByNetworkCommand filterBtcCommand = new FilterByNetworkCommand(Network.BTC);
        FilterByNetworkCommand filterEthCommand = new FilterByNetworkCommand(Network.ETH);

        // Same object -> returns true
        assertTrue(filterBtcCommand.equals(filterBtcCommand));

        // Same values -> returns true
        FilterByNetworkCommand filterBtcCommandCopy = new FilterByNetworkCommand(Network.BTC);
        assertTrue(filterBtcCommand.equals(filterBtcCommandCopy));

        // Different types -> returns false
        assertFalse(filterBtcCommand.equals(1));

        // Null -> returns false
        assertFalse(filterBtcCommand.equals(null));

        // Different network -> returns false
        assertFalse(filterBtcCommand.equals(filterEthCommand));
    }
}
