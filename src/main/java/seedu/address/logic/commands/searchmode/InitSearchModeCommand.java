package seedu.address.logic.commands.searchmode;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;

import static java.util.Objects.requireNonNull;

public class InitSearchModeCommand extends Command {
    public static final String COMMAND_WORD = "searchmode";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters search mode.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Entered search mode.";

    @Override
    public CommandResult execute(Model model, EventManager eventManager) {
        requireNonNull(model);
        model.setSearchMode(true);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
