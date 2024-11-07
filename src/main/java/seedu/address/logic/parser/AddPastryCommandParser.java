package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import seedu.address.logic.commands.AddPastryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;

/**
 * Parses input arguments and creates a new {@code AddPastryCommand} object.
 */
public class AddPastryCommandParser implements Parser<AddPastryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddPastryCommand}
     * and returns an {@code AddPastryCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddPastryCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Split the arguments into tokens
        String[] splitArgs = args.trim().split("\\s+");

        // Locate the cost, which should be the first valid double encountered
        double cost = -1;
        int costIndex = -1;
        for (int i = 0; i < splitArgs.length; i++) {
            try {
                cost = Double.parseDouble(splitArgs[i]);
                costIndex = i;
                break; // Found the cost, break out of the loop
            } catch (NumberFormatException e) {
                // Not a double, continue checking
            }
        }

        if (costIndex == -1) {
            throw new ParseException("The cost must be a valid number.");
        }

        if (cost <= 0) {
            throw new ParseException("The cost must be a positive number.");
        }

        // Extract the pastry name, which is everything before the cost
        String name = String.join(" ", List.of(splitArgs).subList(0, costIndex));

        // Extract the ingredient names, which are everything after the cost
        List<String> ingredientNames = List.of(splitArgs).subList(costIndex + 1, splitArgs.length);
        ArrayList<Ingredient> ingredients = parseIngredients(ingredientNames);

        return new AddPastryCommand(name, cost, ingredients);
    }


    /**
     * Parses a list of ingredient names into a list of {@code Ingredient} objects.
     * @throws ParseException if an ingredient name is invalid.
     */
    private ArrayList<Ingredient> parseIngredients(List<String> ingredientNames) throws ParseException {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        IngredientCatalogue catalogue = IngredientCatalogue.getInstance();

        for (String ingredientName : ingredientNames) {
            try {
                Ingredient ingredient = catalogue.getIngredientByName(ingredientName);
                ingredients.add(ingredient);
            } catch (NoSuchElementException e) {
                throw new ParseException("Invalid ingredient: " + ingredientName);
            }
        }

        return ingredients;
    }
}
