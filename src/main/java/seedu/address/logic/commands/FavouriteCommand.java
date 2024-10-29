package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSONS_INDEX;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Represents a command to mark contacts as favourites.
 */
public class FavouriteCommand extends Command {
    public static final String COMMAND_WORD = "favourite";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " Makes a Contact have Favourite Status "
            + "Parameters: c/CONTACT_INDEXES \n"
            + "Example: " + COMMAND_WORD + " c/1 2";
    public static final String MESSAGE_SUCCESS = "Favourite contact(s) updated: %s";
    private final List<Index> contactIndexes;
    /**
     * Creates a FavouriteCommand to mark the specified contacts as favourites.
     *
     * @param contactIndexes The list of indexes of contacts to be marked as favourite.
     */
    public FavouriteCommand(List<Index> contactIndexes) {
        requireNonNull(contactIndexes);
        this.contactIndexes = contactIndexes;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        for (Index i : contactIndexes) {
            if (i.getZeroBased() >= lastShownList.size() || i.getZeroBased() < 0) {
                throw new CommandException(MESSAGE_INVALID_PERSONS_INDEX);
            }
        }
        List<Person> personsList = contactIndexes.stream()
            .map(index -> lastShownList.get(index.getZeroBased()))
            .collect(Collectors.toList());
        for (Person p : personsList) {
            Person favouritePerson = createFavouritePerson(p);
            model.setPerson(p, favouritePerson);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personsList)));
    }
    /**
     * @param personToEdit
     * @return a new Person Object with Favourite Attribute changed to true
     */
    public static Person createFavouritePerson(Person personToEdit) {
        assert personToEdit != null;
        return personToEdit.setFavouritePerson();
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        //instanceof handles nulls
        if (!(other instanceof FavouriteCommand)) {
            return false;
        }
        FavouriteCommand otherFavouriteCommand = (FavouriteCommand) other;
        return contactIndexes.equals(otherFavouriteCommand.contactIndexes);
    }
}
