package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_FOUND_INTEREST;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_PERSON_FOUND_INTEREST;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.InterestContainsKeywordsPredicate;

/**
 * Finds and lists all persons in the address book whose interests contain the argument keyword.
 * Keyword matching is case-insensitive.
 */
public class FindByInterestCommand extends Command {

    public static final String COMMAND_WORD = "findi"; // Command keyword for find by interest

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose interests contain "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: i/KEYWORD\n"
            + "Example: " + COMMAND_WORD + " i/reading";

    private final InterestContainsKeywordsPredicate predicate;

    public FindByInterestCommand(InterestContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        int count = model.getFilteredPersonList().size();
        String message;
        if (count == 0) {
            message = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        } else if (count == 1) {
            message = MESSAGE_PERSON_FOUND_INTEREST;
        } else {
            message = String.format(MESSAGE_PERSONS_FOUND_INTEREST, count);
        }
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByInterestCommand // instanceof handles nulls
                && predicate.equals(((FindByInterestCommand) other).predicate)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
