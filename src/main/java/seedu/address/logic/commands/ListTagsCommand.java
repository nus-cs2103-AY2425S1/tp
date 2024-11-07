package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult.SwitchView;
import seedu.address.model.Model;


/**
 * Lists all tags in the address book to the user.
 */
public class ListTagsCommand extends Command {

    public static final String COMMAND_WORD = "list-tags";

    public static final String COMMAND_KEYWORD = "ltags";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        return new CommandResult(Messages.MESSAGE_LIST_TAG_SUCCESS, SwitchView.TAG);
    }
}
