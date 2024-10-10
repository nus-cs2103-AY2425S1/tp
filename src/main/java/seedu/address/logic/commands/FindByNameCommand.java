package seedu.address.logic.commands;

import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindByNameCommand extends AbstractFindCommand {
    public FindByNameCommand(NameContainsKeywordsPredicate predicate) {
        super(predicate);
    }
}
