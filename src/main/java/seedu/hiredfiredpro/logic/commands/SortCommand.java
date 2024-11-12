package seedu.hiredfiredpro.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.hiredfiredpro.logic.commands.exceptions.CommandException;
import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.person.Person;

/**
 * Represents a command to sort the candidate list by interview score.
 * The sort order can be either ascending or descending.
 * Usage:
 * - Parameters: 'a' for ascending or 'd' for descending
 * - Example: sort a
 * The command will sort the list of candidates based on their interview scores
 * and update the model with the sorted list.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the candidate list by interview score.\n"
            + "Parameters: a (ascending) or d (descending)\n"
            + "Example: " + COMMAND_WORD + " a";

    public static final String MESSAGE_SUCCESS = "Sorted candidate list by interview score in %s order";

    private final boolean isAscending;

    public SortCommand(boolean isAscending) {
        this.isAscending = isAscending;
    }

    public boolean isAscending() {
        return isAscending;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Comparator<Person> comparator = (p1, p2) -> {
            int comparison = Float.compare(p1.getInterviewScore().toFloat(), p2.getInterviewScore().toFloat());
            return isAscending ? comparison : -comparison;
        };

        model.updateSortedPersonList(comparator);

        return new CommandResult(String.format(MESSAGE_SUCCESS, isAscending ? "ascending" : "descending"));
    }
}
