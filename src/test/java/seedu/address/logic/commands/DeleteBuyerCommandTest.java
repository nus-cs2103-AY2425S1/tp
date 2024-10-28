package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteBuyerCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingBook;
import static seedu.address.testutil.TypicalProperty.getTypicalPropertyBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.Phone;

public class DeleteBuyerCommandTest {
    private Model model = new ModelManager(new UserPrefs(),
            getTypicalPropertyBook(), getTypicalClientBook(), getTypicalMeetingBook());
    @Test
    public void execute_phoneNumberFound_assertCommandSuccess() {
        final Phone phoneNumber = new Phone("94351253");
        DeleteBuyerCommand deleteBuyerCommand = new DeleteBuyerCommand(phoneNumber);
        Client personToDelete = model.getFilteredClientList().stream()
                .filter(Client::isBuyer)
                .filter(person -> person.getPhone().equals(phoneNumber))
                .findFirst().orElseThrow(() -> new AssertionError("Phone number not found in the model"));
        String expectedMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete));
        assertCommandSuccess(deleteBuyerCommand, model, expectedMessage, model);
    }
    @Test
    public void equals() {
        final DeleteBuyerCommand standardCommand = new DeleteBuyerCommand(new Phone(VALID_PHONE_AMY));
        // same values -> returns true
        DeleteBuyerCommand commandWithSameValues = new DeleteBuyerCommand(new Phone(VALID_PHONE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ExitCommand()));
        // different buyer -> return false
        assertFalse(standardCommand.equals(new DeleteBuyerCommand(new Phone(VALID_PHONE_BOB))));
    }
}
