package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

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
 * Assigns an existing product to an existing supplier in the address book.
 */
public class AssignProductCommand extends Command {
    public static final String COMMAND_WORD = CommandWords.ASSIGN_PRODUCT_COMMAND;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a product to a supplier. "
            + "Parameters: "
            + PREFIX_PRODUCT_NAME + "PRODUCT_NAME "
            + PREFIX_SUPPLIER_NAME + "SUPPLIER_NAME "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_PRODUCT_NAME + "apple pie "
            + PREFIX_SUPPLIER_NAME + "Amy Bee";

    public static final String MESSAGE_SUCCESS = "Assigned Product: %1$s to Supplier: %2$s";
    public static final String MESSAGE_SUPPLIER_NOT_FOUND = "Supplier not found: %1$s";
    public static final String MESSAGE_PRODUCT_NOT_FOUND = "Product not found: %1$s";
    public static final String MESSAGE_PRODUCT_ALREADY_ASSIGNED = "Product is already assigned to the supplier.";
    public static final String MESSAGE_PRODUCT_ALREADY_ASSIGNED_TO_OTHER =
            "Product is already assigned to a different supplier: %1$s.";
    private final ProductName productName;
    private final Name supplierName;

    /**
     * @param productName of the product in the filtered product list to assign
     * @param supplierName of the supplier in the filtered supplier list to assign
     */
    public AssignProductCommand(ProductName productName, Name supplierName) {
        this.productName = productName;
        this.supplierName = supplierName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Product productToAssign = findProductByName(model, productName);
        Supplier supplierToAssign = findSupplierByName(model, supplierName);
        checkProductAssignmentStatus(model, productToAssign, supplierToAssign);
        assignProductToSupplier(model, supplierToAssign, productToAssign);
        model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, productToAssign.getName(), supplierToAssign.getName()));
    }
    private Product findProductByName(Model model, ProductName productName) throws CommandException {
        Product product = model.findProductByName(productName);
        if (product == null) {
            throw new CommandException(String.format(MESSAGE_PRODUCT_NOT_FOUND, productName));
        }
        return product;
    }

    private Supplier findSupplierByName(Model model, Name supplierName) throws CommandException {
        Supplier supplier = model.findSupplier(supplierName);
        if (supplier == null) {
            throw new CommandException(String.format(MESSAGE_SUPPLIER_NOT_FOUND, supplierName));
        }
        return supplier;
    }

    private void checkProductAssignmentStatus(Model model, Product product, Supplier supplier) throws CommandException {
        Name supplierName = product.getSupplierName();
        if (supplierName == null) {
            assert !model.isProductAssignedToAnySupplier(product)
                    : "Product with null supplier name should not be assigned to any supplier";
        } else {
            assert model.isProductAssignedToAnySupplier(product)
                    : "Product with non-null supplier name should be assigned to a supplier";
        }
        if (product.getSupplierName() != null && !product.getSupplierName().equals(supplier.getName())) {
            throw new CommandException(String.format(MESSAGE_PRODUCT_ALREADY_ASSIGNED_TO_OTHER,
                    product.getSupplierName()));
        }
        if (supplier.getProducts().contains(product)) {
            throw new CommandException(MESSAGE_PRODUCT_ALREADY_ASSIGNED);
        }
    }

    private void assignProductToSupplier(Model model, Supplier supplier, Product product) {
        Set<Product> updatedProductList = new HashSet<>(supplier.getProducts());
        updatedProductList.add(product);

        Product updatedProduct = new Product(product.getName(), product.getStockLevel(), product.getTags());
        updatedProduct.setSupplierName(supplier.getName());

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
        }
        if (other instanceof AssignProductCommand) {
            AssignProductCommand otherCommand = (AssignProductCommand) other;
            return this.productName.equals(otherCommand.productName)
                    && this.supplierName.equals(otherCommand.supplierName);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, supplierName);
    }
}
