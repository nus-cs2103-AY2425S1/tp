package seedu.address.logic.commands.listingcommands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandUtils;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;

/**
 * Adds buyers to an existing listing in the system.
 */
public class AddBuyersToListingCommand extends Command {

    public static final String COMMAND_WORD = "addlistingbuyers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds buyers to the listing identified by its index. "
            + "Parameters: LISTING_NAME buy/BUYER_NAME [buy/MORE_BUYER_NAMES]...\n"
            + "Example: " + COMMAND_WORD + " 1 buy/1 buy/3";

    public static final String MESSAGE_ADD_BUYERS_SUCCESS = "Buyers added to listing: %1$s";
    public static final String MESSAGE_PERSON_NOT_BUYER = "The specified person is not a buyer:\n"
            + "%d. %s";
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

        CommandUtils.handleInvalidListingIndex(zeroBased, lastShownListingList.size());

        Listing listingToEdit = lastShownListingList.get(zeroBased);
        Set<Person> existingBuyers = listingToEdit.getBuyers();
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        Set<Person> updatedBuyers = addBuyers(existingBuyers, lastShownPersonList);
        Listing updatedListing = ListingCommandsUtil.updateListingWithBuyers(listingToEdit, updatedBuyers);

        model.setListing(listingToEdit, updatedListing);
        return new CommandResult(String.format(MESSAGE_ADD_BUYERS_SUCCESS, Messages.format(listingToEdit)));
    }

    private Set<Person> addBuyers(Set<Person> existingBuyers, List<Person> lastShownPersonList)
            throws CommandException {
        Set<Person> updatedBuyers = new HashSet<>(existingBuyers);

        for (Index buyerIndex : buyersToAdd) {
            int zeroBasedBuyer = buyerIndex.getZeroBased();
            CommandUtils.handleInvalidPersonIndex(zeroBasedBuyer, lastShownPersonList.size());

            Person buyerToAdd = lastShownPersonList.get(zeroBasedBuyer);
            ListingCommandsUtil.verifyPersonIsNotBuyer(buyerToAdd, buyerToAdd.getRole(), buyerIndex);

            // Add the buyer to newBuyers set only if not already in existingBuyers
            if (!existingBuyers.contains(buyerToAdd)) {
                updatedBuyers.add(buyerToAdd);
            }
        }

        return updatedBuyers;
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
