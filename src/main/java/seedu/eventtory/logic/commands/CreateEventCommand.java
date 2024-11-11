package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.eventtory.commons.util.ToStringBuilder;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.event.Event;

/**
 * Creates an event in EventTory.
 */
public class CreateEventCommand extends CreateCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new event in EventTory.\n"
            + "Parameters: "
            + PREFIX_EVENT + " "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE" + "\n"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: "
            + COMMAND_WORD + " " + PREFIX_EVENT + " "
            + PREFIX_NAME + "John Baby Shower "
            + PREFIX_DATE + "2021-10-10 "
            + PREFIX_TAG + "celebration";

    public static final String MESSAGE_SUCCESS = "New event created: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in EventTory";
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
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
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
