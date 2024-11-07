package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.SupplyOrderList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Supplier;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.Ingredients;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.Remark;
import seedu.address.storage.StorageManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddSupplierCommand}.
 */
public class AddSupplierCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs(),
                IngredientCatalogue.getInstance(), new PastryCatalogue(),
                new StorageManager(), new CustomerOrderList(), new SupplyOrderList());

        // Initialize sample ingredients list with ingredient IDs
        Ingredients ingredientsSupplied = new Ingredients(List.of(
                new Ingredient(1, "Flour", 1.50),
                new Ingredient(2, "Sugar", 0.80)
        ));

        // Add a sample Supplier to the model to ensure it's in the filtered list
        Supplier existingSupplier = new Supplier(
                new Name("Supplier ABC"),
                new Phone("91234567"),
                new Email("supplierabc@example.com"),
                new Address("123 Clementi Rd, #04-01"),
                ingredientsSupplied,
                new Remark("Reliable supplier"),
                new HashSet<>(List.of(new Tag("wholesale")))
        );
        model.addPerson(existingSupplier);
    }

    @Test
    public void execute_newSupplier_success() {
        // Prepare a new supplier with unique details to avoid duplication
        Ingredients ingredientsSupplied = new Ingredients(List.of(
                new Ingredient(4, "Chocolate", 1.00),
                new Ingredient(5, "Cheese", 3.00)
        ));

        Supplier validSupplier = new Supplier(
                new Name("Supplier XYZ"),
                new Phone("92345678"),
                new Email("supplierxyz@example.com"),
                new Address("789 Orchard Rd, #03-01"),
                ingredientsSupplied,
                new Remark("High quality products"),
                new HashSet<>(List.of(new Tag("organic")))
        );

        // Prepare the AddSupplierCommand
        AddSupplierCommand command = new AddSupplierCommand(validSupplier);

        // Expected model state after adding the supplier
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), IngredientCatalogue.getInstance(),
                new PastryCatalogue(), new StorageManager(), new CustomerOrderList(), new SupplyOrderList());
        expectedModel.addPerson(validSupplier);

        // Expected success message
        String expectedMessage = String.format(AddSupplierCommand.MESSAGE_SUCCESS, Messages.format(validSupplier));

        // Verify success of the command execution
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateSupplier_throwsCommandException() {
        // Use the first Supplier from the model as a duplicate
        Supplier duplicateSupplier = model.getFilteredPersonList().stream()
                .filter(person -> person instanceof Supplier)
                .map(person -> (Supplier) person)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Supplier not found in filtered list"));

        // Create an AddSupplierCommand with the duplicate supplier
        AddSupplierCommand command = new AddSupplierCommand(duplicateSupplier);

        // Verify that the command fails with the appropriate duplicate message
        assertCommandFailure(command, model, AddSupplierCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

    @Test
    public void equals() {
        // Create two different ingredients collections
        Ingredients ingredientsOne = new Ingredients(List.of(
                new Ingredient(1, "Flour", 1.50),
                new Ingredient(6, "Eggs", 2.50)
        ));
        Ingredients ingredientsTwo = new Ingredients(List.of(
                new Ingredient(3, "Butter", 2.00),
                new Ingredient(7, "Vanilla", 4.00)
        ));

        // Create two different supplier objects with different ingredients
        Supplier supplier1 = new Supplier(
                new Name("Supplier One"),
                new Phone("91234567"),
                new Email("supplierone@example.com"),
                new Address("123 Main St"),
                ingredientsOne,
                new Remark("Reliable supplier"),
                new HashSet<>(List.of(new Tag("frequent")))
        );

        Supplier supplier2 = new Supplier(
                new Name("Supplier Two"),
                new Phone("98765432"),
                new Email("suppliertwo@example.com"),
                new Address("456 Elm St"),
                ingredientsTwo,
                new Remark("Occasional supplier"),
                new HashSet<>(List.of(new Tag("seasonal")))
        );

        AddSupplierCommand addSupplier1Command = new AddSupplierCommand(supplier1);
        AddSupplierCommand addSupplier2Command = new AddSupplierCommand(supplier2);

        // Same object -> returns true
        assertEquals(addSupplier1Command, addSupplier1Command);

        // Same values -> returns true
        AddSupplierCommand addSupplier1CommandCopy = new AddSupplierCommand(supplier1);
        assertEquals(addSupplier1Command, addSupplier1CommandCopy);

        // Different supplier -> returns false
        assertNotEquals(addSupplier1Command, addSupplier2Command);

        // Null -> returns false
        assertNotEquals(addSupplier1Command, null);

        // Different type -> returns false
        assertNotEquals(addSupplier1Command, new ClearCommand());
    }
}