package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Property;

/**
 * Adds a property to the list of properties to sell for a specific contact.
 */
public class AddPropertyToSellCommand extends Command {
    public static final String COMMAND_WORD = "addSell";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property to the list of properties to sell. "
            + "Parameters: "
            + "PROPERTY_TYPE "
            + "PROPERTY_NAME "
            + "UNIT_NUMBER "
            + "PRICE "
            + "Example: " + COMMAND_WORD + " "
            + "Condo "
            + "The Florence Residences "
            + "01-01 "
            + "1000000";

    public static final String MESSAGE_SUCCESS = "New property added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists "
            + "in the list of properties to sell";

    private final Property propertyToSellToBeAdded;

    /**
     * Creates an AddPropertyToSellCommand to add the specified {@code Property}
     */
    public AddPropertyToSellCommand(Property property) {
        requireNonNull(property);
        this.propertyToSellToBeAdded = property;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        /*if (model.hasProperty(property)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        }

        model.addProperty(property);
        return new CommandResult(String.format(MESSAGE_SUCCESS, property));*/
        return new CommandResult("Hello from AddPropertyToSellCommand");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPropertyToSellCommand // instanceof handles nulls
                && propertyToSellToBeAdded.equals(((AddPropertyToSellCommand) other).propertyToSellToBeAdded));
    }
}
