package seedu.address.logic.commands;

import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindByNameCommand extends AbstractFindCommand {
    public FindByNameCommand(NameContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindByNameCommand)) {
            return false;
        }

        FindByNameCommand otherFindCommand = (FindByNameCommand) other;
        return this.getPredicate().equals(otherFindCommand.getPredicate());
    }
}
