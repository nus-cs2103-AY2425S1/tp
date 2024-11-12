package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.TagMatchesKeywordsPredicate;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

/**
 * Filters the persons by the specified tag and displays it.
 * Keyword matching is case-insensitive.
 */
public class FilterPersonCommand extends FilterCommand {
    public static final String COMMAND_FIELD = "person";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the persons by "
            + "the specified tag (case-insensitive) and displays it.\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_FIELD + " Chauffeur";

    private final TagMatchesKeywordsPredicate predicate;

    public FilterPersonCommand(TagMatchesKeywordsPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                false, false, CommandTabChange.PERSON, CommandDetailChange.SIMPLIFIED);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterPersonCommand)) {
            return false;
        }

        FilterPersonCommand otherFilterPersonCommand = (FilterPersonCommand) other;
        return predicate.equals(otherFilterPersonCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
