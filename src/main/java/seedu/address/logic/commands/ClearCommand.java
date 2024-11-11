package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.ClientHub;
import seedu.address.model.Model;

/**
 * Clears the client hub.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Client Hub has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setClientHub(new ClientHub());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
