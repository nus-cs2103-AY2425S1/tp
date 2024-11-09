package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import seedu.address.model.Model;

/**
 * Lists all groups in the address book to the user.
 */
public class ListGroupsCommand extends Command {

    public static final String COMMAND_WORD = "listGroups";

    public static final String MESSAGE_SUCCESS = "Listed all groups";

    public static final String MESSAGE_NOGROUPS = "no groups found";
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        if (model.groupsString().equals(MESSAGE_NOGROUPS)) {
            return new CommandResult(MESSAGE_NOGROUPS, false, false);
        } else {
            return new CommandResult(MESSAGE_SUCCESS, false, false);
        }
    }
}
