package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_CONFIRMATION = "Are you sure you want to clear the address book? ";
    public static final String MESSAGE_ABORT = "Clear command aborted.";

    /**
     * Executes the clear command.
     *
     * @param model The model in which the command should operate.
     * @return The result of the command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        boolean isConfirmed = MainWindow.showConfirmationDialog(MESSAGE_CONFIRMATION);
        if (!isConfirmed) {
            return new CommandResult(MESSAGE_ABORT);
        }

        model.setAddressBook(new AddressBook());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
