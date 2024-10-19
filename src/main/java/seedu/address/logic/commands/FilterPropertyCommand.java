package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.MatchingPrice;
import seedu.address.model.property.Type;

/**
 * Filters properties based on their optional {@code PropertyType}.
 */
public class FilterPropertyCommand extends Command {
    /** The command word to trigger the filtering action. */
    public static final String COMMAND_WORD = "filterproperty";

    /**
     * Usage information for the filterclient command.
     * Provides a description of the command's purpose and the format for entering client names.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the clients based on PropertyType and price. "
            + "Parameters: PropertyType (must be a string) and/or price (must be an integer) "
            + PREFIX_TYPE + "[TYPE]\n" + PREFIX_LTE + "[MATCHING_PRICE]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TYPE + "CONDO" + " " + PREFIX_LTE + "30000000";

    public static final String MESSAGE_SUCCESS = "Listed filtered properties";
    public static final String MESSAGE_UNSUCCESS = "Filtered properties failed";

    /** The type used for filtering. */
    private Type type;
    private MatchingPrice lteObj;
    private MatchingPrice gteObj;

    /**
     * Constructs a FilterClientCommand to filter the specified {@code Property}.
     *
     * @param type The client to filter by.
     * @throws NullPointerException If the provided client is null.
     */
    public FilterPropertyCommand(Type type, MatchingPrice lteObj, MatchingPrice gteObj) {
        this.type = type;
        this.lteObj = lteObj;
        this.gteObj = gteObj;
    }

    /**
     * Executes the command to filter properties based on the provided type.
     *
     * @param model The model which contains the client data to be filtered.
     * @return The result of the command execution.
     * @throws CommandException If the filtering operation fails.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        String typeString = type == null ? "" : type.toString();
        int lte = lteObj == null ? Integer.MAX_VALUE : lteObj.toInteger();
        int gte = gteObj == null ? 0 : gteObj.toInteger();

        model.updateFilteredPropertyList(property -> property.getType().toString()
                .matches("(?i)^" + typeString + ".*")
            && MatchingPrice.getMatchingPrice(property.getAsk(), property.getBid()) <= lte
            && MatchingPrice.getMatchingPrice(property.getAsk(), property.getBid()) >= gte);
        model.setDisplayProperties();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof FilterPropertyCommand)) {
            return false;
        }
        // state check
        FilterPropertyCommand e = (FilterPropertyCommand) other;
        return type.equals(e.type);
    }
}
