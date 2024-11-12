package seedu.address.logic.commands.sortcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_GROUP_MARKER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;

/**
 * Sorts all the groups in the address book.
 */
public class SortGroupCommand extends Command {

    public static final String COMMAND_WORD = "sort_g";
    public static final String COMMAND_WORD_ALIAS = "sg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Sorts all groups by group name in alphabetical order.\n"
        + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all groups by group name in alphabetical order";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        model.sortGroupList(new Comparator<Group>() {
            @Override
            public int compare(Group g1, Group g2) {
                return g1.getGroupName().getGroupName().compareTo(g2.getGroupName().getGroupName());
            }
        });
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        model.setStateGroups();
        return new CommandResult(MESSAGE_SUCCESS, LIST_GROUP_MARKER);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }
}
