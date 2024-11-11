package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_APPLE_PIE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.ProductBuilder;
import seedu.address.testutil.SupplierBuilder;

public class UnassignProductCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    //Product not found
    @Test
    public void execute_productNotFound_throwsCommandException() {
        Supplier supplier = new SupplierBuilder().withName(VALID_NAME_AMY).build();
        model.addSupplier(supplier);

        UnassignProductCommand unassignProductCommand = new UnassignProductCommand(
                new ProductName("Nonexistent Product"));

        assertCommandFailure(unassignProductCommand, model, String.format(UnassignProductCommand
                        .MESSAGE_PRODUCT_NOT_FOUND,
                "Nonexistent Product"));
    }

    //Product is found, but not assigned
    @Test
    public void execute_productNotAssigned_throwsCommandException() {
        Supplier supplier = new SupplierBuilder().withName(VALID_NAME_AMY).build();
        model.addSupplier(supplier);

        Product product = new ProductBuilder().withName(VALID_PRODUCT_APPLE_PIE).build();
        model.addProduct(product);

        UnassignProductCommand unassignProductCommand = new UnassignProductCommand(
                new ProductName(VALID_PRODUCT_APPLE_PIE));

        assertCommandFailure(unassignProductCommand, model, UnassignProductCommand.MESSAGE_PRODUCT_NOT_ASSIGNED);
    }

    //Product is assigned and found successfully
    @Test
    public void execute_productAssignedAndFound_success() {
        Product product = new ProductBuilder().withName(VALID_PRODUCT_APPLE_PIE).withSupplierName(VALID_NAME_AMY)
                .build();
        Supplier supplier = new SupplierBuilder().withName(VALID_NAME_AMY).withProducts(Set.of(product)).build();
        model.addSupplier(supplier);
        model.addProduct(product);
        UnassignProductCommand unassignProductCommand = new UnassignProductCommand(new
                ProductName(VALID_PRODUCT_APPLE_PIE));
        String expectedMessage = String.format(UnassignProductCommand.MESSAGE_SUCCESS,
                VALID_PRODUCT_APPLE_PIE, VALID_NAME_AMY);
        Product updatedProduct = new ProductBuilder().withName(VALID_PRODUCT_APPLE_PIE).build();
        Supplier updatedSupplier = new SupplierBuilder().withName(VALID_NAME_AMY).withProducts(
                Collections.emptySet()).build();
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setProduct(product, updatedProduct);
        expectedModel.setSupplier(supplier, updatedSupplier);
        assertCommandSuccess(unassignProductCommand, model, expectedMessage, expectedModel);
    }
}
