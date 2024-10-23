package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Inventory;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.PastryCatalogue;

/**
 * Checks if there is sufficient stock in the inventory to make a specific pastry.
 */
public class CheckPastryStockCommand extends Command {

    public static final String COMMAND_WORD = "checkPastryStock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks if there is enough stock to make a specific pastry. "
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " Croissant";

    public static final String MESSAGE_PASTRY_AVAILABLE = "Sufficient stock available to make: %1$s";
    public static final String MESSAGE_PASTRY_UNAVAILABLE = "Insufficient stock for making: %1$s";

    private final String pastryName;

    /**
     * Creates a CheckPastryStockCommand to check the stock for the given {@code Pastry}.
     */
    public CheckPastryStockCommand(String pastryName) {
        requireNonNull(pastryName);
        this.pastryName = pastryName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Retrieve the pastry by name from the catalogue
        PastryCatalogue pastryCatalogue = model.getPastryCatalogue();
        Pastry pastry = pastryCatalogue.getPastryByName(pastryName);

        if (pastry == null) {
            throw new CommandException("Pastry not found in the catalogue: " + pastryName);
        }

        // Check if sufficient stock exists using the inventory
        Inventory inventory = model.getInventory();
        if (!inventory.canMakePastry(pastry)) {
            throw new CommandException(String.format(MESSAGE_PASTRY_UNAVAILABLE, pastryName));
        }

        // If stock is sufficient, return success
        return new CommandResult(String.format(MESSAGE_PASTRY_AVAILABLE, pastryName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CheckPastryStockCommand)) {
            return false;
        }

        CheckPastryStockCommand otherCommand = (CheckPastryStockCommand) other;
        return pastryName.equals(otherCommand.pastryName);
    }
}