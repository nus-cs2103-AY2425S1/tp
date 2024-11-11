package seedu.address.logic.commands.listingcommands;

import static seedu.address.logic.commands.listingcommands.AddBuyersToListingCommand.MESSAGE_PERSON_NOT_BUYER;
import static seedu.address.logic.commands.listingcommands.AddListingCommand.MESSAGE_NOT_BUYER;
import static seedu.address.logic.commands.listingcommands.AddListingCommand.MESSAGE_NOT_SELLER;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.name.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Utility class containing helper methods for various Listing-related commands.
 */
public class ListingCommandsUtil {

    /**
     * Throws an exception if the person is not a Buyer. The error message is formatted using
     * {@link seedu.address.logic.commands.listingcommands.AddBuyersToListingCommand#MESSAGE_PERSON_NOT_BUYER}
     *
     * @param person the person to check
     * @param role the role of the person
     * @param buyerIndex the index of the buyer in the list
     * @throws CommandException if the person is a seller
     */
    public static void verifyPersonIsNotBuyer(Person person, Role role, Index buyerIndex) throws CommandException {
        if (!role.equals(Role.BUYER)) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_BUYER,
                    buyerIndex.getOneBased(), person.getName()));
        }
    }

    /**
     * Throws an exception if the person is not a Buyer. The error message is
     * formatted using {@link seedu.address.logic.commands.listingcommands.AddListingCommand#MESSAGE_NOT_BUYER}
     *
     * @param person the person to check
     * @param role the role of the person
     * @param buyerIndex the index of the buyer in the list
     * @throws CommandException if the person is a seller
     */
    public static void handleNonBuyer(Person person, Role role, Index buyerIndex) throws CommandException {
        if (!role.equals(Role.BUYER)) {
            throw new CommandException(String.format(MESSAGE_NOT_BUYER,
                    buyerIndex.getOneBased(), person.getName()));
        }
    }

    /**
     * Throws an exception if the person is not a Seller.
     *
     * @param person the person to check
     * @param role the role of the person
     * @param sellerIndex the index of the seller in the list
     * @throws CommandException if the person is a buyer
     */
    public static void handleNonSeller(Person person, Role role, Index sellerIndex) throws CommandException {
        if (!role.equals(Role.SELLER)) {
            throw new CommandException(String.format(MESSAGE_NOT_SELLER,
                    sellerIndex.getOneBased(), person.getName()));
        }
    }

    /**
     * Updates the given listing with a new set of buyers.
     *
     * @param listingToEdit the listing to be updated
     * @param updatedBuyers the set of updated buyers
     * @return a new Listing object with the updated buyers
     */
    public static Listing updateListingWithBuyers(Listing listingToEdit, Set<Person> updatedBuyers) {
        Name name = listingToEdit.getName();
        Address address = listingToEdit.getAddress();
        Price price = listingToEdit.getPrice();
        Area area = listingToEdit.getArea();
        Region region = listingToEdit.getRegion();
        Person seller = listingToEdit.getSeller();

        return new Listing(name, address, price, area, region, seller, updatedBuyers);
    }
}
