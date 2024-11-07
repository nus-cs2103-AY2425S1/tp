package seedu.address.logic.commands.listingcommands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * Adds buyers to an existing listing in the system.
 */
public class AddBuyersToListingCommand extends Command {

    public static final String COMMAND_WORD = "addlistingbuyers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds buyers to the listing identified by its index. "
            + "Parameters: LISTING_INDEX buyer/BUYER_INDEX [buyer/MORE_BUYER_INDEXES]...\n"
            + "Example: " + COMMAND_WORD + " 1 buyer/1 buyer/3";

    public static final String MESSAGE_ADD_BUYERS_SUCCESS = "Buyers added to listing: %1$s";
    public static final String MESSAGE_LISTING_NOT_FOUND = "The specified listing name does not exist.";
    public static final String MESSAGE_DUPLICATE_BUYERS = "%1$s is already associated with this listing.";
    public static final String MESSAGE_BUYER_NOT_FOUND = "The specified buyer %1$s does not exist in the client list.";
    public static final String MESSAGE_PERSON_NOT_BUYER = "The specified person %1$s is not a buyer.";
    private final Index index;
    private final Set<Index> buyersToAdd;

    /**
     * Constructs an {@code AddBuyersToListingCommand}.
     *
     * @param index The index of the listing to which buyers will be added.
     * @param buyersToAdd The indexes of the buyers to add to the listing.
     */
    public AddBuyersToListingCommand(Index index, Set<Index> buyersToAdd) {
        requireNonNull(index);
        requireNonNull(buyersToAdd);

        this.index = index;
        this.buyersToAdd = new HashSet<>(buyersToAdd);
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
        Set<Person> newBuyers = new HashSet<>();
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        for (Index buyerIndex : buyersToAdd) {
            int zeroBasedBuyer = buyerIndex.getZeroBased();
            if (zeroBasedBuyer >= lastShownPersonList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person buyerToAdd = lastShownPersonList.get(zeroBasedBuyer);

            // Check if the person is actually an instance of Buyer
            if (!buyerToAdd.getRole().equals(Role.BUYER)) {
                throw new CommandException(String.format(MESSAGE_PERSON_NOT_BUYER, buyerToAdd.getName()));
            }

            // Add the buyer to newBuyers set only if not already in existingBuyers
            if (!existingBuyers.contains(buyerToAdd)) {
                newBuyers.add(buyerToAdd);
            }

        }

        existingBuyers.addAll(newBuyers);

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
        return new CommandResult(String.format(MESSAGE_ADD_BUYERS_SUCCESS, Messages.format(listingToEdit)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddBuyersToListingCommand)) {
            return false;
        }

        AddBuyersToListingCommand otherCommand = (AddBuyersToListingCommand) other;
        return index.equals(otherCommand.index)
                && buyersToAdd.equals(otherCommand.buyersToAdd);
    }
}
