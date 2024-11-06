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
    public static final String MESSAGE_GROUP_EXISTS_IN_MODEL = "Group(s) already exists in model";

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
        Stream<Group> checkForDuplicates = toAdd.stream().filter(x -> Collections.frequency(toAdd, x) > 1).distinct();
        long numDuplicates = toAdd.stream().filter(x -> Collections.frequency(toAdd, x) > 1).distinct().count();
        String duplicateMessage = checkForDuplicates.map(a -> a.getGroupName().getGroupName()).reduce(
                MESSAGE_DUPLICATE_GROUP, (x, y) -> x + "\n" + y);
        List<Group> noDuplicateGroupList = toAdd.stream().distinct().toList();
        if (noDuplicateGroupList.size() > 1) {
            resultMessage += "\n";
        }
        for (Group g: noDuplicateGroupList) {
            if (model.hasGroup(g)) {
                throw new CommandException(MESSAGE_GROUP_EXISTS_IN_MODEL);
            }
            count++;
            resultMessage += Messages.format(g);
            if (count < toAdd.size()) {
                resultMessage += "\n";
            }
            model.addGroup(g);
        }
        model.updateFilteredGroupList(x ->
                toAdd.stream().anyMatch(y -> y.getGroupName().equals(x.getGroupName())));
        model.setStateGroups();
        if (numDuplicates > 0) {
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
