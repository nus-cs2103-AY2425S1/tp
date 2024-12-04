package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Supplier;

/**
 * Unassigns an existing product to an existing supplier in the address book.
 */
public class UnassignProductCommand extends Command {
    public static final String COMMAND_WORD = CommandWords.UNASSIGN_PRODUCT_COMMAND;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns a product to a supplier. "
            + "Parameters: "
            + PREFIX_PRODUCT_NAME + "PRODUCT_NAME "
            + PREFIX_SUPPLIER_NAME + "SUPPLIER_NAME "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_PRODUCT_NAME + "apple pie "
            + PREFIX_SUPPLIER_NAME + "Amy Bee";

    public static final String MESSAGE_SUCCESS = "Unassigned Product: %1$s to Supplier: %2$s";
    public static final String MESSAGE_PRODUCT_NOT_FOUND = "Product not found: %1$s";
    public static final String MESSAGE_PRODUCT_NOT_ASSIGNED = "Product was not assigned to the supplier initially.";
    private final ProductName productName;

    /**
     * @param productName of the product in the filtered product list to unassign.
     */
    public UnassignProductCommand(ProductName productName) {
        this.productName = productName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Objects.requireNonNull(model);

        Product productToUnassign = findProductByName(model, productName);
        Supplier supplierToUnassign = findSupplierWithProduct(model, productToUnassign);
        removeProductFromSupplier(model, supplierToUnassign, productToUnassign);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        model.updateFilteredSupplierList(Model.PREDICATE_SHOW_ALL_SUPPLIERS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, productToUnassign.getName(),
                supplierToUnassign.getName()));
    }

    // Helper methods
    private Product findProductByName(Model model, ProductName productName) throws CommandException {
        Product product = model.findProductByName(productName);
        if (product == null) {
            throw new CommandException(String.format(MESSAGE_PRODUCT_NOT_FOUND, productName));
        }
        return product;
    }

    private Supplier findSupplierWithProduct(Model model, Product product) throws CommandException {
        Name supplierName = product.getSupplierName();
        // Assertions to ensure consistency between supplier name and assignment status
        if (supplierName == null) {
            assert !model.isProductAssignedToAnySupplier(product)
                    : "Product with null supplier name should not be assigned to any supplier";
        } else {
            assert model.isProductAssignedToAnySupplier(product)
                    : "Product with non-null supplier name should be assigned to a supplier";
        }
        // Check if the product is assigned to any supplier
        if (supplierName == null || !model.isProductAssignedToAnySupplier(product)) {
            throw new CommandException(MESSAGE_PRODUCT_NOT_ASSIGNED);
        }
        return model.findSupplier(supplierName);
    }
    private void removeProductFromSupplier(Model model, Supplier supplier, Product product) {
        Set<Product> updatedProductList = new HashSet<>(supplier.getProducts());
        updatedProductList.remove(product);
        Product updatedProduct = new Product(product.getName(), product.getStockLevel(), product.getTags());
        Supplier updatedSupplier = new Supplier(
                supplier.getName(), supplier.getPhone(), supplier.getEmail(),
                supplier.getAddress(), supplier.getTags(), updatedProductList);

        model.setProduct(product, updatedProduct);
        model.setSupplier(supplier, updatedSupplier);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof UnassignProductCommand) {
            UnassignProductCommand otherCommand = (UnassignProductCommand) other;
            return this.productName.equals(otherCommand.productName);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName);
    }
}
