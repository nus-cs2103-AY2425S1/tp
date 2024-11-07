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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

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
    public static final String MESSAGE_INVALID_SELLER_INDEX = "The seller index provided is invalid!";
    public static final String MESSAGE_INVALID_BUYER_INDEX = "The buyer index (%d) provided is invalid!";
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

        List<Person> lastShownList = model.getFilteredPersonList();
        int zeroBasedSeller = sellerIndex.getZeroBased();
        int oneBasedSeller = sellerIndex.getOneBased();
        if (zeroBasedSeller >= lastShownList.size() || zeroBasedSeller < 0) {
            throw new CommandException(MESSAGE_INVALID_SELLER_INDEX);
        }

        Person seller = lastShownList.get(zeroBasedSeller);

        if (!seller.getRole().equals(Role.SELLER)) {
            throw new CommandException(String.format(MESSAGE_NOT_SELLER,
                    oneBasedSeller, seller.getName()));
        }

        Set<Person> personBuyers = new HashSet<>();

        if (!buyerIndexes.isEmpty()) {
            for (Index buyerIndex : buyerIndexes) {
                int zeroBasedBuyer = buyerIndex.getZeroBased();
                int oneBasedBuyer = buyerIndex.getOneBased();
                if (zeroBasedBuyer >= lastShownList.size() || zeroBasedBuyer < 0) {
                    throw new CommandException(String.format(MESSAGE_INVALID_BUYER_INDEX, oneBasedBuyer));
                }

                Person buyer = lastShownList.get(zeroBasedBuyer);

                if (!buyer.getRole().equals(Role.BUYER)) {
                    throw new CommandException(String.format(MESSAGE_NOT_BUYER,
                            oneBasedBuyer, buyer.getName()));
                }

                personBuyers.add(buyer);
            }
        }

        Listing toAdd = new Listing(listingName, address, price, area, region, seller, personBuyers);

        if (model.hasListing(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LISTING);
        }

        model.addListing(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
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
        return listingName.equals(otherCommand.listingName)
                && price.equals(otherCommand.price)
                && area.equals(otherCommand.area)
                && address.equals(otherCommand.address)
                && region.equals(otherCommand.region)
                && sellerIndex.equals(otherCommand.sellerIndex)
                && buyerIndexes.equals(otherCommand.buyerIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", this.listingName)
                .add("address", this.address)
                .add("seller", this.sellerIndex)
                .toString();
    }
}
