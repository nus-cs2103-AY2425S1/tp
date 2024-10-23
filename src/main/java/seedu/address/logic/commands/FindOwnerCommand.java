package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.OwnerNameContainsKeywordsPredicate;

/**
 * Finds and lists all owners in PawPatrol whose name contains any of the argument keywords.
 */
public class FindOwnerCommand extends FindCommand<Owner> {

    public static final String COMMAND_WORD = "find owner";

    /**
     * Constructs a {@code FindOwnerCommand} with the specified {@code OwnerNameContainsKeywordsPredicate}.
     */
    public FindOwnerCommand(OwnerNameContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    /**
     * Executes the find owner command.
     * @param model {@code Model} which the command should operate on.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredOwnerList(super.predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_OWNERS_LISTED_OVERVIEW, model.getFilteredOwnerList().size()));
    }
}
