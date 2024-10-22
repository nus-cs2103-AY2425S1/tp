package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LISTINGS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ShowListingCommand extends Command {

    public static final String COMMAND_WORD = "showlisting";
    public static final String MESSAGE_SUCCESS = "These are your listings!";
    public static final String MESSAGE_NO_LISTING_IN_LIST = "You currently have no listings in the list.";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredListingList(PREDICATE_SHOW_ALL_LISTINGS);
        if (model.getFilteredListingList().isEmpty()) {
            throw new CommandException(MESSAGE_NO_LISTING_IN_LIST);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
