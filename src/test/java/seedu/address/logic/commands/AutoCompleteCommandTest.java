package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalProducts.APPLE;
import static seedu.address.testutil.TypicalSuppliers.ALICE;
import static seedu.address.testutil.TypicalSuppliers.BENSON;
import static seedu.address.testutil.TypicalSuppliers.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
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

        List<String> expectedCompletions = new ArrayList<>(Arrays.asList(
            AddProductCommand.COMMAND_WORD,
            AddSupplierCommand.COMMAND_WORD,
            AssignProductCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            DeleteProductCommand.COMMAND_WORD,
            DeleteSupplierCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            ViewAllSupplierCommand.COMMAND_WORD,
            SetThresholdCommand.COMMAND_WORD,
            UnassignProductCommand.COMMAND_WORD,
            ViewProductCommand.COMMAND_WORD,
            ViewSupplierCommand.COMMAND_WORD
        ));
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
    public void execute_tagInputType_returnsTags() throws CommandException {
        Product apple = new ProductBuilder(APPLE).withTags("fruit").build();
        Supplier alice = new SupplierBuilder(ALICE).withTags("supplier").build();
        model.addProduct(apple);
        model.addSupplier(alice);

        AutoCompleteCommand autoCompleteCommand = new AutoCompleteCommand("", CliSyntax.PREFIX_TAG.getPrefix());
        CommandResult result = autoCompleteCommand.execute(model);
        List<String> expectedCompletions = Arrays.asList("fruit", "supplier");
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
}
