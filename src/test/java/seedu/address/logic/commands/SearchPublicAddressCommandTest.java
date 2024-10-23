package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_BTC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_ETH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.SearchPublicAddressCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
/**
 * Contains integration tests (interaction with the Model) and unit tests for SearchPublicAddressCommand.
 */
public class SearchPublicAddressCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute() {
        final String publicAddress = "Some publicAddress";
        assertCommandFailure(new SearchPublicAddressCommand(publicAddress), model,
                String.format(MESSAGE_ARGUMENTS, publicAddress));
    }
    @Test
    public void equals() {
        final SearchPublicAddressCommand standardCommand = new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_ETH);
        // same values -> returns true
        SearchPublicAddressCommand commandWithSameValues = new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_ETH);
        assertEquals(standardCommand, commandWithSameValues);
        // same object -> returns true
        assertEquals(standardCommand, standardCommand);
        // null -> returns false
        assertNotEquals(null, standardCommand);
        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());
        // different publicAddress -> returns false
        assertNotEquals(standardCommand, new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC));
    }
}
