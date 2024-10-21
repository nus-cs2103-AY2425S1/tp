package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import java.util.Optional;

public class FavoriteCommand extends Command {
    public static final String COMMAND_WORD = "favorite";

    private final Name name;

    public FavoriteCommand(Name name) {
        this.name = name;
    }

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
