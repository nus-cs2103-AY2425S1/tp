package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.ConfirmationHandler;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_userConfirmedEmptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        ConfirmationHandler mockHandler = person -> true;

        assertCommandSuccess(new ClearCommand(mockHandler), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_userCancelledEmptyAddressBook_throwsCommandException() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        ConfirmationHandler mockHandler = person -> false;

        assertCommandFailure(new ClearCommand(mockHandler), model, Messages.MESSAGE_USER_CANCEL);
    }

    @Test
    public void execute_userConfirmedNonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ConfirmationHandler mockHandler = person -> true;

        expectedModel.setAddressBook(new AddressBook());
        assertCommandSuccess(new ClearCommand(mockHandler), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_userCancelledNonEmptyAddressBook_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ConfirmationHandler mockHandler = person -> false;

        expectedModel.setAddressBook(new AddressBook());

        assertCommandFailure(new ClearCommand(mockHandler), model, Messages.MESSAGE_USER_CANCEL);
    }

}
