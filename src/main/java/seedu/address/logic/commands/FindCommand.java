package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ContainsGeneralKeywordsPredicate;
import seedu.address.model.person.ContainsSpecificKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String SPECIFIC_FIND_PREFIX = "s/";

    /*
     * The first character of a specific find must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "For more specific searches, use the \"s/\" prefix. When doing so, keywords need to be an exact match.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie\n"
            + "Example: " + COMMAND_WORD + " " + SPECIFIC_FIND_PREFIX + "John Doe "
            + SPECIFIC_FIND_PREFIX + "23 Abrams Street";
    public static final String HELP_FIND_COMMAND = "Find Command\n"
            + "- Format: find KEYWORDS [MORE_KEYWORDS]\n"
            + "- Example: find John Doe\n"
            + "- Example: find s/John Doe s/23 Abrams Street\n"
            + "- Only names, phone numbers, emails, addresses and tags can be searched;"
            + " The command is case insensitive."
            + " Only full words will be matched."
            + " By default, when more than one keyword is used,"
            + " persons matching at least one keyword will be returned."
            + " If a more specific search is required to find individuals exactly matching the specified keyword,"
            + " utilise the \"s/\" prefix.";

    private final ContainsSpecificKeywordsPredicate specificPredicate;
    private final ContainsGeneralKeywordsPredicate generalPredicate;

    /**
     * Overloaded constructor for FindCommand.
     * @param specificPredicate If a specific find command is called,
     *                         a predicate which returns true only when all the keywords match, is loaded.
     */
    public FindCommand(ContainsSpecificKeywordsPredicate specificPredicate) {
        requireNonNull(specificPredicate);
        this.specificPredicate = specificPredicate;
        this.generalPredicate = null;
    }

    /**
     * Overloaded constructor for FindCommand.
     * @param generalPredicate If a general find command is called,
     *                         a predicate which returns true when at least one of the keywords match, is loaded.
     */
    public FindCommand(ContainsGeneralKeywordsPredicate generalPredicate) {
        requireNonNull(generalPredicate);
        this.generalPredicate = generalPredicate;
        this.specificPredicate = null;
    }

    /**
     * Method to verify if all keywords used in a specific find are valid.
     * @param specificKeywords List of keywords.
     * @return True if all keywords are valid, false otherwise.
     */
    public static boolean isValidSpecificFind(List<String> specificKeywords) {
        boolean result = true;
        for (int i = 0; i < specificKeywords.size(); i++) {
            if (specificKeywords.get(i).matches(VALIDATION_REGEX)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (this.generalPredicate != null) {
            model.updateFilteredPersonList(generalPredicate);
        } else {
            model.updateFilteredPersonList(specificPredicate);
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

        if (specificPredicate != null) {
            return specificPredicate.equals(otherFindCommand.specificPredicate);
        } else {
            return generalPredicate.equals(otherFindCommand.generalPredicate);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("specific predicate", specificPredicate)
                .add("general predicate", generalPredicate)
                .toString();
    }
}
