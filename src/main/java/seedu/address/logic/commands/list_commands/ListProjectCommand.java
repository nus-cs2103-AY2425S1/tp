package seedu.address.logic.commands.list_commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

import seedu.address.model.Model;
import seedu.address.logic.commands.CommandResult;

/**
 * Lists all projects in the address book to the user.
 */
public class ListProjectCommand extends ListCommand {

    // Command word using the 'list' prefix + 'project'
    public static final String COMMAND_WORD = COMMAND_PREFIX + "project";

    public static final String MESSAGE_SUCCESS = "Listed all projects";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
