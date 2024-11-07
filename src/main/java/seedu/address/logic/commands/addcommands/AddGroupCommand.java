package seedu.address.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_GROUP_MARKER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;

/**
 * Adds a group to the address book.
 */
public class AddGroupCommand extends Command {

    public static final String COMMAND_WORD = "add_g";
    public static final String COMMAND_WORD_ALIAS = "ag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Adds a group to T_Assistant.\n"
        + "Parameters: "
        + PREFIX_GROUP_NAME + "GROUP_NAME \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GROUP_NAME + "CS2103T-T14-1";

    public static final String MESSAGE_SUCCESS = "New group(s) added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "Duplicate group(s) entered, only 1 will be added:";
    public static final String MESSAGE_GROUPS_EXIST_IN_MODEL = "All group(s) entered exist in the model";
    public static final String MESSAGE_GROUP_IN_MODEL = "The following group(s) already exist and will not be added:";

    private final List<Group> toAdd;

    /**
     * Creates an AddGroupCommand to add the specified {@code Group}.
     */
    public AddGroupCommand(List<Group> groups) {
        requireNonNull(groups);
        toAdd = groups;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String resultMessage = "";
        int count = 0;

        // create a stream consisting of all groups which are entered more than once by the user
        Stream<Group> checkForDuplicates = toAdd.stream().filter(x -> Collections.frequency(toAdd, x) > 1)
                .filter(x -> !model.hasGroup(x))
                .distinct();

        // count the number of duplicated groups entered
        long numDuplicates = toAdd.stream().filter(x -> Collections.frequency(toAdd, x) > 1)
                .filter(x -> !model.hasGroup(x))
                .distinct()
                .count();

        // create duplicate message for duplicate groups, if none will just return an empty string
        String duplicateMessage = checkForDuplicates.map(a -> a.getGroupName().getGroupName()).reduce(
                MESSAGE_DUPLICATE_GROUP, (x, y) -> x + "\n" + y);
        if (numDuplicates == 0) {
            duplicateMessage = "";
        }

        //  create a list without duplicate groups
        List<Group> noDuplicateGroupList = toAdd.stream().distinct().toList();
        if (noDuplicateGroupList.size() > 1) {
            resultMessage += "\n";
        }

        int countGroupsInModel = 0;
        String messageGroupInModel = MESSAGE_GROUP_IN_MODEL;

        // iterate through the list of groups, incrementing count if group does not exist in the model and deleting it
        // from the original list if it does
        for (Group g: noDuplicateGroupList) {
            if (model.hasGroup(g)) {
                countGroupsInModel++;
                messageGroupInModel += "\n" + g.getGroupName().getGroupName();
                toAdd.removeAll(List.of(g));
            } else {
                count++;
                resultMessage += Messages.format(g);
                resultMessage = count < noDuplicateGroupList.size() ? resultMessage + "\n" : resultMessage;
                model.addGroup(g);
            }
        }

        // throws an exception if all groups entered already exist
        if (countGroupsInModel == noDuplicateGroupList.size()) {
            throw new CommandException(MESSAGE_GROUPS_EXIST_IN_MODEL);
        }

        // displays warning message if only some groups entered exist in the model
        if (0 < countGroupsInModel && countGroupsInModel < noDuplicateGroupList.size()) {
            duplicateMessage = messageGroupInModel + "\n" + duplicateMessage;
        }

        // update group list to only display the added groups
        model.updateFilteredGroupList(x ->
                toAdd.stream().anyMatch(y -> y.getGroupName().equals(x.getGroupName())));
        model.setStateGroups();

        // displays warning message if duplicates are found or groups exist in model
        if (numDuplicates > 0 || countGroupsInModel > 0) {
            return new CommandResult(duplicateMessage
                    + "\n" + String.format(MESSAGE_SUCCESS, resultMessage), LIST_GROUP_MARKER);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, resultMessage), LIST_GROUP_MARKER);
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
        if (!(other instanceof AddGroupCommand)) {
            return false;
        }

        AddGroupCommand otherAddGroupCommand = (AddGroupCommand) other;
        return toAdd.equals(otherAddGroupCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("toAdd", toAdd)
            .toString();
    }
}
