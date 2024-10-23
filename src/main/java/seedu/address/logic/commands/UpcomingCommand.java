package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.DeliveryIsUpcomingPredicate;

/**
 * Lists all pending deliveries that are completed before a specified date.
 */
public class UpcomingCommand extends Command {
    public static final String COMMAND_WORD = "upcoming";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Lists all pending deliveries with completion date before the specified date.\n"
            + "Parameters: Completion_Date_Time (dd-mm-yyyy hh:mmr)\n"
            + "Example: " + COMMAND_WORD + " 18-06-2023 17:00";
    public static final String MESSAGE_SUCCESS = Messages.MESSAGE_DELIVERIES_LISTED_OVERVIEW;
    private final DeliveryIsUpcomingPredicate predicate;

    /**
     * Creates an UpcomingCommand to display the list of pending deliveries before the
     * specified date.
     */
    public UpcomingCommand(DeliveryIsUpcomingPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
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
        model.updateFilteredDeliveryList(predicate);
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
        return this.predicate.equals(otherUpcomingCommand.predicate);
    }

    /**
     * Represents the String value of UpcomingCommand paired with the completionDateTime.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("completionDateTime", this.predicate)
                .toString();
    }
}
