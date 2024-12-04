package seedu.address.logic.commands.clientcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandUtils;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.ui.ConfirmationDialog;

/**
 * Deletes a client identified using it's displayed index from the address book.
 */
public class DeleteClientProfileCommand extends Command {

    public static final String COMMAND_WORD = "deleteclient";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the client identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Successfully deleted %1$s ";
    public static final String MESSAGE_DELETE_CANCELLED = "Deletion canceled by user.";
    private final Index targetIndex;
    private final boolean skipConfirmation;

    public DeleteClientProfileCommand(Index targetIndex) {
        this(targetIndex, false);
    }

    /**
     * Creates a {@code DeleteClientProfileCommand} to delete the client profile with the specified name.
     *
     * @param targetIndex The index of the client profile to delete.
     * @param skipConfirmation If {@code true}, the command will skip the confirmation dialog for deletion;
     *                         if {@code false}, it will prompt the user for confirmation before deletion.
     */
    public DeleteClientProfileCommand(Index targetIndex, boolean skipConfirmation) {
        this.targetIndex = targetIndex;
        this.skipConfirmation = skipConfirmation;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        int zeroBased = targetIndex.getZeroBased();
        CommandUtils.handleInvalidPersonIndex(zeroBased, lastShownList.size());

        Person personToDelete = lastShownList.get(zeroBased);
        checkUserConfirmation(model, skipConfirmation, personToDelete);
        removeAllRelatedListings(model, personToDelete, personToDelete.getRole());

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    private void checkUserConfirmation(Model model, boolean skipConfirmation,
                                       Person personToDelete) throws CommandException {
        boolean hasListingsForSeller = model.hasListingsForSeller(personToDelete);
        boolean hasListingsForBuyer = model.hasListingsForBuyer(personToDelete);

        if (!skipConfirmation && (hasListingsForSeller || hasListingsForBuyer)) {
            boolean isConfirmed = ConfirmationDialog.showDeleteConfirmation(personToDelete.getName().toString());

            if (!isConfirmed) {
                throw new CommandException(MESSAGE_DELETE_CANCELLED);
            }
        }
    }

    private void removeAllRelatedListings(Model model, Person personToDelete, Role role) {
        if (role.equals(Role.BUYER)) {
            model.getListings().getListingList()
                    .forEach(listing -> listing.removeBuyer(personToDelete));

        } else if (role.equals(Role.SELLER)) {
            List<Listing> listingsToDelete = model.getListings().getListingList().stream()
                    .filter(listing -> listing.getSeller().equals(personToDelete))
                    .toList();

            for (Listing listing : listingsToDelete) {
                model.deleteListing(listing);
            }
        }

        model.updateFilteredListingList(listing -> true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteClientProfileCommand)) {
            return false;
        }

        DeleteClientProfileCommand otherDeleteCommand = (DeleteClientProfileCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
