package seedu.address.logic.commands;

import seedu.address.model.person.ContactContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose contact contains any of the argument keywords.
 */
public class FindByContactCommand extends AbstractFindCommand {
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
