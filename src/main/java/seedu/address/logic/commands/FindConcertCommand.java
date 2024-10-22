package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.commons.NameContainsKeywordsPredicate;
import seedu.address.model.concert.Concert;

/**
 * Finds and lists all concerts in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindConcertCommand extends Command {

    public static final String COMMAND_WORD = "findc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all concerts whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "coachella glastonbury";

    private final Predicate<Concert> predicate;

    /**
     * Creates a FindConcertCommand to find all concerts who satisfy the {@code predicate}.
     */
    public FindConcertCommand(NameContainsKeywordsPredicate<Concert> predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredConcertList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CONCERTS_LISTED_OVERVIEW, model.getFilteredConcertList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindConcertCommand)) {
            return false;
        }

        FindConcertCommand otherFindCommand = (FindConcertCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
