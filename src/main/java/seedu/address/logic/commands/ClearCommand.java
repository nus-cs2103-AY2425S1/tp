package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.ConfirmationHandler;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.ConfirmClearWindow;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    private ConfirmationHandler confirmationHandler;


    public ClearCommand() {
        this.confirmationHandler = new DefaultConfirmationHandler();
    }

    /**
     * Constructor for a {@code ClearCommand} object for unit tests
     * @param confirmationHandler
     */
    public ClearCommand(ConfirmationHandler confirmationHandler) {
        this.confirmationHandler = confirmationHandler;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        boolean isConfirmed = confirmationHandler.confirm(null);
        if (!isConfirmed) {
            throw new CommandException(Messages.MESSAGE_USER_CANCEL);
        }

        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Nested class for testing purposes
     */
    public static class DefaultConfirmationHandler implements ConfirmationHandler {
        /**
         * Bypasses UI popup for testing purposes
         * @return Whether the deletion proceeds or not
         */

        @Override
        public boolean confirm(Person person) {
            ConfirmClearWindow confirmClearWindow = new ConfirmClearWindow();
            confirmClearWindow.show();
            return confirmClearWindow.isConfirmed();
        }
    }
}
