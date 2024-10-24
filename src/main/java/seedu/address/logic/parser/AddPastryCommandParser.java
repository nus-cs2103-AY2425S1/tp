    package seedu.address.logic.parser;

    import static java.util.Objects.requireNonNull;
    import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.NoSuchElementException;
    import java.util.stream.Collectors;
    import java.util.stream.Stream;

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

            // Extract name, cost, and ingredient parts
            String[] splitArgs = args.trim().split("\\s+");

            if (splitArgs.length < 3) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPastryCommand.MESSAGE_USAGE));
            }

            // First argument is the pastry name
            String name = splitArgs[0];

            // Second argument is the cost; must be a valid double
            double cost;
            try {
                cost = Double.parseDouble(splitArgs[1]);
            } catch (NumberFormatException e) {
                throw new ParseException("The cost must be a valid number.");
            }

            // Remaining arguments are the ingredient names
            List<String> ingredientNames = Stream.of(splitArgs).skip(2).collect(Collectors.toList());
            ArrayList<Ingredient> ingredients = parseIngredients(ingredientNames);

            return new AddPastryCommand(name, cost, ingredients);
        }

        /**
         * Parses a list of ingredient names into a list of {@code Ingredient} objects.
         * @throws ParseException if an ingredient name is invalid.
         */
        private ArrayList<Ingredient> parseIngredients(List<String> ingredientNames) throws ParseException {
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            IngredientCatalogue catalogue = new IngredientCatalogue();

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
