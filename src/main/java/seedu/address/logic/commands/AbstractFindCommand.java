package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.ContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public abstract class AbstractFindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names, contacts or emails "
            + "contain any of the specified keywords (case-insensitive) and displays them as a list with indices.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    protected final ContainsKeywordsPredicate predicate;

    public AbstractFindCommand(ContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(this.predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AbstractFindCommand)) {
            return false;
        }

        AbstractFindCommand otherFindCommand = (AbstractFindCommand) other;
        return this.predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", this.predicate)
                .toString();
    }
}
