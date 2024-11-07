package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Filters and lists all persons in the address book whose tags contain any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterTagCommand extends Command {

    public static final String COMMAND_WORD = "filtertag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all contacts whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friend colleague";

    public static final String MESSAGE_SUCCESS = "%d contact(s) with tag(s): %s listed.";

    private final TagContainsKeywordsPredicate predicate;

    public FilterTagCommand(TagContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        int listSize = model.getFilteredPersonList().size();
        String keywords = String.join(", ", predicate.getKeywords());
        String resultMessage = String.format(MESSAGE_SUCCESS, listSize, keywords);

        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterTagCommand)) {
            return false;
        }

        FilterTagCommand otherCommand = (FilterTagCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
