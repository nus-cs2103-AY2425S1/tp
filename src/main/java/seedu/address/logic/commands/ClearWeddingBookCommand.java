package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.WeddingBook;

/**
 * Clears the address book.
 */
public class ClearWeddingBookCommand extends Command {

    public static final String COMMAND_WORD = "clear-wb";
    public static final String COMMAND_FUNCTION = ": Clears the entire wedding book";
    public static final String MESSAGE_SUCCESS = "Wedding book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setWeddingBook(new WeddingBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
