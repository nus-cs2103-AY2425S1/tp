package seedu.address.logic.commands.searchmode;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;

import static java.util.Objects.requireNonNull;

public class ExitSearchModeCommand extends Command {
    public static final String COMMAND_WORD = "exitsearchmode";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exits search mode.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Exited search mode.";

    public ExitSearchModeCommand() {
    }
    @Override
    public CommandResult execute(Model model, EventManager eventManager) {
        requireNonNull(model);
        model.setSearchMode(false);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
