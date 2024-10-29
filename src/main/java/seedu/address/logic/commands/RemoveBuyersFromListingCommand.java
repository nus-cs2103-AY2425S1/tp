package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Removes buyers from an existing listing in the system.
 */
public class RemoveBuyersFromListingCommand extends Command {

    public static final String COMMAND_WORD = "removeBuyersFromListing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes buyers from the listing identified by its "
            + "name. "
            + "Parameters: LISTING_NAME buyer/BUYER_NAME [buyer/MORE_BUYER_NAMES]...\n"
            + "Example: " + COMMAND_WORD + " Warton House buyer/Alice buyer/Bob";

    public static final String MESSAGE_REMOVE_BUYERS_SUCCESS = "Buyers removed from listing: %1$s";
    public static final String MESSAGE_LISTING_NOT_FOUND = "The specified listing name does not exist.";
    public static final String MESSAGE_BUYER_NOT_FOUND = "Some buyers are not associated with this listing.";

    private final Name listingName;
    private final Set<Name> buyersToRemove;

    /**
     * Constructs a {@code RemoveBuyersFromListingCommand}.
     *
     * @param listingName The name of the listing from which buyers will be removed.
     * @param buyersToRemove The names of the buyers to remove from the listing.
     */
    public RemoveBuyersFromListingCommand(Name listingName, Set<Name> buyersToRemove) {
        requireNonNull(listingName);
        requireNonNull(buyersToRemove);

        this.listingName = listingName;
        this.buyersToRemove = new HashSet<>(buyersToRemove);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Listing listingToEdit = model.getListingByName(listingName);
        if (listingToEdit == null) {
            throw new CommandException(MESSAGE_LISTING_NOT_FOUND);
        }

        Set<Person> existingBuyers = new HashSet<>(listingToEdit.getBuyers());
        Set<Person> buyersToRemoveSet = new HashSet<>();

        for (Name buyerName : buyersToRemove) {
            Optional<Person> buyer = Optional.ofNullable(model.getPersonByName(buyerName));

            if (buyer.isEmpty()) {
                throw new CommandException("The specified buyer " + buyerName + " does not exist in the client list.");
            }

            // Check if the person is actually an instance of Buyer and is in the existing buyers
            if (!(buyer.get() instanceof Buyer) || !existingBuyers.contains(buyer.get())) {
                throw new CommandException("The specified person " + buyerName + " is not a buyer or is not associated "
                        + "with this listing.");
            }

            buyersToRemoveSet.add(buyer.get());
        }

        if (buyersToRemoveSet.isEmpty()) {
            throw new CommandException(MESSAGE_BUYER_NOT_FOUND);
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
        return new CommandResult(String.format(MESSAGE_REMOVE_BUYERS_SUCCESS, updatedListing));
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
        return listingName.equals(otherCommand.listingName)
                && buyersToRemove.equals(otherCommand.buyersToRemove);
    }
}
