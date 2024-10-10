package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

public class CreateEventCommand extends Command {
    public static final String COMMAND_WORD = "create_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new event in the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE";

    public static final String MESSAGE_SUCCESS = "New event created: %1$s";
    public static final String MESSAGE_DUPLICATE_VENDOR = "This event already exists in the address book";
    public static final String MESSAGE_INVALID_DATE = "Date specified is invalid";
    public static final String MESSAGE_EMPTY_NAME = "Cannot create event without a name";
    public static final String MESSAGE_INVALID_NAME = "Cannot create event with invalid name";
    public static final String MESSAGE_UNIMPLEMENTED = "CreateEventCommand is unimplemented";

    private final Event toAdd;

    /**
     * Creates a CreateEventCommand to add the specified {@code Event}
     */
    public CreateEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(MESSAGE_UNIMPLEMENTED);

        // TODO validation checks for event
        // TODO add event to model
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CreateEventCommand)) {
            return false;
        }

        CreateEventCommand otherCreateEventCommand = (CreateEventCommand) other;
        return toAdd.equals(otherCreateEventCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
