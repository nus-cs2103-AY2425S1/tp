// src/main/java/seedu/address/logic/commands/DeleteYCommand.java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Confirms the deletion of a person from the address book.
 */
public class DeleteYCommand extends Command {

    public static final String COMMAND_WORD = "y";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person:\n%1$s";

    private final Person personToDelete;

    public DeleteYCommand(Person personToDelete) {
        this.personToDelete = personToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteYCommand)) {
            return false;
        }

        DeleteYCommand otherDeleteYCommand = (DeleteYCommand) other;
        return personToDelete.equals(otherDeleteYCommand.personToDelete);
    }
}
