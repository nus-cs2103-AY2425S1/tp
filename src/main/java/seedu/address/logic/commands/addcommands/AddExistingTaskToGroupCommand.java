package seedu.address.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_TASK_MARKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.task.Task;

/**
 * Adds an existing task to a set of groups.
 */
public class AddExistingTaskToGroupCommand extends Command {

    public static final String COMMAND_WORD = "add_et_g";
    public static final String COMMAND_WORD_ALIAS = "aetg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Adds an existing task to the group(s).\n"
        + "Parameters: "
        + PREFIX_INDEX + "INDEX (must be a positive integer) "
        + PREFIX_GROUP_NAME + "GROUP_NAME "
        + "[" + PREFIX_GROUP_NAME + "GROUP_NAME]...\n"
        + "Example: " + COMMAND_WORD
        + PREFIX_INDEX + "1 "
        + PREFIX_GROUP_NAME + "CS2103T-F12-2"
        + PREFIX_GROUP_NAME + "CS2103-F13-3";
    public static final String MESSAGE_SUCCESS = "Added task (%1$s) to the following group(s):\n";
    public static final String MESSAGE_FAILURE = "Could not complete the command, see below:\n%1$s";
    public static final String GROUP_HAS_TASK = "The following groups(s) already have the task:";
    public static final String GROUP_NOT_FOUND = "The following group(s) do not exist:";
    public static final String GROUP_DUPLICATE
        = "The following group(s) are duplicated, task will be added to only one:";

    private final Index targetIndex;
    private final List<Group> groups;

    /**
     * Creates an AddExistingTaskToGroupCommand to add the specified {@code Task} at the given {@code Index}
     * to the specified set of {@code Group}s.
     */
    public AddExistingTaskToGroupCommand(Index targetIndex, List<Group> groups) {
        requireNonNull(targetIndex);
        requireNonNull(groups);
        this.targetIndex = targetIndex;
        this.groups = groups;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Group> groupList = groups.stream()
            .filter(model::hasGroup)
            .map(g -> model.getGroupByName(g.getGroupName()))
            .toList();

        List<Task> lastShownTaskList = model.getFilteredTaskList();
        if (targetIndex.getZeroBased() >= lastShownTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }
        Task taskToAdd = lastShownTaskList.get(targetIndex.getZeroBased());

        StringBuilder errorMessage = new StringBuilder();

        // get list of valid groups -- use this for adding
        List<Group> validGroups = groupList.stream()
            .filter(model::hasGroup)
            .filter(g -> !model.hasTaskInGroup(taskToAdd, g)) // relies on having actual group
            .distinct()
            .toList();

        // get list of groups that do exist
        List<Group> groupExistList = groups.stream()
            .filter(model::hasGroup)
            .toList();

        // check that provided groups exist
        List<Group> groupsNotExist = groups.stream()
            .filter(g -> !model.hasGroup(g))
            .distinct()
            .toList();

        // append those that don't to errorMessage
        if (!groupsNotExist.isEmpty()) {
            errorMessage.append(appendResultMessage(groupsNotExist, GROUP_NOT_FOUND));
        }

        // get list of duplicate groups that exist
        List<Group> duplicateGroups = groupExistList.stream()
            .filter(g -> Collections.frequency(groupExistList, g) > 1)
            .distinct()
            .toList();

        // append duplicates to errorMessage
        if (!duplicateGroups.isEmpty()) {
            errorMessage.append(appendResultMessage(duplicateGroups, GROUP_DUPLICATE));
        }

        // check that groups that exist and have task already
        List<Group> groupAlreadyHasTask = groupList.stream() // assumes that above already check existence
            .filter(g -> model.hasTaskInGroup(taskToAdd, g))
            .distinct()
            .toList();

        if (!groupAlreadyHasTask.isEmpty()) {
            errorMessage.append(appendResultMessage(groupAlreadyHasTask, GROUP_HAS_TASK));
        }

        if (validGroups.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_FAILURE, errorMessage));
        }

        StringBuilder successMessage = new StringBuilder(MESSAGE_SUCCESS);

        for (Group group : validGroups) {
            assert model.hasGroup(group) : "Group should exist";
            assert !model.hasTaskInGroup(taskToAdd, group) : "Group should not have task.";

            model.addTaskToGroup(taskToAdd, group);
            model.increaseGroupWithTask(taskToAdd);
            successMessage.append(group.getGroupName());
            successMessage.append("\n");
        }

        model.updateFilteredTaskList(task -> task.equals(taskToAdd));
        return new CommandResult(String.format(successMessage.toString(), taskToAdd.getTaskName())
            + errorMessage, LIST_TASK_MARKER);
    }

    private static String appendResultMessage(List<Group> groups, String message) {
        String txt = groups.stream()
            .map(g -> g.getGroupName().getGroupName())
            .reduce(message, (x, y) -> x + "\n" + y);
        txt += "\n";
        return txt;
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddExistingTaskToGroupCommand)) {
            return false;
        }

        AddExistingTaskToGroupCommand otherAddExistingTaskToGroupCommand = (AddExistingTaskToGroupCommand) other;
        return targetIndex.equals(otherAddExistingTaskToGroupCommand.targetIndex)
            && groups.equals(otherAddExistingTaskToGroupCommand.groups);
    }
}
