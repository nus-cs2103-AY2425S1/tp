package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.GamesContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose games contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindGameCommand extends Command {

    public static final String COMMAND_WORD = "findgame";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose games contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " league overwatch clash";
    private static final boolean IS_UNDOABLE = true;

    private final GamesContainsKeywordsPredicate predicate;
    private Predicate<Person> previousPredicate;

    public FindGameCommand(GamesContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        previousPredicate = model.getCurrentPredicate();
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public void undo(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(previousPredicate);
    }

    @Override
    public boolean canBeUndone() {
        return IS_UNDOABLE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindGameCommand)) {
            return false;
        }

        FindGameCommand otherFindGameCommand = (FindGameCommand) other;
        return predicate.equals(otherFindGameCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
