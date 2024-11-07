package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.logic.commands.util.IndexResolverUtil;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.event.Event;

/**
 * Display details of an event in EventTory to the user.
 */
public class ViewEventCommand extends ViewCommand {

    public static final String MESSAGE_SUCCESS = "Viewing %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the vendor identified by the index number used in the displayed event list.\n" + "Parameters: "
            + PREFIX_EVENT + "INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT
            + "1";

    public ViewEventCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Event eventToView = IndexResolverUtil.resolveEvent(model, targetIndex);

        model.viewEvent(eventToView);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(eventToView)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewEventCommand)) {
            return false;
        }

        ViewEventCommand otherViewEventCommand = (ViewEventCommand) other;
        return targetIndex.equals(otherViewEventCommand.targetIndex);
    }
}
