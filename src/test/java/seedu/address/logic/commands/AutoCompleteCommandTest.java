package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalProducts.APPLE;
import static seedu.address.testutil.TypicalSuppliers.BENSON;
import static seedu.address.testutil.TypicalSuppliers.BOB;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.ProductBuilder;
import seedu.address.testutil.SupplierBuilder;

public class AutoCompleteCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void execute_nullInputType_returnsCommandList() throws CommandException {
        AutoCompleteCommand autoCompleteCommand = new AutoCompleteCommand("", null);
        CommandResult result = autoCompleteCommand.execute(model);

        List<String> expectedCompletions = CommandWords.getCommandWords();
        java.util.Collections.sort(expectedCompletions);
        if (expectedCompletions.size() > AutoCompleteCommand.MAX_SUGGESTIONS) {
            expectedCompletions = expectedCompletions.subList(0, AutoCompleteCommand.MAX_SUGGESTIONS);
        }

        assertEquals(String.join(AutoCompleteCommand.SUGGESTIONS_DELIMITER, expectedCompletions),
            result.getFeedbackToUser());
    }

    @Test
    public void execute_supplierNameInputType_returnsSupplierNames() throws CommandException {
        Supplier benson = new SupplierBuilder(BENSON).build();
        Supplier bob = new SupplierBuilder(BOB).build();
        model.addSupplier(benson);
        model.addSupplier(bob);

        AutoCompleteCommand autoCompleteCommand = new AutoCompleteCommand("B",
            CliSyntax.PREFIX_SUPPLIER_NAME.getPrefix());
        CommandResult result = autoCompleteCommand.execute(model);
        List<String> expectedCompletions = Arrays.asList(benson.getName().fullName, bob.getName().fullName);
        assertEquals(String.join(AutoCompleteCommand.SUGGESTIONS_DELIMITER, expectedCompletions),
            result.getFeedbackToUser());
    }

    @Test
    public void execute_productNameInputType_returnsProductNames() throws CommandException {
        Product apple = new ProductBuilder(APPLE).build();
        model.addProduct(apple);

        AutoCompleteCommand autoCompleteCommand = new AutoCompleteCommand("",
            CliSyntax.PREFIX_PRODUCT_NAME.getPrefix());
        CommandResult result = autoCompleteCommand.execute(model);
        List<String> expectedCompletions = Arrays.asList(apple.getName().fullName);
        assertEquals(String.join(AutoCompleteCommand.SUGGESTIONS_DELIMITER, expectedCompletions),
            result.getFeedbackToUser());
    }

    @Test
    public void execute_noMatchingCompletions_returnsEmptyString() throws CommandException {
        AutoCompleteCommand autoCompleteCommand = new AutoCompleteCommand("nonexistent", null);
        CommandResult result = autoCompleteCommand.execute(model);
        assertEquals("", result.getFeedbackToUser());
    }

    @Test
    public void execute_singleMatchingCompletion_returnsCompletion() throws CommandException {
        AutoCompleteCommand autoCompleteCommand = new AutoCompleteCommand("add_pro", null);
        CommandResult result = autoCompleteCommand.execute(model);
        assertEquals("duct", result.getFeedbackToUser());
    }

    @Test
    public void execute_multipleMatchingCompletions_returnsCompletions() throws CommandException {
        AutoCompleteCommand autoCompleteCommand = new AutoCompleteCommand("a", null);
        CommandResult result = autoCompleteCommand.execute(model);
        List<String> expectedCompletions = Arrays.asList(
            AddProductCommand.COMMAND_WORD,
            AddSupplierCommand.COMMAND_WORD,
            AssignProductCommand.COMMAND_WORD
        );
        assertEquals(String.join(AutoCompleteCommand.SUGGESTIONS_DELIMITER, expectedCompletions),
            result.getFeedbackToUser());
    }

    @Test
    public void getCurrentInput_validInput_returnsCurrentInput() {
        String currentInput = "add";
        Prefix inputType = new Prefix("n/");
        AutoCompleteCommand autoCompleteCommand = new AutoCompleteCommand(currentInput, inputType.getPrefix());

        assertEquals(currentInput, autoCompleteCommand.getCurrentInput());
    }

    @Test
    public void getInputType_validInput_returnsInputType() {
        String currentInput = "add";
        Prefix inputType = new Prefix("n/");
        AutoCompleteCommand autoCompleteCommand = new AutoCompleteCommand(currentInput, inputType.getPrefix());

        assertEquals(inputType, autoCompleteCommand.getInputType());
    }

    @Test
    public void equals() {
        AutoCompleteCommand autoCompleteCommand1 = new AutoCompleteCommand("A",
            CliSyntax.PREFIX_SUPPLIER_NAME.getPrefix());
        AutoCompleteCommand autoCompleteCommand2 = new AutoCompleteCommand("A",
            CliSyntax.PREFIX_SUPPLIER_NAME.getPrefix());
        AutoCompleteCommand autoCompleteCommand3 = new AutoCompleteCommand("B",
            CliSyntax.PREFIX_SUPPLIER_NAME.getPrefix());
        AutoCompleteCommand autoCompleteCommand4 = new AutoCompleteCommand("A",
            CliSyntax.PREFIX_PRODUCT_NAME.getPrefix());

        // Reflexivity
        assertTrue(autoCompleteCommand1.equals(autoCompleteCommand1));

        // Symmetry
        assertTrue(autoCompleteCommand1.equals(autoCompleteCommand2));
        assertTrue(autoCompleteCommand2.equals(autoCompleteCommand1));

        // Transitivity
        assertTrue(autoCompleteCommand1.equals(autoCompleteCommand2));
        assertTrue(autoCompleteCommand2.equals(autoCompleteCommand1));
        assertTrue(autoCompleteCommand1.equals(autoCompleteCommand1));

        // Consistency
        assertTrue(autoCompleteCommand1.equals(autoCompleteCommand2));
        assertTrue(autoCompleteCommand1.equals(autoCompleteCommand2));

        // Non-nullity
        assertFalse(autoCompleteCommand1.equals(null));

        // Different Types
        assertFalse(autoCompleteCommand1.equals(new Object()));

        // Different Values
        assertFalse(autoCompleteCommand1.equals(autoCompleteCommand3));
        assertFalse(autoCompleteCommand1.equals(autoCompleteCommand4));
    }
}
