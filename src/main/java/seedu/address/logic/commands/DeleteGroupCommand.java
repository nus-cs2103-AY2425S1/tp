package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupContainsKeywordsPredicate;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;

/**
 * Deletes a group identified by its name from the address book.
 */
public class DeleteGroupCommand extends Command {

    public static final String COMMAND_WORD = "deleteGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the group identified by its name.\n"
            + "Parameters: GROUP_NAME (must be a valid group name)\n"
            + "Example: " + COMMAND_WORD + " Study Group";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";
    public static final String MESSAGE_GROUP_NOT_FOUND = "Group not found: %1$s";

    private final GroupName groupName;

    public DeleteGroupCommand(GroupName groupName) {
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Group> lastShownList = model.getFilteredGroupList();

        GroupContainsKeywordsPredicate groupPredicate =
                new GroupContainsKeywordsPredicate(List.of(groupName.groupName));
        List<Group> matchingGroups = model.getFilteredGroupList();

        if (matchingGroups.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_GROUP_NOT_FOUND, groupName));
        }

        // Assuming there's only one group with the exact name, delete it
        Group groupToDelete = matchingGroups.get(0);
        model.deleteGroup(groupToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete.getGroupName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteGroupCommand)) {
            return false;
        }

        DeleteGroupCommand otherDeleteGroupCommand = (DeleteGroupCommand) other;
        return groupName.equals(otherDeleteGroupCommand.groupName);
    }

    @Override
    public String toString() {
        return String.format("DeleteGroupCommand[groupName=%s]", groupName);
    }
}
