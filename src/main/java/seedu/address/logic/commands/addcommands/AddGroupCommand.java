package seedu.address.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;

import java.util.List;

/**
 * Adds a group to the address book.
 */
public class AddGroupCommand extends Command {

    public static final String COMMAND_WORD = "add_g";
    public static final String COMMAND_WORD_ALIAS = "ag";
    public static final int LIST_GROUP_MARKER = 1;

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Adds a group to the address book. "
        + "Parameters: "
        + PREFIX_GROUP_NAME + "GROUPNAME \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GROUP_NAME + "Group 1 ";

    public static final String MESSAGE_SUCCESS = "New group(s) added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "Duplicate group detected";

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
        if (toAdd.size() > 1) {
            resultMessage += "\n";
        }
        int count = 0;
        for (Group g: toAdd) {
            if (model.hasGroup(g)) {
                throw new CommandException(MESSAGE_DUPLICATE_GROUP);
            }
            count++;
            resultMessage += Messages.format(g);
            if (count < toAdd.size()) {
                resultMessage += "\n";
            }
            model.addGroup(g);
        }
        model.setStateGroups();
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
