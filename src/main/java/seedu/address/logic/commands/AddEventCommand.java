package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import seedu.address.model.event.Event;

public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_EVENT_TIME + "TIME "
            + PREFIX_EVENT_VENUE + "VENUE "
            + PREFIX_EVENT_CONTACT + "CONTACT "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Oscars"
            + PREFIX_EVENT_TIME + "Sep 22 2024 1800 to 2200 "
            + PREFIX_EVENT_VENUE + "Hollywood "
            + PREFIX_EVENT_CONTACT + "Sydney Sweeney ";

    public static final String MESSAGE_SUCCESS = "New Event added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This event  already exists in the address book";
    private final Event toAdd;

    public AddEventCommand(Event event) {
        requireNonNull(event);
        this.toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
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
