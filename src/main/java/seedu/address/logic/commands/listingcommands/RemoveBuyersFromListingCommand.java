package seedu.address.logic.commands.listingcommands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
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

    public static final String COMMAND_WORD = "removelistingbuyers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes buyers from the listing identified by its "
            + "name. "
            + "Parameters: LISTING_NAME buyer/BUYER_NAME [buy/MORE_BUYER_NAMES]...\n"
            + "Example: " + COMMAND_WORD + " Warton House buy/Alice buy/Bob";

    public static final String MESSAGE_REMOVE_BUYERS_SUCCESS = "Buyers removed from listing: %1$s";
    public static final String MESSAGE_LISTING_NOT_FOUND = "The specified listing name does not exist.";
    public static final String MESSAGE_EMPTY_SET = "Please provide valid buyers";
    public static final String MESSAGE_PERSON_NOT_BUYER = "The client %1$s is not registered as a buyer.";
    public static final String MESSAGE_NOT_BUYER_FOR_LISTING =
            "The specified buyer %1$s is not a buyer of the listing %2$s.";
    public static final String MESSAGE_BUYER_NOT_FOUND = "The specified buyer %1$s does not exist in the client list.";

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


        if (!model.hasListingOfName(listingName)) {
            throw new CommandException(MESSAGE_LISTING_NOT_FOUND);
        }
        Listing listingToEdit = model.getListingByName(listingName);

        Set<Person> existingBuyers = new HashSet<>(listingToEdit.getBuyers());
        Set<Person> buyersToRemoveSet = new HashSet<>();

        for (Name buyerName : buyersToRemove) {


            if (!model.hasPersonOfName(buyerName)) {
                throw new CommandException(String.format(MESSAGE_BUYER_NOT_FOUND, buyerName));
            }

            Person buyer = model.getPersonByName(buyerName);

            // Check if the person is actually an instance of Buyer
            if (!(buyer instanceof Buyer)) {
                throw new CommandException(String.format(MESSAGE_PERSON_NOT_BUYER, buyer.getName()));
            }

            if (!existingBuyers.contains(buyer)) {
                throw new CommandException(String.format(MESSAGE_NOT_BUYER_FOR_LISTING, buyer.getName(), listingName));
            }

            buyersToRemoveSet.add(buyer);
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
        return new CommandResult(String.format(MESSAGE_REMOVE_BUYERS_SUCCESS, listingName));
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
