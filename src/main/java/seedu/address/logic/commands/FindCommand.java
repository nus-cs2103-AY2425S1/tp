package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ContainsGeneralKeywordsPredicate;
import seedu.address.model.person.ContainsSpecificKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";
    public static final String HELP_FIND_COMMAND = "Find Command\n"
            + "- Format: find KEYWORDS [MORE_KEYWORDS]\n"
            + "- Example: find John Doe\n"
            + "- Only names can be searched; Only full words will be matched."
            + " The command is case insensitive. When more than one keyword is used,"
            + " persons matching at least one keyword will be returned.";

    private final ContainsSpecificKeywordsPredicate specificPredicate;
    private final ContainsGeneralKeywordsPredicate generalPredicate;

    public FindCommand(ContainsSpecificKeywordsPredicate specificPredicate,
                       ContainsGeneralKeywordsPredicate generalPredicate) {
        this.specificPredicate = specificPredicate;
        this.generalPredicate = generalPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
//      model.updateFilteredPersonList(specificPredicate); //Individuals will only be shown if they match word for word.
        model.updateFilteredPersonList(generalPredicate); //Works like the current find command.
//      model.updateFindCommandFilteredPersonList(specificPredicate, generalPredicate);
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
        return specificPredicate.equals(otherFindCommand.specificPredicate) &&
                generalPredicate.equals(otherFindCommand.generalPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("specific predicate", specificPredicate)
                .add("general predicate", generalPredicate)
                .toString();
    }
}
