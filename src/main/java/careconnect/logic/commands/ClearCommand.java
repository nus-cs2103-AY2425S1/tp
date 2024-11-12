package careconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import careconnect.logic.LogicManager;
import careconnect.logic.commands.exceptions.CommandException;
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
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.requireConfirmation) {
            // Queues a ClearCommand
            LogicManager.setCommandToConfirm(new ClearCommand(false));
            return new CommandResult(String.format(Command.CONFIRMATION_MESSAGE, ClearCommand.COMMAND_WORD));
        }
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
