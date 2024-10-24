package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.DateAbsentPredicate;

/**
 * Finds and lists all students in TAHub who were not present on the specified
 * date.
 */
public class ListAbsenteesCommand extends Command {

    public static final String COMMAND_WORD = "absentees";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students who were absent on "
            + "the specified date and displays them as a list with index numbers.\n"
            + "Parameters:" + PREFIX_DATE + "DATETIME \n"
            + "Example: " + COMMAND_WORD + PREFIX_DATE + " 01/01/2021";

    private final DateAbsentPredicate predicate;

    public ListAbsenteesCommand(DateAbsentPredicate predicate) {
        requireNonNull(predicate);
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
        if (!(other instanceof ListAbsenteesCommand)) {
            return false;
        }

        ListAbsenteesCommand otherFindCommand = (ListAbsenteesCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
