package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WEDDINGS;

import seedu.address.model.Model;

/**
 * Lists all the weddings in the address book to the user.
 */
public class ListWeddingCommand extends Command {

    public static final String COMMAND_WORD = "list-wedding";

    public static final String MESSAGE_SUCCESS = "Listed all weddings";
    public static final String MESSAGE_EMPTY = " Your wedding book is empty";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
        int numberOfWeddings = model.getWeddingBook().getWeddingList().size();
        String numberOfEvents = ", there are currently " + numberOfWeddings + " weddings in your wedding book";
        if (numberOfWeddings == 0) {
            return new CommandResult(MESSAGE_EMPTY);
        }
        return new CommandResult(MESSAGE_SUCCESS + numberOfEvents);
    }
}

