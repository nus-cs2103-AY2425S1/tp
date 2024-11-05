package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENTS_SUPPLIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Supplier;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.Ingredients;

/**
 * Adds a supplier to the address book.
 */
public class AddSupplierCommand extends Command {

    public static final String COMMAND_WORD = "addSupplier";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a supplier to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_INGREDIENTS_SUPPLIED + "INGREDIENTS SUPPLIED] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Supplier ABC "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "supplierabc@example.com "
            + PREFIX_ADDRESS + "123, Clementi Rd, #04-01 "
            + PREFIX_INGREDIENTS_SUPPLIED + "Flour, Sugar, Butter "
            + PREFIX_TAG + "wholesale";

    public static final String MESSAGE_SUCCESS = "New supplier added: %1$s";
    public static final String MESSAGE_DUPLICATE_SUPPLIER = "This supplier already exists in the address book";
    public static final String MESSAGE_INGREDIENT_NOT_FOUND = "Ingredient '%s' not found in the catalogue. Please add it using the addIngredient command.";

    private final Supplier toAdd;

    /**
     * Creates an AddSupplierCommand to add the specified {@code Supplier}
     */
    public AddSupplierCommand(Supplier supplier) {
        requireNonNull(supplier);
        toAdd = supplier;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Retrieve the IngredientCatalogue from the model
        IngredientCatalogue ingredientCatalogue = model.getIngredientCatalogue();

        // Validate and update ingredients with data from the catalogue
        List<Ingredient> validatedIngredients = new ArrayList<>();
        for (String ingredientName : toAdd.getIngredientsSupplied().getIngredientNames()) {
            try {
                // Retrieve the matching ingredient from the catalogue by name
                Ingredient catalogueIngredient = ingredientCatalogue.getIngredientByName(ingredientName);
                validatedIngredients.add(catalogueIngredient);
            } catch (NoSuchElementException e) {
                // Throw a CommandException if the ingredient is not found in the catalogue
                throw new CommandException(String.format(MESSAGE_INGREDIENT_NOT_FOUND, ingredientName));
            }
        }

// Create an updated Ingredients object with validated ingredients
        Ingredients updatedIngredients = new Ingredients(validatedIngredients);

// Update the supplier's ingredients list with the validated ingredients
        toAdd.setIngredientsSupplied(updatedIngredients);



        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SUPPLIER);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddSupplierCommand)) {
            return false;
        }

        AddSupplierCommand otherAddSupplierCommand = (AddSupplierCommand) other;
        return toAdd.equals(otherAddSupplierCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}

