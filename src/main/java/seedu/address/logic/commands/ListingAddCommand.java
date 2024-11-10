package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueListingList;
import seedu.address.model.person.exceptions.DuplicateListingException;

/**
 * Changes the remark of an existing person in the address book.
 */
public class ListingAddCommand extends Command {

    public static final String COMMAND_WORD_PREFIX = "listing";
    public static final String COMMAND_WORD_SUFFIX = "add";
    public static final String COMMAND_WORD = COMMAND_WORD_PREFIX + " " + COMMAND_WORD_SUFFIX;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a property listing to a client "
            + "by the index number passed into INDEX parameter. "
            + "Multiple types of each property can exist, but the address must be unique.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "t/PROPERTY_TAG a/ADDRESS\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "t/hdb a/NUS avenue 123";

    public static final String MESSAGE_ADDED_LISTING_SUCCESS = "Added property listing to client: %1$s";
    public static final String HELP_LISTING_ADD_COMMAND = "Property Listing Add Command\n"
            + "- Format: listing add INDEX t/PROPERTY_TAG a/address\n"
            + "- Example: listing add 1 t/hdb a/123 NUS Street\n"
            + "- The list of valid PROPERTY_TAG are: HDB, CONDO, RESIDENTIAL, LANDED, EC, COMMERCIAL,\n"
            + "  RETAIL, INDUSTRIAL, OFFICE, WAREHOUSE, SHOPHOUSE, TERRACE,\n"
            + "  SEMIDET, BUNGALOW, DETACHED, GCB, PENTHOUSE, MIXED,\n"
            + "  SERVAPT, DORM";

    private final Listing listing;
    private final Index index;

    /**
     * @param listing The property listing
     */
    public ListingAddCommand(Listing listing, Index index) {
        this.listing = listing;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Add new listing to the Person's existing listings
        UniqueListingList editedListings = new UniqueListingList(personToEdit.getListings());
        try {
            editedListings.add(listing);
        } catch (DuplicateListingException e) {
            throw new CommandException(e.getMessage());
        }


        // Finally create the new Person object
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getRemark(),
                editedListings);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);


        return new CommandResult(generateSuccessMessage(editedPerson,
                MESSAGE_ADDED_LISTING_SUCCESS),
                personToEdit, editedPerson, false);
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit, String message) {
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListingAddCommand)) {
            return false;
        }

        ListingAddCommand e = (ListingAddCommand) other;
        return index.equals(e.index)
                && listing.equals(e.listing);
    }

}
