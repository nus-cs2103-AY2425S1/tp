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
 * Adds a delivery to VendorVault.
 */
public class AddDeliveryCommand extends AddCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_DELIVERY
            + " : Adds a delivery to the address book. "
            + "Parameters: "
            + PREFIX_DATETIME + "DELIVERY_DATE_TIME "
            + PREFIX_SUPPLIER_INDEX + "SUPPLIER_INDEX "
            + PREFIX_PRODUCT + "PRODUCT "
            + PREFIX_QUANTITY + "QUANTITY "
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
     * Creates an AddDeliveryCommand to add the specified delivery wrapped by {@code deliveryWrapper}.
     *
     * @param deliveryWrapper A wrapper class that contains the delivery to add and the SupplierIndex object.
     */
    public AddDeliveryCommand(DeliveryWrapper deliveryWrapper) {
        requireNonNull(deliveryWrapper);
        this.deliveryWrapper = deliveryWrapper;
    }

    /**
     * Executes the AddDeliveryCommand and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the AddDeliveryCommand result for display.
     * @throws CommandException If an error occurs during the AddDeliveryCommand execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Supplier sender = this.getSupplierBasedOnIndex(model);
        assert sender != null;
        SupplierStatus senderStatus = sender.getStatus();
        if (senderStatus.equals(new SupplierStatus("inactive"))) {
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
     * Retrieves the supplier from VendorVault displayed list corresponding to
     * the specified supplier index.
     *
     * @param model VendorVault model.
     * @return Supplier that currently has index value equal to SupplierIndex value wrapped in deliveryWrapper.
     * @throws CommandException If SupplierIndex in deliveryWrapper is invalid.
     */
    public Supplier getSupplierBasedOnIndex(Model model) throws CommandException {
        List<Supplier> lastShownList = model.getModifiedSupplierList();
        SupplierIndex supplierIndex = this.deliveryWrapper.getSupplierIndex();
        if (supplierIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }
        Supplier deliverySupplier = lastShownList.get(supplierIndex.getZeroBased());
        return deliverySupplier;
    }

    /**
     * Returns true if deliveryWrapper object of both objects are same.
     *
     * @param other Object to be compared with.
     * @return True if object is an instance of AddDeliveryCommand and both
     *         deliveryWrapper are equal.
     */
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
     * Represents the String value of AddDeliveryCommand paired with the deliveryWrapper.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("deliveryWrapper", this.deliveryWrapper)
                .toString();
    }
}
