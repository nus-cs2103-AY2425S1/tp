package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.preferredtime.PreferredTimeOverlapsRangesPredicate;


/**
 * Finds and lists all persons in address book whose preferred time ranges overlap with the specified time range.
 * Time matching will not acknowledge the only overlap in boundary.
 */
public class FindTimeCommand extends Command {
    public static final String COMMAND_WORD = "findtime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose preferred time ranges "
            + "overlap with the specified time ranges "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: TIME-TIME [TIME-TIME]...\n"
            + "Example: " + COMMAND_WORD + " 1100-1230 2130-2130";
    private static final boolean IS_UNDOABLE = true;

    private final PreferredTimeOverlapsRangesPredicate predicate;
    private Predicate<Person> previousPredicate;

    public FindTimeCommand(PreferredTimeOverlapsRangesPredicate predicate) {
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
        if (!(other instanceof FindTimeCommand)) {
            return false;
        }

        FindTimeCommand otherFindTimeCommand = (FindTimeCommand) other;
        return predicate.equals(otherFindTimeCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
