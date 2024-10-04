package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.DeleteSellerCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class DeleteSellerCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute() {
        final String phoneNumber = "12345678";
        assertCommandFailure(new DeleteSellerCommand(phoneNumber), model,
                String.format(MESSAGE_ARGUMENTS, phoneNumber));
    }
    @Test
    public void equals() {
        final DeleteSellerCommand standardCommand = new DeleteSellerCommand(VALID_PHONE_AMY);
        // same values -> returns true
        DeleteSellerCommand commandWithSameValues = new DeleteSellerCommand(VALID_PHONE_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different buyer -> return false
        assertFalse(standardCommand.equals(new DeleteSellerCommand(VALID_PHONE_BOB)));
    }
}
