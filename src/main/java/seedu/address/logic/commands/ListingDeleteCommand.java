package seedu.address.logic.commands;

import static seedu.address.logic.commands.ListingAddCommand.COMMAND_WORD_PREFIX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueListingList;
import seedu.address.model.person.exceptions.ListingNotFoundException;

/**
 * Changes the remark of an existing person in the address book.
 */
public class ListingDeleteCommand extends Command {

    public static final String COMMAND_WORD_SUFFIX = "delete";
    public static final String COMMAND_WORD = COMMAND_WORD_PREFIX + " " + COMMAND_WORD_SUFFIX;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a property listing from a Person "
            + "by the index number passed into INDEX parameter and LISTING_INDEX parameter"
            + "Multiple types of each property can exist, but the address must be unique.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "LISTING_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 1";

    public static final String MESSAGE_DELETE_LISTING_SUCCESS = "Deleted property listing from Person: %1$s";
    public static final String HELP_LISTING_DELETE_COMMAND = "Property Listing Delete Command\n"
            + "- Format: listing delete INDEX LISTING_INDEX\n"
            + "- Example: listing delete 1 1";

    private final Index personIndex;
    private final Index listingsIndex;

    /**
     * @param personIndex The index of the Person to target
     * @param listingsIndex The index of the listing of the UniqueListingList of a Person
     */
    public ListingDeleteCommand(Index personIndex, Index listingsIndex) {
        this.personIndex = personIndex;
        this.listingsIndex = listingsIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size() || personIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());

        // Remove listing from the Person's existing listings
        UniqueListingList editedListings = new UniqueListingList(personToEdit.getListings());
        try {
            editedListings.remove(listingsIndex.getZeroBased());
        } catch (ListingNotFoundException e) {
            throw new CommandException(e.getMessage());
        }

        // Finally create the new Person object
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getRemark(),
                editedListings);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(
                generateSuccessMessage(editedPerson, MESSAGE_DELETE_LISTING_SUCCESS),
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
        if (!(other instanceof ListingDeleteCommand)) {
            return false;
        }

        ListingDeleteCommand e = (ListingDeleteCommand) other;
        return personIndex.equals(e.personIndex)
                && listingsIndex.equals(e.listingsIndex);
    }

}
