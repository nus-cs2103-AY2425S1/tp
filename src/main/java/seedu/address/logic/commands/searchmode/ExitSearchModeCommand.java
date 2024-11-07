package seedu.address.logic.commands.searchmode;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;


/**
 * Exits search mode.
 */
public class ExitSearchModeCommand extends Command {
    public static final String COMMAND_WORD = "exit-search";
    public static final String COMMAND_WORD_SHORT_FORM = "es";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD_SHORT_FORM + ": Exits "
            + "search mode.\nExample: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Exited search mode.";

    public ExitSearchModeCommand() {
    }
    @Override
    public CommandResult execute(Model model, EventManager eventManager) {
        requireNonNull(model);
        model.setSearchMode(false);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.clearExcludedPersons();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof ExitSearchModeCommand; // instanceof handles nulls
    }

}
