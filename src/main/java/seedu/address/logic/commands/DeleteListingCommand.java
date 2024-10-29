package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Name;

import java.util.List;

/**
 * Deletes a listing identified by its name.
 */
public class DeleteListingCommand extends Command {

    public static final String COMMAND_WORD = "deleteListing";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the listing specified by its name.\n"
            + "Parameters: LISTING_NAME\n"
            + "Example: " + COMMAND_WORD + " n/Warton House";

    public static final String MESSAGE_DELETE_LISTING_SUCCESS = "Successfully deleted listing: %1$s";
    public static final String MESSAGE_LISTING_NOT_FOUND = "This listing does not exist in EZSTATE";

    private final Name targetName;

    public DeleteListingCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Listing> lastShownList = model.getFilteredListingList();
        Listing listingToDelete = model.getListingByName(targetName);

        if (lastShownList.contains(listingToDelete)) {
            model.deleteListing(listingToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_LISTING_SUCCESS, listingToDelete.getName()));
        } else {
            throw new CommandException(MESSAGE_LISTING_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteListingCommand)) {
            return false;
        }

        DeleteListingCommand otherCommand = (DeleteListingCommand) other;
        return targetName.equals(otherCommand.targetName);
    }
}
