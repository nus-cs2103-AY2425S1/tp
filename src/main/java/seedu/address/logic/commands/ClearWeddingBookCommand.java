package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.StaticContext;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearWeddingBookCommand extends Command {

    public static final String COMMAND_WORD = "clear-wb";
    public static final String COMMAND_FUNCTION = ": Clears the entire wedding book";
    public static final String CONFIRMATION_PROMPT = "Are you sure you want to delete entire wedding book?\n"
            + "Enter 'y' to confirm, or 'n' to cancel.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //Store the wedding book to be deleted in StaticContext
        StaticContext.setClearWeddingBookPending(true);
        return new CommandResult(CONFIRMATION_PROMPT);
    }
}
