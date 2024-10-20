package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LISTINGS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class ShowAllListingsCommand extends Command {

    public static final String COMMAND_WORD = "showlistings";
    public static final String MESSAGE_SUCCESS = "These are your listings!";
    public static final String MESSAGE_NO_LISTINGS_IN_LIST = "You have no listings available.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredListingList(PREDICATE_SHOW_ALL_LISTINGS);
        if (model.getFilteredListingList().isEmpty()) {
            throw new CommandException(MESSAGE_NO_LISTINGS_IN_LIST);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
