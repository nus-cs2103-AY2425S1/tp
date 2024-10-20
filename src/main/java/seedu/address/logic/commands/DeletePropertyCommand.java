package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Unit;

/**
 * Deletes a property using its postal code and unit number.
 */
public class DeletePropertyCommand extends Command {

    public static final String COMMAND_WORD = "deleteproperty";

    public static final String MESSAGE_USAGE = String.format(
            "%s: Deletes the property unit identified by its postal code and unit number.\n"
                    + "Parameters: %sPOSTAL_CODE %sUNIT_NUMBER\n"
                    + "Restrictions: POSTAL_CODE must follow the Singapore postal code format "
                    + "i.e. be a 6 digit integer (between 000000 to 999999)\n"
                    + "UNIT_NUMBER must be in the format (XXX-XXX)",
            COMMAND_WORD,
            PREFIX_POSTALCODE,
            PREFIX_UNITNUMBER
    );


    public static final String MESSAGE_DELETE_PROPERTY_SUCCESS = "Deleted property: %1$s";

    private final PostalCode postalCode;
    private final Unit unitNumber;

    /**
     * Constructs a {@code DeletePropertyCommand} with the specified postal code and unit number to delete a property.
     *
     * @param postalCode The postal code of the property to delete.
     * @param unitNumber The unit number of the property to delete.
     */
    public DeletePropertyCommand(PostalCode postalCode, Unit unitNumber) {
        this.postalCode = postalCode;
        this.unitNumber = unitNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Property propertyToDelete = model.getFilteredPropertyList().stream()
                .filter(property -> property.getPostalCode().equals(postalCode)
                        && property.getUnit().equals(unitNumber))
                .findFirst().orElseThrow(() ->
                        new CommandException(String.format("Property not found. ", postalCode,
                                unitNumber)));

        model.deleteProperty(propertyToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PROPERTY_SUCCESS, Messages.format(propertyToDelete)));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePropertyCommand)) {
            return false;
        }

        DeletePropertyCommand otherDeletePropertyCommand = (DeletePropertyCommand) other;
        return postalCode.equals(otherDeletePropertyCommand.postalCode)
                && unitNumber.equals(otherDeletePropertyCommand.unitNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("postalCode", postalCode)
                .add("unitNumber", unitNumber)
                .toString();
    }
}
