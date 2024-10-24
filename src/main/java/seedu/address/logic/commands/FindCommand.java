package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;


/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie \n"
            + "OR\n"
            + "Example: " + COMMAND_WORD + "/group gooners";


    private final NameContainsKeywordsPredicate predicate;

    private final GroupContainsKeywordsPredicate groupPredicate;


    /**
     * Constructs a {@code FindCommand} for searching persons by name.
     * The command will filter the address book to find all persons whose names contain any of the
     * keywords specified in the given {@code NameContainsKeywordsPredicate}.
     *
     * @param predicate The {@code NameContainsKeywordsPredicate} that defines the search criteria based on name.
     */
    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        this.groupPredicate = null;
    }

    /**
     * Constructs a {@code FindCommand} for searching persons by group.
     * The command will filter the address book to find all persons who belong to groups
     * whose names contain any of the keywords specified in the given {@code GroupContainsKeywordsPredicate}.
     *
     * @param groupPredicate The {@code GroupContainsKeywordsPredicate}
     *      that defines the search criteria based on group membership.
     */

    public FindCommand(GroupContainsKeywordsPredicate groupPredicate) {
        this.predicate = null;
        this.groupPredicate = groupPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (predicate != null) {
            model.updateFilteredPersonList(predicate);
        } else if (groupPredicate != null) {

            // Case for group-based search
            // Use updateFilteredGroupList to get the list of matching groups
            model.updateFilteredGroupList(groupPredicate);
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
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
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherCommand = (FindCommand) other;
        // Check for both predicate and groupPredicate
        if (this.predicate != null) {
            return this.predicate.equals(otherCommand.predicate);
        }

        if (this.groupPredicate != null) {
            return this.groupPredicate.equals(otherCommand.groupPredicate);
        }

        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
