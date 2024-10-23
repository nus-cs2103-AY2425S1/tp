package seedu.address.logic.commands.findcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUERY;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.GroupNameContainsKeywordsPredicate;

/**
 * Finds and lists all groups in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindGroupCommand extends Command {
    public static final String COMMAND_WORD = "find_g";
    public static final String COMMAND_WORD_ALIAS = "fg";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Finds all groups whose names contain any of the specified keywords (case-insensitive) "
        + "and displays them as list with index numbers.\n"
        + "Parameters: " + PREFIX_QUERY + "KEYWORD [" + PREFIX_QUERY + "MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + "/" + COMMAND_WORD_ALIAS + " " + PREFIX_QUERY + " group 1";
    public static final int LIST_GROUP_MARKER = 1;

    private static final Logger logger = LogsCenter.getLogger(FindGroupCommand.class);

    private final GroupNameContainsKeywordsPredicate predicate;

    public FindGroupCommand(GroupNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredGroupList(predicate);
        model.setStateGroups();
        logger.info(String.format("Found %1s groups with predicate - %2s", model.getFilteredGroupList().size(),
            predicate));
        return new CommandResult(
            String.format(Messages.MESSAGE_GROUPS_LISTED_OVERVIEW,
                model.getFilteredGroupList().size()), LIST_GROUP_MARKER
        );
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        return versionHistory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindGroupCommand)) {
            return false;
        }

        FindGroupCommand otherFindGroupCommand = (FindGroupCommand) other;
        return predicate.equals(otherFindGroupCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("predicate", predicate)
            .toString();
    }
}
