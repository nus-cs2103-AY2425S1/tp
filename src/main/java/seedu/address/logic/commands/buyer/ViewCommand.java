package seedu.address.logic.commands.buyer;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all buyers in the buyer list.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Viewing all buyers";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredBuyerList(Model.PREDICATE_SHOW_ALL_BUYERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
