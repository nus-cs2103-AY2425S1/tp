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
import seedu.address.model.delivery.DeliveryWrapper;
import seedu.address.model.delivery.SupplierIndex;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.SupplierStatus;

/**
 * Adds a delivery to the address book.
 */
public class AddDeliveryCommand extends AddCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_DELIVERY
            + " : Adds a delivery to the address book. "
            + "Parameters: "
            + PREFIX_DATETIME + "dd-mm-yyyy hh:mm "
            + PREFIX_SUPPLIER_INDEX + "SUPPLIER_INDEX "
            + PREFIX_PRODUCT + "PRODUCT "
            + PREFIX_QUANTITY + "QUANTITY kg/g/L/mL/units "
            + PREFIX_COST + "COST\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DELIVERY + " "
            + PREFIX_DATETIME + "18-06-2024 17:00 "
            + PREFIX_SUPPLIER_INDEX + "1 "
            + PREFIX_PRODUCT + "bread "
            + PREFIX_QUANTITY + "500 g "
            + PREFIX_COST + "25.50 ";
    public static final String MESSAGE_SUCCESS = "New delivery added: %1$s";
    public static final String MESSAGE_DUPLICATE_DELIVERY = "Delivery is already added!!!";
    public static final String MESSAGE_INACTIVE_SUPPLIER = "Supplier is currently inactive!!!";
    private final DeliveryWrapper deliveryWrapper;
    /**
     * Creates an AddDeliveryCommand to add the specified {@code deliveryToAdd}
     */
    public AddDeliveryCommand(DeliveryWrapper deliveryWrapper) {
        requireNonNull(deliveryWrapper);
        this.deliveryWrapper = deliveryWrapper;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Supplier sender = this.getSupplierBasedOnIndex(model);
        if (sender.getStatus().equals(new SupplierStatus("inactive"))) {
            throw new CommandException(MESSAGE_INACTIVE_SUPPLIER);
        }
        deliveryWrapper.setDeliverySupplier(sender);
        Delivery deliveryToAdd = deliveryWrapper.getDelivery();
        if (model.hasDelivery(deliveryToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DELIVERY);
        }
        model.addDelivery(deliveryToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(deliveryToAdd)));
    }

    /**
     * Retrieves the supplier from addressbook corresponding to the specified supplier index.
     *
     * @param model AddressBook model.
     * @return Supplier that currently has index value equal to supplier index.
     * @throws CommandException If supplier index is invalid.
     */
    public Supplier getSupplierBasedOnIndex(Model model) throws CommandException {
        List<Supplier> lastShownList = model.getModifiedSupplierList();
        SupplierIndex supplierIndex = this.deliveryWrapper.getSupplierIndex();
        if (supplierIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }
        Supplier sender = lastShownList.get(supplierIndex.getZeroBased());
        return sender;
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
        return this.deliveryWrapper.equals(otherAddDeliveryCommand.deliveryWrapper);
    }

    /**
     * Represents the String value of AddDeliveryCommand paired with the deliveryToAdd.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("deliveryWrapper", this.deliveryWrapper)
                .toString();
    }
}
