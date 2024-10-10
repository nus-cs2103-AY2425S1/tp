package seedu.address.logic.commands;

import seedu.address.model.person.ContactContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose contact contains any of the argument keywords.
 */
public class FindByContactCommand extends AbstractFindCommand {
    public static final String COMMAND_WORD = "find /c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose contact numbers "
            + "contain any of the specified keywords (case-insensitive) and displays them as a list with indices.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 91112345 999 995";
    public FindByContactCommand(ContactContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindByContactCommand)) {
            return false;
        }

        FindByContactCommand otherFindCommand = (FindByContactCommand) other;
        return this.getPredicate().equals(otherFindCommand.getPredicate());
    }
}
