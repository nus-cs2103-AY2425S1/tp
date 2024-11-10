package seedu.address.logic.commands.listingcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;

/**
 * Deletes a listing identified by its name.
 */
public class DeleteListingCommand extends Command {

    public static final String COMMAND_WORD = "deletelisting";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the listing identified by its index number used in the displayed listing list.\n"
            + "Parameters: LISTING_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LISTING_SUCCESS = "Successfully deleted listing: %1$s";
    public static final String MESSAGE_LISTING_NOT_FOUND = "This listing does not exist in EZSTATE";

    private final Index targetIndex;

    public DeleteListingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Listing> lastShownList = model.getFilteredListingList();
        int zeroBased = targetIndex.getZeroBased();
        if (zeroBased >= lastShownList.size() || zeroBased < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
        }

        Listing listingToDelete = lastShownList.get(targetIndex.getZeroBased());

        model.deleteListing(listingToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_LISTING_SUCCESS, Messages.format(listingToDelete)));
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
        return targetIndex.equals(otherCommand.targetIndex);
    }
}
