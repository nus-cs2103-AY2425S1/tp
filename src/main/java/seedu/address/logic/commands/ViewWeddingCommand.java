package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameOrJobContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

/**
 * TO be updated
 */
public class ViewWeddingCommand extends Command {
    public static final String COMMAND_WORD = "view-wedding";
    public static final String COMMAND_FUNCTION = COMMAND_WORD
            + ": Filters all persons whose names or jobs contain any of "
            + "the specified keywords (case-insensitive)\n";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "Parameters: n/KEYWORD or j/KEYWORD\n"
            + "Example: " + COMMAND_WORD + " n/John OR " + COMMAND_WORD + " j/Photographer";

    private final NameOrJobContainsKeywordsPredicate predicate;

    public ViewWeddingCommand(NameOrJobContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.logic.commands.ViewWeddingCommand otherViewWeddingCommand)) {
            return false;
        }

        return predicate.equals(otherViewWeddingCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
