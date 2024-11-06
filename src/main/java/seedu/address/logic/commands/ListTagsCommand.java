package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

import seedu.address.logic.commands.CommandResult.SwitchView;
import seedu.address.model.Model;


/**
 * Lists all tasks in the address book to the user.
 */
public class ListTagsCommand extends Command {

    public static final String COMMAND_WORD = "list-tags";

    public static final String COMMAND_KEYWORD = "ltags";

    public static final String MESSAGE_SUCCESS = "Listed all tags";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        return new CommandResult(MESSAGE_SUCCESS, SwitchView.TAG);
    }
}
