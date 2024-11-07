package seedu.address.logic.commands.listingcommands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Removes buyers from an existing listing in the system.
 */
public class RemoveBuyersFromListingCommand extends Command {

    public static final String COMMAND_WORD = "removelistingbuyers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes buyers from the listing identified by its "
            + "index. "
            + "Parameters: LISTING_INDEX buyer/BUYER_INDEX [buyer/MORE_BUYER_INDEXES]...\n"
            + "Example: " + COMMAND_WORD + " 1 buyer/1 buyer/3";

    public static final String MESSAGE_REMOVE_BUYERS_SUCCESS = "Buyers removed from listing: %1$s.\n"
            + "Removed buyers: %2$s";
    public static final String MESSAGE_LISTING_NOT_FOUND = "The specified listing name does not exist.";
    public static final String MESSAGE_EMPTY_SET = "Please provide valid buyers";
    public static final String MESSAGE_PERSON_NOT_BUYER = "The specified person is not a buyer:\n"
            + "%d. %s";
    public static final String MESSAGE_NOT_BUYER_FOR_LISTING =
            "The specified buyer %1$s is not a buyer of the listing %2$s.";
    public static final String MESSAGE_BUYER_NOT_FOUND = "The specified buyer %1$s does not exist in the client list.";

    private final Index index;
    private final Set<Index> buyersToRemove;

    /**
     * Constructs a {@code RemoveBuyersFromListingCommand}.
     *
     * @param index The index of the listing from which buyers will be removed.
     * @param buyersToRemove The indexes of the buyers to remove from the listing.
     */
    public RemoveBuyersFromListingCommand(Index index, Set<Index> buyersToRemove) {
        requireNonNull(index);
        requireNonNull(buyersToRemove);

        this.index = index;
        this.buyersToRemove = new HashSet<>(buyersToRemove);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        int zeroBased = index.getZeroBased();
        List<Listing> lastShownListingList = model.getFilteredListingList();
        if (zeroBased >= lastShownListingList.size() || zeroBased < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
        }

        Listing listingToEdit = model.getFilteredListingList().get(zeroBased);

        Set<Person> existingBuyers = new HashSet<>(listingToEdit.getBuyers());
        Set<Person> buyersToRemoveSet = new HashSet<>();
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        for (Index buyerIndex : buyersToRemove) {
            int zeroBasedBuyer = buyerIndex.getZeroBased();
            int oneBasedBuyer = buyerIndex.getOneBased();
            if (zeroBasedBuyer >= lastShownPersonList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person buyerToRemove = lastShownPersonList.get(zeroBasedBuyer);

            // Check if the person is actually an instance of Buyer
            if (!buyerToRemove.getRole().equals(Role.BUYER)) {
                throw new CommandException(String.format(MESSAGE_PERSON_NOT_BUYER, oneBasedBuyer));
            }

            if (!existingBuyers.contains(buyerToRemove)) {
                throw new CommandException(String.format(MESSAGE_NOT_BUYER_FOR_LISTING,
                        buyerToRemove.getName(), listingToEdit));
            }

            buyersToRemoveSet.add(buyerToRemove);
        }

        if (buyersToRemoveSet.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_SET);
        }

        existingBuyers.removeAll(buyersToRemoveSet);

        // Create an updated listing with the modified buyers set
        Listing updatedListing = new Listing(
                listingToEdit.getName(),
                listingToEdit.getAddress(),
                listingToEdit.getPrice(),
                listingToEdit.getArea(),
                listingToEdit.getRegion(),
                listingToEdit.getSeller(),
                existingBuyers
        );

        model.setListing(listingToEdit, updatedListing);
        String removedNames = buyersToRemoveSet.stream()
                .map(buyer -> buyer.getName().toString()).collect(Collectors.joining(", "));
        return new CommandResult(String.format(MESSAGE_REMOVE_BUYERS_SUCCESS, listingToEdit.getName(), removedNames));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveBuyersFromListingCommand)) {
            return false;
        }

        RemoveBuyersFromListingCommand otherCommand = (RemoveBuyersFromListingCommand) other;
        return index.equals(otherCommand.index)
                && buyersToRemove.equals(otherCommand.buyersToRemove);
    }
}
