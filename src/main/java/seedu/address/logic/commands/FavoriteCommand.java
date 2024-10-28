package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Marks a person identified by the index number in the displayed person list as favorite.
 */
public class FavoriteCommand extends Command {

    public static final String COMMAND_WORD = "favorite";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the person identified "
            + "by the index number used in the displayed person list as a favorite.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: "
            + COMMAND_WORD
            + " 1";

    public static final String MESSAGE_FAVORITE_PERSON_SUCCESS = "Marked Person as Favorite: %1$s";

    private final Index index;

    /**
     * Creates a FavoriteCommand to mark a person as favorite.
     *
     * @param index The index of the person in the displayed person list.
     */
    public FavoriteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    /**
     * Executes the command and marks the specified person as a favorite.
     *
     * @param model The model containing the data for the address book.
     * @return A CommandResult containing the result message.
     * @throws CommandException If the index provided is invalid or if any other issue occurs during execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToFavorite = lastShownList.get(index.getZeroBased());
        Person favoritedPerson = new Person(
                personToFavorite.getName(),
                personToFavorite.getPhone(),
                personToFavorite.getEmail(),
                personToFavorite.getAddress(),
                personToFavorite.getTags(),
                true
        );

        model.setPerson(personToFavorite, favoritedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_FAVORITE_PERSON_SUCCESS, Messages.format(favoritedPerson)));
    }

    /**
     * Checks for equality with another object.
     *
     * @param other The object to compare with.
     * @return True if both objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FavoriteCommand)) {
            return false;
        }

        FavoriteCommand otherFavoriteCommand = (FavoriteCommand) other;
        return index.equals(otherFavoriteCommand.index);
    }

    /**
     * Returns a string representation of the FavoriteCommand.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return "FavoriteCommand{" +
                "index="
                + index
                + '}';
    }
}
