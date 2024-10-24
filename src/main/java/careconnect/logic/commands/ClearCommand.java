package careconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import careconnect.model.AddressBook;
import careconnect.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public ClearCommand() {
        super(true);
    }

    private ClearCommand(boolean requireConfirmation) {
        super(requireConfirmation);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (this.requireConfirmation) {
            // Add a ClearCommand to the stack
            Command.STACK.add(new ClearCommand(false));
            return new CommandResult(Command.CONFIRMATION_MESSAGE);
        }
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
