package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.TagsContainsKeywordsPredicate;


/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters list according to tags "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "< If the tag 'Vegan' is used > \n"
            + "Example: " + COMMAND_WORD
            + " Vegan \n returns all contacts with the tag: Vegan\n"
            + "< If the shortcut: 'v' is set to 'Vegan' > \n"
            + "Example: " + COMMAND_WORD
            + " v \n returns all contacts with tag: Vegan";

    private final TagsContainsKeywordsPredicate predicate;

    public FilterCommand(TagsContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFindCommand = (FilterCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
