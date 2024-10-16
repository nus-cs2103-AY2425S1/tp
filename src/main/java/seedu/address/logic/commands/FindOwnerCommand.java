package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.OwnerNameContainsKeywordsPredicate;

public class FindOwnerCommand extends FindCommand<Owner> {
    public FindOwnerCommand(OwnerNameContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredOwnerList(super.predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_OWNERS_LISTED_OVERVIEW, model.getFilteredOwnerList().size()));
    }
}
