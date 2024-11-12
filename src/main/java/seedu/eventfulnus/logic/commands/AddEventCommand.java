package seedu.eventfulnus.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_SPORT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.eventfulnus.logic.Messages;
import seedu.eventfulnus.logic.commands.exceptions.CommandException;
import seedu.eventfulnus.model.Model;
import seedu.eventfulnus.model.event.Event;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = "addevent";
    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";
    public static final Object MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_SPORT + "SPORT "
            + PREFIX_TEAM + "TEAM1 "
            + PREFIX_TEAM + "TEAM2 "
            + PREFIX_VENUE + "VENUE "
            + "[" + PREFIX_PARTICIPANT + "PARTICIPANT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SPORT + "Chess "
            + PREFIX_TEAM + "COM "
            + PREFIX_TEAM + "FASS "
            + PREFIX_VENUE + "Stadium "
            + PREFIX_PARTICIPANT + "Alice ";

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(Event event) {
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatEvent(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}
