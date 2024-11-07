package seedu.address.logic.commands.searchmode;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;


/**
 * Enters search mode.
 */
public class InitSearchModeCommand extends Command {
    public static final String COMMAND_WORD = "search-mode";
    public static final String COMMAND_WORD_SHORT_FORM = "sm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD_SHORT_FORM + ": Enters "
            + "search mode.\nExample: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Entered search mode.";

    public InitSearchModeCommand() {
    }
    @Override
    public CommandResult execute(Model model, EventManager eventManager) {
        requireNonNull(model);
        model.setSearchMode(true);
        // empties the current displayed list
        model.updateFilteredPersonList(person -> false);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
