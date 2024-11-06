package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;
/**
 * Lists all pending deliveries that are completed before a specified date.
 */
public class UpcomingCommand extends Command {
    public static final String COMMAND_WORD = "upcoming";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Lists all pending deliveries with completion date before and after the specified dates.\n"
            + "Must have at least a START_DATE or END_DATE.\n"
            + "Parameters: "
            + PREFIX_START_DATE + "START_DATE "
            + PREFIX_END_DATE + "END_DATE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_START_DATE + "19-12-2022 08:00"
            + " " + PREFIX_END_DATE + "18-06-2023 17:00";
    public static final String MESSAGE_SUCCESS = Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW;
    private final List<Predicate<Delivery>> predicates;

    /**
     * Creates an UpcomingCommand to display the list of pending deliveries before the
     * specified date.
     */
    public UpcomingCommand(List<Predicate<Delivery>> predicates) {
        requireNonNull(predicates);
        this.predicates = predicates;
    }

    /**
     * Retrieves the list of deliveries that have completion date less than specified date.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Success message to inform user.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        Predicate<Delivery> upcomingDeliveryPredicate = predicates.stream().reduce(Predicate::and).orElse(x -> true);
        model.updateFilteredDeliveryList(upcomingDeliveryPredicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getFilteredDeliveryList().size()));
    }

    /**
     * Returns true if completionDateTime object of both objects are same.
     *
     * @param other Object to be compared with
     * @return True if object is an instance of UpcomingCommand and both
     *          completionDateTime have the same value.
     * */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpcomingCommand)) {
            return false;
        }

        UpcomingCommand otherUpcomingCommand = (UpcomingCommand) other;
        return this.predicates.equals(otherUpcomingCommand.predicates);
    }

    /**
     * Represents the String value of UpcomingCommand paired with the completionDateTime.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", this.predicates)
                .toString();
    }
}
