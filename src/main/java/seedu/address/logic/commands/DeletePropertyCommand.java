package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Unit;

/**
 * Deletes a specific property identified by its postal code and unit number.
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
    public static final String MESSAGE_PROPERTY_NOT_FOUND = "Property with postal code %s and unit number %s not "
            + "found.";

    private static final Logger logger = Logger.getLogger(DeletePropertyCommand.class.getName());
    private final PostalCode postalCode;
    private final Unit unitNumber;

    /**
     * Constructs a {@code DeletePropertyCommand} with the specified postal code and unit number to delete a property.
     *
     * @param postalCode The postal code of the property to delete.
     * @param unitNumber The unit number of the property to delete.
     */
    public DeletePropertyCommand(PostalCode postalCode, Unit unitNumber) {
        this.postalCode = requireNonNull(postalCode, "Postal code cannot be null");
        this.unitNumber = requireNonNull(unitNumber, "Unit number cannot be null");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model cannot be null");
        model.updateFilteredPropertyList(Model.PREDICATE_SHOW_ALL_PROPERTIES);

        logger.log(Level.INFO, "Executing DeletePropertyCommand with postalCode={0}, unitNumber={1}",
                new Object[]{postalCode, unitNumber});

        Property propertyToDelete = findPropertyToDelete(model)
                .orElseThrow(() -> new CommandException(generatePropertyNotFoundMessage()));

        model.deleteProperty(propertyToDelete);
        logger.log(Level.INFO, "Successfully deleted property: {0}", propertyToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PROPERTY_SUCCESS, Messages.format(propertyToDelete)));
    }

    /**
     * Finds the property to delete in the model based on postal code and unit number.
     *
     * @param model The model containing the property list.
     * @return An optional containing the property if found, empty otherwise.
     */
    private Optional<Property> findPropertyToDelete(Model model) {
        assert model != null : "Model should not be null at this point";
        return model.getFilteredPropertyList().stream()
                .filter(property -> property.getPostalCode().equals(postalCode)
                        && property.getUnit().equals(unitNumber))
                .findFirst();
    }

    /**
     * Generates an error message indicating the property was not found.
     *
     * @return The formatted error message.
     */
    private String generatePropertyNotFoundMessage() {
        return String.format(MESSAGE_PROPERTY_NOT_FOUND, postalCode, unitNumber);
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

        DeletePropertyCommand that = (DeletePropertyCommand) other;
        return postalCode.equals(that.postalCode)
                && unitNumber.equals(that.unitNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("postalCode", postalCode)
                .add("unitNumber", unitNumber)
                .toString();
    }
}
