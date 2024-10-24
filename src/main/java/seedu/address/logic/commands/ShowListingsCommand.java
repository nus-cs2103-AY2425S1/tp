package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LISTINGS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays all listings to the user.
 */
public class ShowListingsCommand extends Command {

    public static final String COMMAND_WORD = "showlistings";
    public static final String MESSAGE_SUCCESS = "Here are your listings!";
    public static final String MESSAGE_NO_LISTINGS_IN_LIST = "You have no listings available.";

    /**
     * Executes the command and displays all listings to the user.
     * If no listings are available, a CommandException is thrown with a message.
     *
     * @param model The {@code Model} which the command should operate on.
     * @return A CommandResult containing the success message.
     * @throws CommandException If there are no listings available in the filtered list.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredListingList(PREDICATE_SHOW_ALL_LISTINGS);
        if (model.getFilteredListingList().isEmpty()) {
            throw new CommandException(MESSAGE_NO_LISTINGS_IN_LIST);
        }
        return new CommandResult(MESSAGE_SUCCESS, false, false,
                true, false);
    }
}
