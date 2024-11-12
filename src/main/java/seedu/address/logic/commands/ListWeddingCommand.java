package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

import seedu.address.model.Model;

/**
 * Lists all the weddings in the address book to the user.
 */
public class ListWeddingCommand extends Command {

    public static final String COMMAND_WORD = "list-wed";
    public static final String COMMAND_WORD_SHORT = "lw";
    public static final String COMMAND_FUNCTION = COMMAND_WORD + " OR "
            + COMMAND_WORD_SHORT + ": Lists out the entire wedding book.";
    public static final String MESSAGE_SUCCESS = "Listed all weddings";
    public static final String MESSAGE_EMPTY = " Your wedding book is empty";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);

        return new CommandResult(generateSuccessMessage(model));
    }

    /**
     * Returns {@code String} message result of ListCommand executed on {@code Model}.
     */
    public String generateSuccessMessage(Model model) {
        int numberOfWeddings = model.getWeddingBook().getWeddingList().size();

        if (numberOfWeddings == 0) {
            return MESSAGE_EMPTY;
        }

        String numberOfEvents = ", there are currently " + numberOfWeddings + " weddings in your wedding book";
        return MESSAGE_SUCCESS + numberOfEvents;
    }
}

