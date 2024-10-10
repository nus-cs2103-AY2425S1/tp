package seedu.address.logic.commands;

import seedu.address.model.person.EmailContainsKeywordsPredicate;

public class FindEmailCommand extends AbstractFindCommand {

    public static final String COMMAND_WORD = "find /e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose emails "
            + "contain any of the specified keywords (case-insensitive) and displays them as a list with indices.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    public FindEmailCommand(EmailContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindEmailCommand otherFindCommand)) {
            return false;
        }
        return super.predicate.equals(otherFindCommand.predicate);
    }
}
