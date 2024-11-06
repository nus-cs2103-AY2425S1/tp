package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Map;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.product.Product;

/**
 * Adds a new pastry to the bakery's pastry catalogue.
 */
public class AddPastryCommand extends Command {
    public static final String COMMAND_WORD = "addPastry";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new pastry to the bakery's catalogue. "
            + "Parameters: NAME COST INGREDIENTS...\n"
            + "Example: " + COMMAND_WORD + " Croissant 3.50 Flour Butter Sugar";

    public static final String MESSAGE_ADD_PASTRY_SUCCESS = "New pastry added: %1$s";
    public static final String MESSAGE_DUPLICATE_PASTRY = "This pastry already exists in the catalogue.";

    private final String name;
    private final double cost;
    private final ArrayList<Ingredient> ingredients;

    /**
     * Creates an AddPastryCommand to add the specified {@code Pastry}.
     */
    public AddPastryCommand(String name, double cost, ArrayList<Ingredient> ingredients) {
        requireAllNonNull(name, ingredients);
        this.name = name;
        this.cost = cost;
        this.ingredients = ingredients;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Find the next available id from the pastry catalogue.
        PastryCatalogue pastryCatalogue = model.getPastryCatalogue();
        int nextProductId = pastryCatalogue.getNextProductId();

        // Create a new pastry with the next available product ID.
        Pastry newPastry = new Pastry(nextProductId, name, cost, ingredients);

        // Check if the pastry already exists in the catalogue.
        if (isDuplicatePastry(pastryCatalogue.getCatalogue(), newPastry)) {
            throw new CommandException(MESSAGE_DUPLICATE_PASTRY);
        }

        // Add the new pastry to the catalogue.
        pastryCatalogue.addPastry(name, cost, ingredients);

        // Return a success message.
        return new CommandResult(String.format(MESSAGE_ADD_PASTRY_SUCCESS, newPastry));
    }

    /**
     * Checks if a pastry with the same name, cost, and ingredients already exists in the catalogue.
     */
    private boolean isDuplicatePastry(Map<Integer, Product> catalogue, Pastry newPastry) {
        return catalogue.values().stream()
                .filter(product -> product instanceof Pastry)
                .map(product -> (Pastry) product)
                .anyMatch(existingPastry ->
                        existingPastry.getName().equalsIgnoreCase(newPastry.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddPastryCommand)) {
            return false;
        }

        AddPastryCommand otherCommand = (AddPastryCommand) other;
        return name.equals(otherCommand.name)
                && cost == otherCommand.cost
                && ingredients.equals(otherCommand.ingredients);
    }
}
