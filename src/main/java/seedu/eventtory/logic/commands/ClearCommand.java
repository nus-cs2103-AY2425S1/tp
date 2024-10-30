package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.Model;

/**
 * Clears the contents of EventTory.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "EventTory has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setEventTory(new EventTory());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
