package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.SchedulePredicate;

/**
 * View the patient to meet on the given day.
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who have their next appointment "
          + "date on the date keyed in by the user and displays them as a list with index numbers.\n"
          + "Parameters: " + PREFIX_DATE + "DATE \n"
          + "Example: " + COMMAND_WORD + " " + PREFIX_DATE + "12 October 2024";

    private SchedulePredicate predicate;

    public ScheduleCommand(SchedulePredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
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
        if (!(other instanceof ScheduleCommand)) {
            return false;
        }

        ScheduleCommand otherScheduleCommand = (ScheduleCommand) other;
        return predicate.equals(otherScheduleCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
              .add("predicate", predicate)
              .toString();
    }

}
