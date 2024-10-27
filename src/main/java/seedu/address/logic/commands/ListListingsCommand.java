package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LISTINGS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all listings in the system to the user.
 */
public class ListListingsCommand extends Command {

    public static final String COMMAND_WORD = "listListings";
    public static final String MESSAGE_SUCCESS = "Here are your listings!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all listings currently in the system.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_NO_LISTINGS_IN_LIST = "You currently have no listings in the system.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredListingList(PREDICATE_SHOW_ALL_LISTINGS);

        if (model.getFilteredListingList().isEmpty()) {
            throw new CommandException(MESSAGE_NO_LISTINGS_IN_LIST);
        }

        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false);
    }
}
