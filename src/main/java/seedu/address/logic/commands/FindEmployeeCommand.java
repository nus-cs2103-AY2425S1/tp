package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EMPLOYEES;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all employees in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindEmployeeCommand extends FindCommand {
    public static final String ARGUMENT_WORD = "/e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all employees whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: /e KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + ARGUMENT_WORD + " alice bob charlie";

    public FindEmployeeCommand(NameContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_EMPLOYEES.and(super.getPredicate()));
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
