package careconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import careconnect.commons.util.ToStringBuilder;
import careconnect.logic.Messages;
import careconnect.model.Model;
import careconnect.model.person.NameAndAddressAndTagsContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/ NAME [MORE_NAMES] a/ ADDRESS [MORE_ADDRESSES] t/ TAG [MORE_TAGS]\n"
            + "Example: " + COMMAND_WORD + " n/ alice bob charlie a/Serangoon t/friend";

    private final NameAndAddressAndTagsContainsKeywordsPredicate predicate;

    public FindCommand(NameAndAddressAndTagsContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
