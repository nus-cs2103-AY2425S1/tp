package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class DeletePropertyCommand extends Command {
    public static final String COMMAND_WORD = "delprop";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the client's property corresponding to the client's name.\n"
            + "Parameters: CLIENT_NAME (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " n/Tan Wen Xuan";

    public static final String MESSAGE_DELETE_PROPERTY_SUCCESS = "Sucessfully deleted property from %1$s";

    private final Name targetName;

    public DeletePropertyCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToDeleteProperty = model.getPersonByName(targetName);
        if (!lastShownList.contains(personToDeleteProperty)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INPUT);
        }

        Person personWithoutProperty = new Person(personToDeleteProperty.getName(),
                personToDeleteProperty.getPhone(), personToDeleteProperty.getEmail(),
                personToDeleteProperty.getTags(), personToDeleteProperty.getAppointment(), new Property(""));
        model.setPerson(personToDeleteProperty, personWithoutProperty);

        return new CommandResult(String.format(MESSAGE_DELETE_PROPERTY_SUCCESS,
                personToDeleteProperty.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePropertyCommand)) {
            return false;
        }

        DeletePropertyCommand otherDeletePropertyCommand = (DeletePropertyCommand) other;
        return targetName.equals(otherDeletePropertyCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .toString();
    }
}
