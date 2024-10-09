package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TelContainsNumberPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS] [num/ PHONE] \n"
            + "Example: " + COMMAND_WORD + " alice bob /num 12345678";

    private final NameContainsKeywordsPredicate namePredicate;
    private final TelContainsNumberPredicate numberPredicate;

    public FindCommand(NameContainsKeywordsPredicate namePredicate, TelContainsNumberPredicate numberPredicate) {
        this.namePredicate = namePredicate;
        this.numberPredicate = numberPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // if name predicate is not null
        if (namePredicate != null) {
            model.updateFilteredPersonList(namePredicate);
            // if number was entered search for it too
            if (numberPredicate != null) {
                model.updateFilteredPersonList(numberPredicate);
            }
        } else {
            // name predicate null -> number predicate must exist
            model.updateFilteredPersonList(numberPredicate);
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
        return namePredicate.equals(otherFindCommand.namePredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", namePredicate)
                .toString();
    }
}
