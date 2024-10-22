package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PhoneBeginsWithKeywordPredicate;

/**
 * Finds and lists all persons in address book whose phone number begins with the argument keywords.
 * Keyword matching has to be all numbers.
 */
public class FindPhoneCommand extends Command {

    public static final String COMMAND_WORD = "fp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose phone number begins with "
            + "the specified keywords (integers only with no spacing) and displays them as a list with index numbers.\n"
            + "Parameters: integers \n"
            + "Example: " + COMMAND_WORD + " 91234567";

    private final PhoneBeginsWithKeywordPredicate predicate;

    public FindPhoneCommand(PhoneBeginsWithKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getDisplayPersons().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindPhoneCommand)) {
            return false;
        }

        FindPhoneCommand otherFindCommand = (FindPhoneCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
