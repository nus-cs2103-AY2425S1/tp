package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.ui.controller.ConfirmationController;
import seedu.address.ui.controller.ConfirmationWindowController;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to clear ALL contacts from MediContact?\n"
            + "This action is IRREVERSIBLE.";
    public static final String MESSAGE_CLEAR_CANCELLED = "Clear action cancelled.";
    private final ConfirmationController confirmationController;

    public ClearCommand() {
        this.confirmationController = new ConfirmationWindowController();
    }

    /**
     * This constructor should only be used for testing purposes to skip confirmation window.
     * @param confirmationController provides the result for confirm clear.
     */
    public ClearCommand(ConfirmationController confirmationController) {
        assert confirmationController != null;
        this.confirmationController = confirmationController;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (isClearConfirmed()) {
            model.setAddressBook(new AddressBook());
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        }

        return new CommandResult(String.format(MESSAGE_CLEAR_CANCELLED));
    }

    /**
     * Checks if the clear action is confirmed by the user.
     *
     * @return true if the user confirms clear, false otherwise.
     */
    private boolean isClearConfirmed() {
        return confirmationController.isConfirmed("Confirm Clear", String.format(MESSAGE_CONFIRMATION));
    }
}
