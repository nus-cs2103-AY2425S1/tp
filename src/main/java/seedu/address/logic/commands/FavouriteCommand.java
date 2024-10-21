package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.tag.Tag.FAVOURITE_TAG;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;


/**
 * Command to sort the contacts in the address book.
 * The contacts can be sorted in either ascending (A-Z) or descending (Z-A) order based on their names.
 */
public class FavouriteCommand extends Command {
    public static final String COMMAND_WORD = "favourite";
    public static final String MESSAGE_FAVOURITE_SORTED = "Favourite contacts are brought to the list top.";
    public static final String MESSAGE_FAVOURITE_ADDED = "Add %1s to favourite.";
    private Index index = null;

    /**
     * Constructs a SortCommand with the specified sorting order.
     */
    public FavouriteCommand(Index index) {
        this.index = index;
    }
    /**
     * Default constructor for group favourite command
     * */
    public FavouriteCommand() {
    }

    /**
     * Executes the sort command, sorting the contact list based on the specified order.
     *
     * @param model The model containing the list of contacts.
     * @return The result of executing the sort command, including a success message and the sorted contact list.
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Check if both references point to the same object
        }
        if (!(obj instanceof FavouriteCommand that)) {
            return false; // Check if obj is of the correct type
        }
        // Cast obj to SortCommand
        return index == that.index; // Compare the order strings for equality
    }
}
