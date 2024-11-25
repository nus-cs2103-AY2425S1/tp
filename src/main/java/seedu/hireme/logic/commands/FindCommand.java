package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hireme.commons.util.ToStringBuilder;
import seedu.hireme.logic.Messages;
import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.NameContainsKeywordsPredicate;

/**
 * Finds and lists all internship applications in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "/find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all internship applications whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " google meta amazon";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW,
                model.getFilteredList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
