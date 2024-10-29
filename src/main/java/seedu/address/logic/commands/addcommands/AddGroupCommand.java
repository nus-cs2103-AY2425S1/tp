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

    public static final String MESSAGE_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book";

    private final Group toAdd;

    /**
     * Creates an AddGroupCommand to add the specified {@code Group}.
     */
    public AddGroupCommand(Group group) {
        requireNonNull(group);
        toAdd = group;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasGroup(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.addGroup(toAdd);
        model.setStateGroups();
        model.updateFilteredGroupList(x -> x.getGroupName().equals(toAdd.getGroupName()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)), LIST_GROUP_MARKER);
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
