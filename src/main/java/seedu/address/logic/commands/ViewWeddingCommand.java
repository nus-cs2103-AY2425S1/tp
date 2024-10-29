package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * TO be updated
 */
public class ViewWeddingCommand extends Command {
    public static final String COMMAND_WORD = "view-wed";
    public static final String COMMAND_FUNCTION = COMMAND_WORD
            + ": Shows all persons involved in the weddings of "
            + "the specified keywords (case-insensitive)\n";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "Parameters: KEYWORD"
            + "Example: " + COMMAND_WORD + " Jonus & Izzat";

    private final TagContainsKeywordsPredicate predicate;

    public ViewWeddingCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PARTICIPANTS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewWeddingCommand otherViewWeddingCommand)) {
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
