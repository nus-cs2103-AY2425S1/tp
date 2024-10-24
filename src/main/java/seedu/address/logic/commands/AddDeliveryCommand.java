package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_INDEX;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.SupplierIndex;
import seedu.address.model.person.Person;

/**
 * Adds a delivery to the address book.
 */
public class AddDeliveryCommand extends Command {
    public static final String COMMAND_WORD = "add";

    //to change later
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_DELIVERY
            + " : Adds a delivery to the address book. "
            + "Parameters: "
            + PREFIX_DATETIME + "dd-mm-yyyy hh:mm "
            + PREFIX_SUPPLIER_INDEX + "SUPPLIER_INDEX "
            + PREFIX_PRODUCT + "PRODUCT "
            + PREFIX_QUANTITY + "QUANTITY kg/g/litres/ml/units "
            + PREFIX_COST + "COST\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DELIVERY + " "
            + PREFIX_DATETIME + "18-06-2024 17:00 "
            + PREFIX_SUPPLIER_INDEX + "1 "
            + PREFIX_PRODUCT + "bread "
            + PREFIX_QUANTITY + "500 g "
            + PREFIX_COST + "25.50 ";
    public static final String MESSAGE_SUCCESS = "New delivery added: %1$s";
    public static final String MESSAGE_DUPLICATE_DELIVERY = "Delivery is already added!!!";
    private final Delivery deliveryToAdd;
    /**
     * Creates an AddDeliveryCommand to add the specified {@code deliveryToAdd}
     */
    public AddDeliveryCommand(Delivery deliveryToAdd) {
        requireNonNull(deliveryToAdd);
        this.deliveryToAdd = deliveryToAdd;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //Need to update when list changes name
        List<Person> lastShownList = model.getFilteredPersonList();
        SupplierIndex supplierIndex = this.deliveryToAdd.getSupplierIndex();
        if (supplierIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person sender = lastShownList.get(supplierIndex.getZeroBased());
        this.deliveryToAdd.setDeliverySender(sender);
        if (model.hasDelivery(deliveryToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DELIVERY);
        }
        model.addDelivery(this.deliveryToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(this.deliveryToAdd)));
    }

    /**
     * Returns true if deliveryToAdd object of both objects are same.
     *
     * @param other Object to be compared with
     * @return True if object is an instance of AddDeliveryCommand and both
     *          deliveries have the same identity and data fields.
     * */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddDeliveryCommand)) {
            return false;
        }

        AddDeliveryCommand otherAddDeliveryCommand = (AddDeliveryCommand) other;
        return this.deliveryToAdd.equals(otherAddDeliveryCommand.deliveryToAdd);
    }

    /**
     * Represents the String value of AddDeliveryCommand paired with the deliveryToAdd.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("deliveryToAdd", this.deliveryToAdd)
                .toString();
    }
}
