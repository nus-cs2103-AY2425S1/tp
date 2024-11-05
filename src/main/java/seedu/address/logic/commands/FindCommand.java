package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in ClientHub whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds clients whose name, phone number, "
            + "address or client type contain any of "
            + "all specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: n/NAME or p/PHONE_NUMBER, or a/ADDRESS or c/CLIENT_TYPE\n"
            + "Examples:\n"
            + "- " + COMMAND_WORD + " alice wong\n"
            + "- " + COMMAND_WORD + " p/91234567\n"
            + "- " + COMMAND_WORD + " a/123, Jurong West Ave 6\n"
            + "- " + COMMAND_WORD + " c/Investment Plan 1\n"
            + "Additional Info:\n"
            + "- The command can only take in one prefix at any point of time. (find n/NAME a/Address is invalid)\n";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                Messages.getMessagePersonsListedOverview(model.getDisplayPersons().size()));
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
