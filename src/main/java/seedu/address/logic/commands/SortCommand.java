package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Represents a command to sort the person list by interview score.
 * The sort order can be either ascending or descending.
 * Usage:
 * - Parameters: 'a' for ascending or 'd' for descending
 * - Example: sort a
 * The command will sort the list of persons based on their interview scores
 * and update the model with the sorted list.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the person list by interview score.\n"
            + "Parameters: a (ascending) or d (descending)\n"
            + "Example: " + COMMAND_WORD + " a";

    public static final String MESSAGE_SUCCESS = "Sorted person list by interview score in %s order";

    private final boolean isAscending;

    public SortCommand(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> sortedList = model.getFilteredPersonList().stream()
                .sorted((p1, p2) -> Float.compare(p1.getInterviewScore().toFloat(), p2.getInterviewScore().toFloat()))
                .collect(Collectors.toList());

        if (!isAscending) {
            sortedList = sortedList.stream()
                    .sorted((p1, p2) -> Float.compare(
                            p2.getInterviewScore().toFloat(), p1.getInterviewScore().toFloat()))
                    .collect(Collectors.toList());
        }

        model.setPersons(sortedList);
        return new CommandResult(String.format(MESSAGE_SUCCESS, isAscending ? "ascending" : "descending"));
    }
}
