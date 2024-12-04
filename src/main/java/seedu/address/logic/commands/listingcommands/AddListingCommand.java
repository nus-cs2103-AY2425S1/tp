package seedu.address.logic.commands.listingcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandUtils;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.name.Name;
import seedu.address.model.person.Person;

/**
 * Adds a listing to the address book.
 */
public class AddListingCommand extends Command {

    public static final String COMMAND_WORD = "listing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a listing to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_AREA + "AREA "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_REGION + "REGION "
            + PREFIX_SELLER + "SELLER INDEX "
            + "[" + PREFIX_BUYER + "BUYER INDEX]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Warton House "
            + PREFIX_PRICE + "400000 "
            + PREFIX_AREA + "1000 "
            + PREFIX_ADDRESS + "123 PASIR RIS (S)123456 "
            + PREFIX_REGION + "east "
            + PREFIX_SELLER + "2 "
            + PREFIX_BUYER + "1 "
            + PREFIX_BUYER + "3 ";

    public static final String MESSAGE_SUCCESS = "New listing added: %1$s";
    public static final String MESSAGE_NOT_SELLER = "The seller index specified is not a seller:\n"
            + "%d. %s";
    public static final String MESSAGE_NOT_BUYER = "The buyer index specified is not a buyer:\n"
            + "%d. %s";
    public static final String MESSAGE_DUPLICATE_LISTING = "This listing already exists in EZSTATE!";
    private final Name listingName;
    private final Price price;
    private final Area area;
    private final Address address;
    private final Region region;
    private final Index sellerIndex;

    private final Set<Index> buyerIndexes;


    /**
     * Creates an AddListingCommand to add the specified {@code Listing}
     */
    public AddListingCommand(Name listingName, Price price, Area area, Address address, Region region,
                                Index sellerIndex, Set<Index> buyerIndexes) {
        requireAllNonNull(listingName, price, area, address, region, sellerIndex);
        this.listingName = listingName;
        this.price = price;
        this.area = area;
        this.address = address;
        this.region = region;
        this.sellerIndex = sellerIndex;
        this.buyerIndexes = buyerIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownPersonList = model.getFilteredPersonList();
        int zeroBasedSeller = sellerIndex.getZeroBased();
        CommandUtils.handleInvalidSellerIndex(zeroBasedSeller, lastShownPersonList.size());

        Person seller = lastShownPersonList.get(zeroBasedSeller);

        // Check if the person is actually an instance of a Seller
        ListingCommandsUtil.handleNonSeller(seller, seller.getRole(), sellerIndex);

        // Gets buyers for the new listing
        Set<Person> listingBuyers = getBuyers(buyerIndexes, lastShownPersonList);

        Listing toAdd = new Listing(listingName, address, price, area, region, seller, listingBuyers);

        if (model.hasListing(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LISTING);
        }

        model.addListing(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    private Set<Person> getBuyers(Set<Index> buyerIndexes, List<Person> lastShownPersonList) throws CommandException {
        Set<Person> listingBuyers = new HashSet<>();

        if (!buyerIndexes.isEmpty()) {
            for (Index buyerIndex : buyerIndexes) {
                int zeroBasedBuyer = buyerIndex.getZeroBased();
                CommandUtils.handleInvalidBuyerIndex(zeroBasedBuyer, lastShownPersonList.size());

                Person buyer = lastShownPersonList.get(zeroBasedBuyer);

                // Check if the person is actually an instance of a Buyer
                ListingCommandsUtil.handleNonBuyer(buyer, buyer.getRole(), buyerIndex);
                listingBuyers.add(buyer);
            }
        }

        return listingBuyers;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddListingCommand)) {
            return false;
        }

        AddListingCommand otherCommand = (AddListingCommand) other;
        boolean hasSameListingName = listingName.equals(otherCommand.listingName);
        boolean hasSamePrice = price.equals(otherCommand.price);
        boolean hasSameArea = area.equals(otherCommand.area);
        boolean hasSameAddress = address.equals(otherCommand.address);
        boolean hasSameRegion = region.equals(otherCommand.region);
        boolean hasSameSellerIndex = sellerIndex.equals(otherCommand.sellerIndex);
        boolean hasSameBuyerIndexes = buyerIndexes.equals(otherCommand.buyerIndexes);

        return hasSameListingName && hasSamePrice && hasSameArea && hasSameAddress && hasSameRegion
                && hasSameSellerIndex && hasSameBuyerIndexes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", this.listingName)
                .add("address", this.address)
                .toString();
    }
}
