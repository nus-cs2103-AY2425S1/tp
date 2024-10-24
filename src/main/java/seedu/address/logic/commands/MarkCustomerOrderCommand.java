package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.Inventory;
import seedu.address.model.product.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkCustomerOrderCommand extends Command {
    public static final String COMMAND_WORD = "markCustomerOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the customer order at the given index as completed and updates the inventory.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_ORDER_SUCCESS = "Marked customer order as completed: %1$s";
    public static final String MESSAGE_ORDER_ALREADY_COMPLETED = "The order at index %1$s is already completed.";
    public static final String MESSAGE_INSUFFICIENT_STOCK = "Not enough stock to fulfill the order.";
    public static final String MESSAGE_INVALID_INDEX = "The index provided is invalid.";

    private final int targetIndex;

    public MarkCustomerOrderCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        CustomerOrderList customerOrderList = model.getCustomerOrderList();
        Inventory inventory = model.getInventory();  // Get inventory from the model

        if (targetIndex <= 0 || targetIndex > customerOrderList.getOrders().size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        CustomerOrder customerOrder = customerOrderList.getOrders().get(targetIndex - 1);

        // Check if the order is already completed
        if (customerOrder.getStatus() == OrderStatus.COMPLETED) {
            return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_COMPLETED, targetIndex));
        }

        List<? extends Product> items = customerOrder.getItems();

        // Step 1: Accumulate required ingredient quantities
        Map<Integer, Integer> requiredIngredients = new HashMap<>();

        for (Product product : items) {
            if (product instanceof Pastry pastry) {
                for (Ingredient ingredient : pastry.getIngredients()) {
                    int ingredientId = ingredient.getProductId();
                    requiredIngredients.put(ingredientId, requiredIngredients.getOrDefault(ingredientId, 0) + 1);
                }
            }
        }

//        // Step 2: Check if inventory has enough of each ingredient
//        for (Map.Entry<Integer, Integer> entry : requiredIngredients.entrySet()) {
//            int ingredientId = entry.getKey();
//            int requiredQuantity = entry.getValue();
//
//            if (inventory.getStockLevel(ingredientId) < requiredQuantity) {
//                throw new CommandException(MESSAGE_INSUFFICIENT_STOCK);
//            }
//        }

//        // Step 3: If enough stock, update inventory by removing required quantities
//        for (Map.Entry<Integer, Integer> entry : requiredIngredients.entrySet()) {
//            int ingredientId = entry.getKey();
//            int quantityToRemove = entry.getValue();
//            inventory.removeStock(ingredientId, quantityToRemove);
//        }

        // Mark the order as completed
        customerOrder.setStatus(OrderStatus.COMPLETED);

        customerOrderList.removeOrder(customerOrder.getPhoneNumber());
        customerOrderList.addOrder(customerOrder);
        return new CommandResult(String.format(MESSAGE_MARK_ORDER_SUCCESS, targetIndex));
    }


    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MarkCustomerOrderCommand
                && targetIndex == ((MarkCustomerOrderCommand) other).targetIndex);
    }
}