package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Command to toggle the favorite status of a person in the address book.
 */
public class FavoriteCommand extends Command {
    public static final String COMMAND_WORD = "favorite";

    private final Name name;

    /**
     * Creates a FavoriteCommand to toggle the favorite status of the specified {@code Person}.
     *
     * @param name The name of the person whose favorite status is to be toggled.
     */
    public FavoriteCommand(Name name) {
        this.name = name;
    }

    /**
     * Executes the command to toggle the favorite status of the person identified by the name.
     *
     * @param model The model containing the address book data.
     * @return A CommandResult indicating the outcome of the command execution.
     * @throws CommandException If the person with the specified name is not found in the address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        Optional<Person> optionalPerson = model.getPersonByName(name);
        if (optionalPerson.isEmpty()) {
            throw new CommandException("Person not found: " + name);
        }

        Person personToUpdate = optionalPerson.get();
        personToUpdate.setFavorite(!personToUpdate.isFavorite()); // Toggle favorite status

        model.setPerson(personToUpdate, personToUpdate); // Update the person in the model
        return new CommandResult(String.format("Favorite status updated for: %s", personToUpdate.getName()));
    }
}
