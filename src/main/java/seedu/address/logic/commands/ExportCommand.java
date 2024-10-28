package seedu.address.logic.commands;


import seedu.address.model.AddressBook;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Exports the contacts to a file akin to the save file.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "Address book has been exported!";

    private AddressBook initialAddressBook;

    @Override
    public CommandResult execute(Model model) {
        requireNotExecuted();
        requireNonNull(model);
        isExecuted = true;
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
