package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in PawPatrol whose name contains any of the argument keywords.
 */
public class FindPersonCommand extends FindCommand<Person> {
    /**
     * Constructs a {@code FindPersonCommand} with the specified {@code NameContainsKeywordsPredicate}.
     */
    public FindPersonCommand(NameContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    /**
     * Executes the find person command.
     * @param model {@code Model} which the command should operate on.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(super.predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
