package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.vendor.NameContainsKeywordsPredicate;

/**
 * Parent abstract class for find commands.
 * Contains the predicate to be used to filter.
 * Keyword matching is case insensitive.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all vendors or events whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_VENDOR + "KEYWORD [MORE_KEYWORDS]... or " + PREFIX_EVENT + "+ KEYWORD [MORE_KEYWORDS]...\n"
            + "Example to find a vendor: " + COMMAND_WORD + " " + PREFIX_VENDOR + " alice bob charlie\n"
            + "Example to find an event: " + COMMAND_WORD + " " + PREFIX_EVENT + " party birthday banquet\n";

    protected final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
