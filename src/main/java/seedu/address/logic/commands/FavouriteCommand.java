package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.tag.Tag.FAVOURITE_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;


/**
 * Command to sort the contacts in the address book based on their "favourite" status.
 * The contacts can either be sorted with favourites brought to the top, or
 * a specific contact can be marked as a favourite based on an index.
 */
public class FavouriteCommand extends Command {
    public static final String COMMAND_WORD = "favourite";
    public static final String MESSAGE_FAVOURITE_SORTED = "Favourite contacts are brought to the list top.";
    public static final String MESSAGE_FAVOURITE_ADDED = "Add %1s to favourite.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a person as a favourite or sorts all favourite persons to the top of the list.\n"
            + "Two usage formats are supported:\n"
            + "1. To mark a person as favourite: Specify the index of the person in the displayed list.\n"
            + "   Parameters: INDEX (must be a positive integer)\n"
            + "   Example: " + COMMAND_WORD + " 1\n"
            + "2. To sort all favourite persons to the top: Use the command without any parameters.\n"
            + "   Example: " + COMMAND_WORD;

    private Index index = null;

    /**
     * Constructs a {@code FavouriteCommand} with the specified index,
     * marking the contact at that index as a favourite.
     *
     * @param index The index of the person to be marked as a favourite.
     */
    public FavouriteCommand(Index index) {
        this.index = index;
    }
    /**
     * Default constructor for sorting contacts, bringing favourite contacts to the top of the list.
     */
    public FavouriteCommand() {
    }

    /**
     * Executes the favourite command.
     * If an index is provided, it marks the contact at the specified index as a favourite.
     * If no index is provided, it sorts the contact list by bringing favourite contacts to the top.
     *
     * @param model The model containing the list of contacts.
     * @return The result of executing the favourite command, either marking a contact as favourite
     *         or sorting the contact list.
     * @throws CommandException If the specified index is invalid (i.e., out of range).
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (index == null) {
            model.sortPersonsFavourite();
            return new CommandResult(MESSAGE_FAVOURITE_SORTED);
        } else {
            List<Person> lastShownList = model.getFilteredPersonList();

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToEdit = lastShownList.get(index.getZeroBased());
            Set<Tag> addedFavouriteTagSet = new HashSet<>(personToEdit.getTags());
            addedFavouriteTagSet.add(FAVOURITE_TAG);
            Person editedPerson = new Person(
                    personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getBirthday(),
                    addedFavouriteTagSet,
                    personToEdit.getDateOfCreation(),
                    personToEdit.getHistory());
            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_FAVOURITE_ADDED, editedPerson));
        }
    }
    /**
     * Checks whether two {@code FavouriteCommand} objects are equal.
     *
     * @param obj The object to be compared with this {@code FavouriteCommand}.
     * @return {@code true} if both objects refer to the same command or have the same index,
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Check if both references point to the same object
        }
        if (!(obj instanceof FavouriteCommand that)) {
            return false; // Check if obj is of the correct type
        }
        // Compare the index fields, ensuring that null is handled correctly
        return (this.index == null && that.index == null) || (this.index != null && this.index.equals(that.index));
    }

}
