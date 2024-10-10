package seedu.address.logic.commands;

import seedu.address.model.student.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindNameCommand extends FindCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/KEYWORD [n/MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice n/bob n/charlie";

    public FindNameCommand(NameContainsKeywordsPredicate predicate) {
        super(predicate);
    }
}
