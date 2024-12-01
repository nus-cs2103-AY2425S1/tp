package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts and lists all persons in the address book by the specified criteria.
 * The available sorting criteria are by name or by appointment.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String COMMAND_WORD_SHORT = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons by the specified criteria.\n"
            + "Parameters: name | appointment\n"
            + "Example: " + COMMAND_WORD + " name\n"
            + "         " + COMMAND_WORD + " appointment\n";

    public static final String MESSAGE_INVALID_SORTING_CRITERIA = "Invalid sorting criteria.\n";
    public static final String MESSAGE_SORTED_BY_NAME = "Sorted by name.";
    public static final String MESSAGE_SORTED_BY_APPOINTMENT = "Sorted by appointment.";

    /**
     * The available sorting criteria: sorting by name or by appointment.
     */
    public enum SortType {
        NAME, APPOINTMENT
    }

    private final SortType sortType;

    /**
     * Creates a SortCommand to sort persons by the specified {@code SortType}.
     *
     * @param sortType The type of sorting to be applied (name or appointment).
     */
    public SortCommand(SortType sortType) {
        this.sortType = sortType;
    }

    /**
     * Executes the sort command and sorts the persons in the address book based on the specified criteria.
     * If sorting by name, it sorts alphabetically. If sorting by appointment, it sorts chronologically
     * while placing persons with no appointment at the bottom.
     *
     * @param model {@code Model} which contains the address book data.
     * @return The result of the command execution.
     * @throws CommandException if an invalid sorting criteria is specified.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String resultMessage;

        switch (sortType) {
        case NAME:
            // Sort persons by name alphabetically
            model.updateSortedPersonList(Comparator.comparing(person -> person.getName().toString()));
            resultMessage = MESSAGE_SORTED_BY_NAME;
            break;
        case APPOINTMENT:
            // Sort persons by appointment, placing null appointments at the bottom
            model.updateSortedPersonList(Comparator.comparing(person ->
                            person.getAppointment() == null ? null : person.getAppointment().value,
                    Comparator.nullsLast(Comparator.naturalOrder())));
            resultMessage = MESSAGE_SORTED_BY_APPOINTMENT;
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_SORTING_CRITERIA);
        }

        return new CommandResult(
                String.format(resultMessage, model.getFilteredPersonList().size()));
    }

    /**
     * Compares this SortCommand to another object to check for equality.
     *
     * @param other The object to compare to.
     * @return True if both objects are of the same type and have the same {@code SortType}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return sortType == otherSortCommand.sortType;
    }
}
