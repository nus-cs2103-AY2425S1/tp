package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NricContainsKeywordsPredicate;


/**
 * Finds and lists all persons in address book whose {@code Nric} contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindNricCommand extends Command {

    public static final String COMMAND_WORD = "findnric";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds and lists all persons who have matching Nric\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NricContainsKeywordsPredicate predicate;

    public FindNricCommand(NricContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof seedu.address.logic.commands.FindCommand)) {
            return false;
        }

        seedu.address.logic.commands.FindNricCommand otherFindCommand =
                (seedu.address.logic.commands.FindNricCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
