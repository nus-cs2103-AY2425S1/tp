package seedu.address.logic.commands.group;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.exceptions.GroupNotFoundException;

/**
 * Command which displays the members of a group to the GUI.
 */
public class ViewGroupCommand extends Command {
    public static final String COMMAND_WORD = "viewGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Outputs as list, the persons in the group with "
            + "the specified name.\n"
            + "Parameters: "
            + PREFIX_GROUP_NAME + "GROUP_NAME "
            + "Example: " + COMMAND_WORD + " g/blood drive\n";

    public static final String MESSAGE_VIEW_GROUP_SUCCESS = "Displaying group %s\n";

    private final String groupName;

    /**
     * Creates a new {@code ViewGroupCommand} with the specified {@code groupName}.
     */
    public ViewGroupCommand(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            model.filterByGroup(groupName);
        } catch (GroupNotFoundException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_VIEW_GROUP_SUCCESS, groupName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ViewGroupCommand e)) {
            return false;
        }
        return groupName.equals(e.groupName);
    }
}
