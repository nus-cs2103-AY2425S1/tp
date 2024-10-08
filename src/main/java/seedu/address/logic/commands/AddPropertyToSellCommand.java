package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;
import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Property;

/**
 * Adds a property to the list of properties to sell for a specific contact.
 */
public class AddPropertyToSellCommand extends Command {
    public static final String COMMAND_WORD = "addSell";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property to the list of properties to sell"
            + "for the specific contact."
            + "Parameters: INDEX (Must be a positive integer)"
            + PREFIX_HOUSING_TYPE + "[HOUSING_TYPE]"
            + PREFIX_SELLING_PRICE + "[SELLING_PRICE]"
            + PREFIX_POSTAL_CODE + "[POSTAL_CODE]"
            + PREFIX_UNIT_NUMBER + "[UNIT_NUMBER]"
            + PREFIX_TAG + "[TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_HOUSING_TYPE + "Condo "
            + PREFIX_SELLING_PRICE + "1.65M "
            + PREFIX_POSTAL_CODE + "567510 "
            + PREFIX_UNIT_NUMBER + "10-65 "
            + PREFIX_TAG + "Extremely spacious "
            + PREFIX_TAG + "Near MRT";

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

    public AddPropertyToSellCommand() {
        this.propertyToSellToBeAdded = null;
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
