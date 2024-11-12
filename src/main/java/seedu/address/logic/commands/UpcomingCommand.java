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
 * Lists all pending deliveries based on specified date(s).
 */
public class UpcomingCommand extends Command {
    public static final String COMMAND_WORD = "upcoming";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Lists all pending deliveries with delivery date before and/or after the specified dates.\n"
            + "Parameters: "
            + PREFIX_START_DATE + "DELIVERY_DATE_TIME "
            + PREFIX_END_DATE + "DELIVERY_DATE_TIME\n"
            + "Must have at least one DELIVERY_DATE_TIME.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_START_DATE + "19-12-2022 08:00"
            + " " + PREFIX_END_DATE + "18-06-2023 17:00";
    public static final String MESSAGE_SUCCESS = Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW;
    private final List<Predicate<Delivery>> predicates;
    /**
     * Creates an UpcomingCommand to display the list of pending deliveries before the
     * specified date.
     *
     * @param predicates The list of predicates to filter the delivery list by.
     */
    public UpcomingCommand(List<Predicate<Delivery>> predicates) {
        requireNonNull(predicates);
        this.predicates = predicates;
    }
    /**
     * Executes the UpcomingCommand and returns the result message.
     *
     * @param model {@code Model} which the UpcomingCommand should operate on.
     * @return Feedback message of the UpcomingCommand result for display.
     * @throws CommandException If an error occurs during UpcomingCommand execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        Predicate<Delivery> upcomingDeliveryPredicate = predicates.stream().reduce(Predicate::and).orElse(x -> true);
        model.updateFilteredDeliveryList(upcomingDeliveryPredicate);
        int numberOfUpcomingDeliveries = model.getFilteredDeliveryList().size();
        return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfUpcomingDeliveries));
    }

    /**
     * Returns true if List of Predicates for both objects are the same.
     *
     * @param other Object to be compared with.
     * @return True if object is an instance of UpcomingCommand and contains the same
     *         Predicate objects in the list.
     */
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
     * Represents the String value of UpcomingCommand paired with the list of Predicates.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", this.predicates)
                .toString();
    }
}
