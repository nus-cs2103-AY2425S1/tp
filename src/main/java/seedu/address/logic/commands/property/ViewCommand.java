package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROPERTIES;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all meet-ups in the meet-up list.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Viewing all properties";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, true);
    }
}
