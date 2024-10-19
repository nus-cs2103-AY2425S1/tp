package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;

/**
 * Adds a group to the address book.
 */
public class ListGroupCommand extends Command {

    public static final String COMMAND_WORD = "list_g";
    public static final String COMMAND_WORD_ALIAS = "lg";
    public static final int LIST_GROUP_MARKER = 1;

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Lists all groups.\n"
        + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all groups";

    /**
     * Creates an ListGroupCommand to add the specified {@code Group}
     */
    public ListGroupCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        model.setStateGroups();
        return new CommandResult(MESSAGE_SUCCESS, LIST_GROUP_MARKER);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) {
        return versionHistory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }
}
