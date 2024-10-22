package seedu.address.logic.commands;

/**
 * Sorts and lists suppliers or deliveries based on a given comparator.
 */
public abstract class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = "SortDeliveryCommand.MESSAGE_USAGE;";
}
