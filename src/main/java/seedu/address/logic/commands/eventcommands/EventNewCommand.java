package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.AddressBookParser.EVENT_COMMAND_INDICATOR;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_START_TIME;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds an event to the calendar.
 */
public class EventNewCommand extends Command {
    public static final String COMMAND_WORD = "new";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the calander. "
            + "Parameters: "
            + EVENT_PREFIX_NAME + "NAME "
            + EVENT_PREFIX_LOCATION + "LOCATION "
            + EVENT_PREFIX_DATE + "DATE "
            + EVENT_PREFIX_START_TIME + "START TIME "
            + EVENT_PREFIX_END_TIME + "END TIME "
            + EVENT_PREFIX_DESCRIPTION + "DESCRIPTION "
            + "Example: " + EVENT_COMMAND_INDICATOR + COMMAND_WORD + " "
            + EVENT_PREFIX_NAME + "Food collection "
            + EVENT_PREFIX_LOCATION + "NTUC "
            + EVENT_PREFIX_DATE + "2024-11-29 "
            + EVENT_PREFIX_START_TIME + "00:00 "
            + EVENT_PREFIX_END_TIME + "23:59 "
            + EVENT_PREFIX_DESCRIPTION + "Collecting unsold food from NTUC for distribution ";
    private static final String MESSAGE_DUPLICATE_EVENT = "This event already exists!";
    private static final String MESSAGE_SUCCESS = "Event added successfully!";
    private final Event toAdd;

    /**
     * Creates an EventAddCommand to add the specified {@code Event}.
     *
     * @param event The event to be added.
     */
    public EventNewCommand(Event event) {
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

        model.getFilteredEventList().stream().forEach(event -> System.out.println(event));

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }
}
