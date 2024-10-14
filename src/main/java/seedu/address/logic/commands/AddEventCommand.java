package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;

import seedu.address.model.Model;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand {

    public static final String COMMAND_WORD = "add_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "EVENT_NAME "
            + PREFIX_EVENT_DESCRIPTION + "EVENT_DESCRIPTION"
            + PREFIX_EVENT_START_DATE + "EVENT_START_DATE"
            + PREFIX_EVENT_END_DATE + "EVENT_END_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "Meeting "
            + PREFIX_EVENT_DESCRIPTION + "Club Meeting for Orientation"
            + PREFIX_EVENT_START_DATE + "2024-10-01"
            + PREFIX_EVENT_END_DATE + "2024-10-10\n"
            + "Dates must be in YYYY-MM-DD format!";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book";

    private final Event toAdd;
}
