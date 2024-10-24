package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.model.AddressBook;
import tuteez.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    private static final Logger logger = LogsCenter.getLogger(ClearCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing clear command");
        int numberOfEntries = model.getAddressBook().getPersonList().size();
        model.setAddressBook(new AddressBook());
        String logMessage = String.format("Tuteez address book cleared. %d %s have been cleared",
                numberOfEntries, numberOfEntries == 1 ? "entry" : "entries");
        logger.info(logMessage);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
