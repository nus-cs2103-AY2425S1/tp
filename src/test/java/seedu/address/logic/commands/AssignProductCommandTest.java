package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_APPLE_PIE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.ProductBuilder;
import seedu.address.testutil.SupplierBuilder;

public class AssignProductCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_assignProductToSupplier_success() throws Exception {
        Supplier supplier = new SupplierBuilder().withName(VALID_NAME_AMY).build();
        Product product = new ProductBuilder().withName(VALID_PRODUCT_APPLE_PIE).build();
        model.addSupplier(supplier);
        model.addProduct(product);

        AssignProductCommand assignProductCommand = new AssignProductCommand(
                new ProductName(VALID_PRODUCT_APPLE_PIE),
                new Name(VALID_NAME_AMY));

        String expectedMessage = String.format(AssignProductCommand.MESSAGE_SUCCESS, VALID_PRODUCT_APPLE_PIE,
                VALID_NAME_AMY);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setSupplier(supplier, new SupplierBuilder(supplier).withProducts(Set.of(product)).build());

        assertCommandSuccess(assignProductCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_supplierNotFound_throwsCommandException() {
        Product product = new ProductBuilder().withName(VALID_PRODUCT_APPLE_PIE).build();
        model.addProduct(product);

        AssignProductCommand assignProductCommand = new AssignProductCommand(
                new ProductName(VALID_PRODUCT_APPLE_PIE),
                new Name("Nonexistent Supplier"));

        assertCommandFailure(assignProductCommand, model, String.format(AssignProductCommand.MESSAGE_SUPPLIER_NOT_FOUND,
                "Nonexistent Supplier"));
    }

    @Test
    public void execute_productNotFound_throwsCommandException() {
        Supplier supplier = new SupplierBuilder().withName(VALID_NAME_AMY).build();
        model.addSupplier(supplier);

        AssignProductCommand assignProductCommand = new AssignProductCommand(
                new ProductName("Nonexistent Product"),
                new Name(VALID_NAME_AMY));

        assertCommandFailure(assignProductCommand, model, String.format(AssignProductCommand.MESSAGE_PRODUCT_NOT_FOUND,
                "Nonexistent Product"));
    }

    @Test
    public void execute_productAlreadyAssigned_throwsCommandException() {
        Product product = new ProductBuilder()
                .withName(VALID_PRODUCT_APPLE_PIE)
                .withSupplierName(VALID_NAME_AMY)
                .build();
        model.addProduct(product);

        Supplier supplier = new SupplierBuilder()
                .withName(VALID_NAME_AMY)
                .withProducts(Set.of(product))
                .build();
        model.addSupplier(supplier);

        AssignProductCommand assignProductCommand = new AssignProductCommand(
                new ProductName(VALID_PRODUCT_APPLE_PIE),
                new Name(VALID_NAME_AMY));

        assertCommandFailure(assignProductCommand, model, AssignProductCommand.MESSAGE_PRODUCT_ALREADY_ASSIGNED);
    }

    @Test
    public void execute_productAssignedToDifferentSupplier_throwsCommandException() {

        Supplier supplierAmy = new SupplierBuilder().withName(VALID_NAME_AMY).build();
        Supplier supplierBob = new SupplierBuilder().withName(VALID_NAME_BOB).build();
        Product product = new ProductBuilder().withName(VALID_PRODUCT_APPLE_PIE)
                .withSupplierName(VALID_NAME_AMY)
                .build();

        model.addSupplier(supplierAmy);
        model.addSupplier(supplierBob);
        model.addProduct(product);

        Supplier updatedSupplierAmy = new SupplierBuilder(supplierAmy)
                .withProducts(Set.of(product)).build();
        model.setSupplier(supplierAmy, updatedSupplierAmy);

        AssignProductCommand assignProductCommand = new AssignProductCommand(
                new ProductName(VALID_PRODUCT_APPLE_PIE),
                new Name(VALID_NAME_BOB));

        String expectedMessage = String.format(AssignProductCommand.MESSAGE_PRODUCT_ALREADY_ASSIGNED_TO_OTHER,
                VALID_NAME_AMY);

        assertCommandFailure(assignProductCommand, model, expectedMessage);
    }
}
