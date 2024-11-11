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
import seedu.address.model.person.Person;
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
    public static final String MESSAGE_DUPLICATE_PHONE = "A contact in the address book already has this phone number. "
            + "Please use a different phone number.";
    public static final String MESSAGE_DUPLICATE_EMAIL = "A contact in the address book already has this email address. "
            + "Please use a different email address.";
    public static final String MESSAGE_DUPLICATE_ADDRESS = "A contact in the address book already has this home address. "
            + "Please use a different home address.";
    public static final String MESSAGE_INGREDIENT_NOT_FOUND = "Ingredient '%s' not found in the catalogue. "
            + "Please add it using the addIngredient command.";

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

        // Check for duplicate phone number across all contacts
        for (Person person : model.getFilteredPersonList()) {
            if (person.getPhone().equals(toAdd.getPhone())) {
                throw new CommandException(MESSAGE_DUPLICATE_PHONE);
            }
            if (person.getEmail().equals(toAdd.getEmail())) {
                throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
            }
            if (person.getAddress().equals(toAdd.getAddress())) {
                throw new CommandException(MESSAGE_DUPLICATE_ADDRESS);
            }
        }

        // Add the new supplier if no duplicates are found
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

