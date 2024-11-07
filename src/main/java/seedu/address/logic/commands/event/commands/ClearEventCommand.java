package seedu.address.logic.commands.event.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;


/**
 * Clears the Events from the model.
 */
public class ClearEventCommand extends Command {
    public static final String COMMAND_WORD = "clear-event";
    public static final String MESSAGE_SUCCESS = "Events has been cleared!";

    @Override
    public CommandResult execute(Model model, EventManager eventManager) {
        requireNonNull(model);
        model.setEventManager(new EventManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
