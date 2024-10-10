package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_CELEBRITY;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends AddCommand {

    public static final String COMMAND_FIELD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_EVENT_TIME + "TIME "
            + PREFIX_EVENT_VENUE + "VENUE "
            + PREFIX_EVENT_CELEBRITY + "CELEBRITY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Oscars"
            + PREFIX_EVENT_TIME + "Sep 22 2024 1800 to 2200 "
            + PREFIX_EVENT_VENUE + "Hollywood "
            + PREFIX_EVENT_CELEBRITY + "Sydney Sweeney ";

    public static final String MESSAGE_SUCCESS = "New Event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event  already exists in the address book";
    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        this.toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.eventFormat(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddEventCommand)) {
            return false;
        }

        AddEventCommand otherAddEventCommand = (AddEventCommand) other;
        return toAdd.equals(otherAddEventCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
