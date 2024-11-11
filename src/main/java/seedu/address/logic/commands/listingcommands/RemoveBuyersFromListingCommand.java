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
import seedu.address.logic.commands.CommandUtils;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;

/**
 * Removes buyers from an existing listing in the system.
 */
public class RemoveBuyersFromListingCommand extends Command {

    public static final String COMMAND_WORD = "removelistingbuyers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes buyers from the listing identified by its "
            + "index number used in the displayed listing list "
            + "Parameters: LISTING_INDEX buy/BUYER_INDEX [buy/MORE_BUYER_INDEXES]...\n"
            + "Example: " + COMMAND_WORD + " 1 buy/1 buy/3";

    public static final String MESSAGE_REMOVE_BUYERS_SUCCESS = "Buyers removed from listing: %1$s.\n"
            + "Removed buyers: %2$s";
    public static final String MESSAGE_EMPTY_SET = "Please provide valid buyers";
    public static final String MESSAGE_PERSON_NOT_BUYER = "The specified person is not a buyer:\n"
            + "%d. %s";
    public static final String MESSAGE_NOT_BUYER_FOR_LISTING =
            "The specified buyer %1$s is not a buyer of the listing %2$s.";

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
        CommandUtils.handleInvalidListingIndex(zeroBased, lastShownListingList.size());

        Listing listingToEdit = lastShownListingList.get(zeroBased);

        Set<Person> existingBuyers = new HashSet<>(listingToEdit.getBuyers());
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        Set<Person> buyersToRemoveSet = getBuyersToRemove(existingBuyers, lastShownPersonList, listingToEdit);
        existingBuyers.removeAll(buyersToRemoveSet);

        Listing updatedListing = ListingCommandsUtil.updateListingWithBuyers(listingToEdit, existingBuyers);

        model.setListing(listingToEdit, updatedListing);

        return new CommandResult(generateSuccessMessage(listingToEdit, buyersToRemoveSet));
    }

    private Set<Person> getBuyersToRemove(Set<Person> existingBuyers,
                                     List<Person> lastShownPersonList, Listing listingToEdit) throws CommandException {
        Set<Person> buyersToRemoveSet = new HashSet<>();

        for (Index buyerIndex : buyersToRemove) {
            int zeroBasedBuyer = buyerIndex.getZeroBased();
            CommandUtils.handleInvalidPersonIndex(zeroBasedBuyer, lastShownPersonList.size());

            Person buyerToRemove = lastShownPersonList.get(zeroBasedBuyer);

            // Check if the person is actually an instance of Buyer
            ListingCommandsUtil.verifyPersonIsNotBuyer(buyerToRemove, buyerToRemove.getRole(), buyerIndex);
            // Check if the person is a buyer of the listing
            validateBuyerExistsInListing(existingBuyers, buyerToRemove, listingToEdit);

            buyersToRemoveSet.add(buyerToRemove);
        }

        if (buyersToRemoveSet.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_SET);
        }

        return buyersToRemoveSet;
    }

    private void validateBuyerExistsInListing(Set<Person> existingBuyers,
                                              Person buyerToRemove, Listing listingToEdit) throws CommandException {
        if (!existingBuyers.contains(buyerToRemove)) {
            throw new CommandException(String.format(MESSAGE_NOT_BUYER_FOR_LISTING,
                    buyerToRemove.getName(), Messages.format(listingToEdit)));
        }
    }

    private String generateSuccessMessage(Listing listingToEdit, Set<Person> buyersToRemoveSet) {

        String removedNames = buyersToRemoveSet.stream()
                .map(buyer -> buyer.getName().toString()).collect(Collectors.joining(", "));
        return String.format(MESSAGE_REMOVE_BUYERS_SUCCESS, listingToEdit.getName(), removedNames);
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
        boolean hasSameIndex = index.equals(otherCommand.index);
        boolean hasSameBuyersToRemove = buyersToRemove.equals(otherCommand.buyersToRemove);

        return hasSameIndex && hasSameBuyersToRemove;
    }
}
