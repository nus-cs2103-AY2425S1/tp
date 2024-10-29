package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Adds buyers to an existing listing in the system.
 */
public class AddBuyersToListingCommand extends Command {

    public static final String COMMAND_WORD = "addBuyersToListing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds buyers to the listing identified by its name. "
            + "Parameters: LISTING_NAME buyer/BUYER_NAME [buyer/MORE_BUYER_NAMES]...\n"
            + "Example: " + COMMAND_WORD + " Warton House buyer/Alice buyer/Bob";

    public static final String MESSAGE_ADD_BUYERS_SUCCESS = "Buyers added to listing: %1$s";
    public static final String MESSAGE_LISTING_NOT_FOUND = "The specified listing name does not exist.";
    public static final String MESSAGE_DUPLICATE_BUYERS = "Some buyers are already associated with this listing.";

    private final Name listingName;
    private final Set<Name> buyersToAdd;

    /**
     * Constructs an {@code AddBuyersToListingCommand}.
     *
     * @param listingName The name of the listing to which buyers will be added.
     * @param buyersToAdd The names of the buyers to add to the listing.
     */
    public AddBuyersToListingCommand(Name listingName, Set<Name> buyersToAdd) {
        requireNonNull(listingName);
        requireNonNull(buyersToAdd);

        this.listingName = listingName;
        this.buyersToAdd = new HashSet<>(buyersToAdd);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Listing listingToEdit = model.getListingByName(listingName);
        if (listingToEdit == null) {
            throw new CommandException(MESSAGE_LISTING_NOT_FOUND);
        }

        // Collect existing buyers and add new ones
        Set<Person> existingBuyers = new HashSet<>(listingToEdit.getBuyers());
        Set<Person> newBuyers = buyersToAdd.stream()
                .map(model::getPersonByName)
                .filter(buyer -> buyer != null && !existingBuyers.contains(buyer))
                .collect(Collectors.toSet());

        if (newBuyers.isEmpty()) {
            throw new CommandException(MESSAGE_DUPLICATE_BUYERS);
        }

        existingBuyers.addAll(newBuyers);

        // Create a modified listing with updated buyers
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
        return new CommandResult(String.format(MESSAGE_ADD_BUYERS_SUCCESS, updatedListing));
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
        return listingName.equals(otherCommand.listingName)
                && buyersToAdd.equals(otherCommand.buyersToAdd);
    }
}
