package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;
import seedu.address.model.listing.Address;
import seedu.address.model.person.Person;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddListingCommand extends Command {

    public static final String COMMAND_WORD = "listing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a listing to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_AREA + "AREA "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_REGION + "REGION "
            + PREFIX_SELLER + "SELLER "
            + "[" + PREFIX_BUYER + "BUYER]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Warton House "
            + PREFIX_PRICE + "4000 "
            + PREFIX_AREA + "1000 "
            + PREFIX_ADDRESS + "123 PASIR RIS (S)123456 "
            + PREFIX_REGION + "east "
            + PREFIX_SELLER + "Sean Dias "
            + PREFIX_BUYER + "Rong yi "
            + PREFIX_BUYER + "Wen Xuan ";

    public static final String MESSAGE_SUCCESS = "New listing added: %1$s";
    public static final String MESSAGE_DUPLICATE_LISTING = "This listing already exists in EZSTATE";
    private final Name listingName;
    private final Price price;
    private final Area area;
    private final Address address;
    private final Region region;
    private final Name seller;

    private final Set<Name> buyers;


    /**
     * Creates an AddListingCommand to add the specified {@code Listing}
     */
    public AddListingCommand(Name listingName, Price price, Area area, Address address, Region region,
                                Name seller, Set<Name> buyers) {
       this.listingName = listingName;
       this.price = price;
       this.area = area;
       this.address = address;
       this.region = region;
       this.seller = seller;
       this.buyers = buyers;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        Person seller = model.getPersonByName(this.seller);
        if (!lastShownList.contains(seller)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INPUT);
        }
        Set<Person> personBuyers = new HashSet<>();
        for (Name b: buyers) {
            Person buyer = model.getPersonByName(b);
            if (!lastShownList.contains(buyer)) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INPUT);
            }
            personBuyers.add(buyer);
        }

        Listing toAdd = new Listing(listingName, address, price, area, region, seller, personBuyers);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

}
