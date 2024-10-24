package seedu.address.logic.commands.listcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.ui.DisplayType;

/**
 * Lists all projects in the address book to the user.
 */
public class ListProjectsCommand extends ListCommand {

    // Command word using the 'list' prefix + 'project'
    public static final String COMMAND_WORD = COMMAND_PREFIX + "projects";

    public static final String MESSAGE_SUCCESS = "Listed all projects";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(MESSAGE_SUCCESS, DisplayType.PROJECT_LIST);
    }
}
