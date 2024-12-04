package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Utility class containing helper methods for handling index validations in commands.
 */
public class CommandUtils {

    /**
     * Checks if the provided person index is valid.
     * Throws a CommandException if the index is out of bounds.
     *
     * @param zeroBasedIndex the zero-based index of the person
     * @param personListSize the size of the person list
     * @throws CommandException if the index is invalid (out of bounds)
     */
    public static void handleInvalidPersonIndex(int zeroBasedIndex, int personListSize) throws CommandException {
        if (zeroBasedIndex >= personListSize || zeroBasedIndex < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    /**
     * Checks if the provided seller index is valid.
     * Throws a CommandException if the index is out of bounds.
     *
     * @param zeroBased the zero-based index of the seller
     * @param personListSize the size of the person list
     * @throws CommandException if the index is invalid (out of bounds)
     */
    public static void handleInvalidSellerIndex(int zeroBased, int personListSize) throws CommandException {
        if (zeroBased >= personListSize || zeroBased < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_SELLER_INDEX);
        }
    }

    /**
     * Checks if the provided buyer index is valid.
     * Throws a CommandException if the index is out of bounds.
     *
     * @param zeroBased the zero-based index of the buyer
     * @param personListSize the size of the person list
     * @throws CommandException if the index is invalid (out of bounds)
     */
    public static void handleInvalidBuyerIndex(int zeroBased, int personListSize) throws CommandException {
        if (zeroBased >= personListSize || zeroBased < 0) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_BUYER_INDEX, zeroBased + 1));
        }
    }

    /**
     * Checks if the provided listing index is valid.
     * Throws a CommandException if the index is out of bounds.
     *
     * @param zeroBasedIndex the zero-based index of the listing
     * @param listingListSize the size of the listing list
     * @throws CommandException if the index is invalid (out of bounds)
     */
    public static void handleInvalidListingIndex(int zeroBasedIndex, int listingListSize) throws CommandException {
        if (zeroBasedIndex >= listingListSize || zeroBasedIndex < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_LISTING_DISPLAYED_INDEX);
        }
    }
}
