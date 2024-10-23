package seedu.address.logic.commands.personcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.Model;
import seedu.address.model.types.common.PersonTagContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book who has a tag with a name containing any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchPersonCommand extends SearchCommand {
    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all people who have tags containing any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " p friends work";

    private final PersonTagContainsKeywordsPredicate predicate;

    public SearchPersonCommand(PersonTagContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof SearchPersonCommand)) {
            return false;
        }

        SearchPersonCommand otherSearchPersonCommand = (SearchPersonCommand) other;
        return predicate.equals(otherSearchPersonCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
