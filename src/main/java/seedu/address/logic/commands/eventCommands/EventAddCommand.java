package seedu.address.logic.commands.eventCommands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_START_TIME;

public class EventAddCommand extends Command {
    private static final String MESSAGE_DUPLICATE_EVENT = "This event already exists!";
    private static final String MESSAGE_SUCCESS = "Event added succesfully!";
    private final Event toAdd;
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the calander. "
            + "Parameters: "
            + EVENT_PREFIX_NAME + "NAME "
            + EVENT_PREFIX_LOCATION + "LOCATION "
            + EVENT_PREFIX_DATE + "DATE "
            + EVENT_PREFIX_START_TIME + "START TIME "
            + EVENT_PREFIX_END_TIME + "END TIME "
            + EVENT_PREFIX_DESCRIPTION + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + EVENT_PREFIX_NAME + "Food collection "
            + EVENT_PREFIX_LOCATION + "NTUC "
            + EVENT_PREFIX_DATE + "Oct 11 2025 "
            + EVENT_PREFIX_START_TIME + "0000 "
            + EVENT_PREFIX_END_TIME + "2359 "
            + EVENT_PREFIX_DESCRIPTION + "Collecting unsold food from NTUC for distribution ";

    public EventAddCommand(Event event) {
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
}
