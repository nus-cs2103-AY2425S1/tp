package spleetwaise.address.logic.commands;

import spleetwaise.address.model.AddressBookModel;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(AddressBookModel model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
