package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using their name from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the name used in the address book.\n"
            + "Parameters: n/NAME\n"
            + "Example: " + COMMAND_WORD + " n/Li Sirui";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person:\n%1$s";
    public static final String MESSAGE_NO_MATCH_FOUND = "No contact with the name '%1$s' found.";
    public static final String MESSAGE_MISSING_NAME = "Contact name is required.";

    private final String name;

    public DeleteCommand(String name) {
        this.name = name.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (name.isEmpty()) {
            throw new CommandException(MESSAGE_MISSING_NAME);
        }

        List<Person> matchingPersons = model.getFilteredPersonList().stream()
                .filter(person -> person.getName().fullName.equalsIgnoreCase(name))
                .toList();

        if (matchingPersons.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_MATCH_FOUND, name));
        }

        Person personToDelete = matchingPersons.get(0);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.formatForDeletion(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCommand otherDeleteCommand)) {
            return false;
        }

        return name.equals(otherDeleteCommand.name);
    }
}
