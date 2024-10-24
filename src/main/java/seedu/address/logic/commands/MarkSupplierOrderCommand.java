package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.SupplyOrder;
import seedu.address.model.order.SupplierOrderList;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Inventory;
import seedu.address.model.product.Product;

import java.util.List;

public class MarkSupplierOrderCommand extends Command {
    public static final String COMMAND_WORD = "markSupplierOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the supplier order at the given index as completed and updates the inventory.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_ORDER_SUCCESS = "Marked supplier order as completed: %1$s";
    public static final String MESSAGE_ORDER_ALREADY_COMPLETED = "The order at index %1$s is already completed.";
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private final int targetIndex;

    public MarkSupplierOrderCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        SupplierOrderList supplierOrderList = model.getSupplierOrderList();
        Inventory inventory = model.getInventory();  // Get inventory from the model

        // Validate index
        if (targetIndex <= 0 || targetIndex > supplierOrderList.getOrders().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        // Retrieve the supplier order at the target index
        SupplyOrder supplyOrder = supplierOrderList.getOrders().get(targetIndex - 1);

        // Check if the order is already completed
        if (supplyOrder.getStatus() == OrderStatus.COMPLETED) {
            return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_COMPLETED, targetIndex));
        }

        List<? extends Product> items = supplyOrder.getItems();  // List of items from the supplier order

        // Filter to ensure we only process Ingredient
        for (Product product : items) {
            if (product instanceof Ingredient ingredient) {
                inventory.addStock(ingredient.getProductId(), 1);  // Add stock to inventory, currently assume quantity is one
            }
        }

        // Mark the order as completed
        supplyOrder.setStatus(OrderStatus.COMPLETED);

        supplierOrderList.removeOrder(supplyOrder.getPhoneNumber());
        supplierOrderList.addOrder(supplyOrder);

        return new CommandResult(String.format(MESSAGE_MARK_ORDER_SUCCESS, targetIndex));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkSupplierOrderCommand
                && targetIndex == ((MarkSupplierOrderCommand) other).targetIndex);
    }
}
