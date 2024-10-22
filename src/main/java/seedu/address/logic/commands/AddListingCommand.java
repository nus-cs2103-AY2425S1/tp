package seedu.address.logic.commands;

import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddListingCommand extends Command {

    public static final String COMMAND_WORD = "listing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a listing to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_SIZE + "SIZE "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_AREA + "AREA "
            + PREFIX_SELLER + "SELLER "
            + "[" + PREFIX_BUYER + "BUYER]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Warton House "
            + PREFIX_PRICE + "4000 "
            + PREFIX_SIZE + "1000 "
            + PREFIX_ADDRESS + "123 PASIR RIS (S)123456 "
            + PREFIX_AREA + "200 "
            + PREFIX_SELLER + "Sean Dias "
            + PREFIX_BUYER + "Rong yi "
            + PREFIX_BUYER + "Wen Xuan ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This listing already exists in EZSTATE";
    private final Listing toAdd;

    /**
     * Creates an AddListingCommand to add the specified {@code Person}
     */
    public AddClientProfile(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

}
