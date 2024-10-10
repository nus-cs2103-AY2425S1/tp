package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.List;

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
//
    private final GroupContainsKeywordsPredicate groupPredicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        this.groupPredicate = null;
    }

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
            List<Group> matchingGroups = model.updateFilteredGroupList(groupPredicate);

            // Now filter the persons based on whether they are in any of the matching groups
            model.updateFilteredPersonList(person -> matchingGroups.stream()
                    .flatMap(group -> group.getMembers().stream())
                    .anyMatch(member -> member.isSamePerson(person)));
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
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

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
