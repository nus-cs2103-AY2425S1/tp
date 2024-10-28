package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Ask;
import seedu.address.model.property.Bid;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;

/**
 * Adds a {@code Property} to the address book.
 * Extends {@link Command} and uses its functionality to add a specific type of property: {@code Property}.
 */
public class AddPropertyCommand extends Command {
    public static final String COMMAND_WORD = "addproperty";

    public static final String MESSAGE_USAGE = String
            .format("%s: Adds property to the address book.\n"
                    + "Parameters: %sPOSTALCODE %sUNIT %sTYPE %sASK %sBID\n"
                    + "Restrictions:\n"
                    + "\t%s\n\t%s\n\t%s\n\t%s\n\t%s",
                    COMMAND_WORD, PREFIX_POSTALCODE, PREFIX_UNITNUMBER, PREFIX_TYPE, PREFIX_ASK, PREFIX_BID,
                    PostalCode.MESSAGE_CONSTRAINTS, Unit.MESSAGE_CONSTRAINTS, Type.MESSAGE_CONSTRAINTS,
                    Ask.MESSAGE_CONSTRAINTS, Bid.MESSAGE_CONSTRAINTS);

    public static final String MESSAGE_SUCCESS = "New property added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists in the address book";
    private static final Logger logger = LogsCenter.getLogger(AddPropertyCommand.class);

    private final Property toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddPropertyCommand(Property property) {
        requireNonNull(property);
        assert property != null : "Property cannot be null";
        logger.info("AddProperty object created for: " + property);
        toAdd = property;
    }

    /**
     * Executes the AddPropertyCommand.
     * Adds the property to the model if the property does not already exist in the address book.
     *
     * @param model The model which contains the address book data.
     * @return A {@link CommandResult} with a success message if the property was added.
     * @throws CommandException if the seller already exists in the address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert toAdd != null : "Property cannot be null";
        logger.info("Execution begining for " + toAdd);

        if (model.hasProperty(toAdd)) {
            logger.warning("Attempting to create duplicate property for: " + toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        }

        model.addProperty(toAdd);
        logger.info("Property successfully added for: " + toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPropertyCommand)) {
            return false;
        }

        AddPropertyCommand otherAddCommand = (AddPropertyCommand) other;
        return toAdd.isSameProperty(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
