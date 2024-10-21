package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;

import tutorease.address.commons.util.ToStringBuilder;
import tutorease.address.logic.Messages;
import tutorease.address.model.Model;
import tutorease.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds all persons whose name is contained in the keyword.
 */
public class FindContactCommand extends ContactCommand {
    public static final String SUB_COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + SUB_COMMAND_WORD
            + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindContactCommand(NameContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof FindContactCommand)) {
            return false;
        }

        FindContactCommand otherFindContactCommand = (FindContactCommand) other;
        return predicate.equals(otherFindContactCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
