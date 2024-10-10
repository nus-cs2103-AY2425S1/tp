package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
// import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
// import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
// import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
// import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
// import seedu.address.model.person.Property;

/**
 * Adds a property to the list of properties to buy for a specific contact.
 */
public class AddPropertyToBuyCommand extends Command {
    public static final String COMMAND_WORD = "addBuy";
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property to the list of properties to buy"
//            + "for the specific contact."
//            + "Parameters: INDEX (Must be a positive integer)"
//            + PREFIX_HOUSING_TYPE + "[HOUSING_TYPE]"
//            + PREFIX_SELLING_PRICE + "[SELLING_PRICE]"
//            + PREFIX_POSTAL_CODE + "[POSTAL_CODE]"
//            + PREFIX_UNIT_NUMBER + "[UNIT_NUMBER]"
//            + PREFIX_TAG + "[TAG]...\n"
//            + "Example: " + COMMAND_WORD + " "
//            + PREFIX_HOUSING_TYPE + "Condo "
//            + PREFIX_SELLING_PRICE + "1.65M "
//            + PREFIX_POSTAL_CODE + "567510 "
//            + PREFIX_UNIT_NUMBER + "10-65 "
//            + PREFIX_TAG + "Extremely spacious "
//            + PREFIX_TAG + "Near MRT";

    public static final String MESSAGE_SUCCESS = "New property added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists "
            + "in the list of properties to sell";

//    private final Property propertyToBuyToBeAdded;

    /**
     * Creates an AddPropertyToBuyCommand to add the specified {@code Property}
     */
//    public AddPropertyToBuyCommand(Property property) {
//        requireNonNull(property);
//        this.propertyToBuyToBeAdded = property;
//    }

//    public AddPropertyToBuyCommand() {
//        this.propertyToBuyToBeAdded = null;
//    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        /*if (model.hasProperty(property)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        }

        model.addProperty(property);
        return new CommandResult(String.format(MESSAGE_SUCCESS, property));*/
        return new CommandResult("Hello from AddPropertyToBuyCommand");
    }

//    @Override
//    public boolean equals(Object other) {
//        return other == this // short circuit if same object
//                || (other instanceof AddPropertyToBuyCommand // instanceof handles nulls
//                && propertyToBuyToBeAdded.equals(((AddPropertyToBuyCommand) other).propertyToBuyToBeAdded));
//    }
}