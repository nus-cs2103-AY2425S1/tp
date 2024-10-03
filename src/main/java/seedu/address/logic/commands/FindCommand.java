package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ClassIdContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

import java.util.function.Predicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    private final ClassIdContainsKeywordsPredicate predicateClassId;



    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        this.predicateClassId = null;
    }

    public FindCommand(ClassIdContainsKeywordsPredicate predicate) {
        this.predicateClassId = predicate;
        this.predicate = null;
    }



    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (predicate != null) {
            model.updateFilteredPersonList(predicate);
        } else if (predicateClassId != null) {
            model.updateFilteredPersonList(predicateClassId);
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
        return (predicate != null ? predicate.equals(otherFindCommand.predicate)
                : predicateClassId.equals(otherFindCommand.predicateClassId));
    }

    @Override
    public String toString() {
        if (predicate != null) {
            return new ToStringBuilder(this)
                    .add("predicate", predicate)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("predicate", predicateClassId)
                    .toString();
        }

    }
}
