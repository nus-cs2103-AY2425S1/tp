package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Name;

import java.util.HashSet;
import java.util.Set;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.DANIEL;

public class RemoveBuyersFromListingCommandTest {

    private static final Set<Name> VALID_BUYERS = Set.of(DANIEL.getName());
    private static final Set<Name> INVALID_BUYER = Set.of(AMY.getName());
    @Test
    public void constructor_nullListingName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveBuyersFromListingCommand(null,
                VALID_BUYERS));
    }

    @Test
    public void constructor_nullBuyersToRemove_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveBuyersFromListingCommand(PASIR_RIS.getName(),
                null));
    }

    @Test
    public void execute_buyerNotInClientList_throwsCommandException() {
        assertThrows(CommandException.class, () -> new RemoveBuyersFromListingCommand(PASIR_RIS.getName(),
                INVALID_BUYER));
    }
}
