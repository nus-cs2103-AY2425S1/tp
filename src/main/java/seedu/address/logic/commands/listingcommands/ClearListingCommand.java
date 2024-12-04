package seedu.address.logic.commands.listingcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Listings;
import seedu.address.model.Model;

/**
 * Clears all listings in the system.
 */
public class ClearListingCommand extends Command {

    public static final String COMMAND_WORD = "clearlistings";
    public static final String MESSAGE_SUCCESS = "All listings have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setListings(new Listings());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
