package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.StatusContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose application status contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FilterStatusCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons whose application status contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [Applied / Screening / Interview Scheduled "
            + "/ Interviewed / Offer / Onboarding / Hired / Rejected]\n"
            + "Example 1: " + COMMAND_WORD + " Rejected\n"
            + "Example 2: " + COMMAND_WORD + " Onboarding\n";

    private final StatusContainsKeywordsPredicate predicate;

    public FilterStatusCommand(StatusContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert model != null : "Model should not be null";

        model.updateFilteredPersonList(predicate);

        assert model.getFilteredPersonList() != null : "Filtered person list should not be null after update";

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterStatusCommand)) {
            return false;
        }

        FilterStatusCommand otherFindDoctorCommand = (FilterStatusCommand) other;
        return predicate.equals(otherFindDoctorCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
