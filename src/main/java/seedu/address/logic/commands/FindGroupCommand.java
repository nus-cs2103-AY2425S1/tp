package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.group.GroupContainsKeywordsPredicate;


/**
 * Finds and lists all groups in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindGroupCommand extends Command {

    public static final String COMMAND_WORD = "findGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all groups whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Class A \n";


    private final GroupContainsKeywordsPredicate groupPredicate;


    /**
     * Constructs a {@code FindCommand} for searching persons by group.
     * The command will filter the address book to find all persons who belong to groups
     * whose names contain any of the keywords specified in the given {@code GroupContainsKeywordsPredicate}.
     *
     * @param groupPredicate The {@code GroupContainsKeywordsPredicate}
     *      that defines the search criteria based on group membership.
     */

    public FindGroupCommand(GroupContainsKeywordsPredicate groupPredicate) {
        this.groupPredicate = groupPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (groupPredicate != null) {
            model.updateFilteredGroupList(groupPredicate);
        }
        return new CommandResult(
            String.format(Messages.MESSAGE_GROUPS_LISTED_OVERVIEW, model.getFilteredGroupList().size()),
                false,
                true,
                false);
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

        FindGroupCommand otherCommand = (FindGroupCommand) other;

        if (this.groupPredicate != null) {
            return this.groupPredicate.equals(otherCommand.groupPredicate);
        }

        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("groupPredicate", groupPredicate)
                .toString();
    }
}
