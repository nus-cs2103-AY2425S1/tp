package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import java.util.HashSet;
import java.util.List;
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

    public static final String MESSAGE_SUCCESS = "Successfully unassigned product %s.";
    public static final String MESSAGE_SUPPLIER_NOT_FOUND = "Supplier not found: %1$s";
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

        List<Supplier> lastShownSupplierList = model.getModifiedSupplierList();
        List<Product> lastShownProductList = model.getModifiedProductList();

        Product productToUnassign = lastShownProductList.stream()
                .filter(product -> product.getName().equals(this.productName))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PRODUCT_NOT_FOUND, this.productName)));

        // Check if product is assigned to any supplier
        boolean isAssignedToAnySupplier = lastShownSupplierList.stream()
                .anyMatch(supplier -> supplier.getProducts().contains(productToUnassign));

        if (!isAssignedToAnySupplier) {
            throw new CommandException(MESSAGE_PRODUCT_NOT_ASSIGNED);
        }

        // Locate the specific supplier that has this product assigned
        Name supplierName = productToUnassign.getSupplierName();
        Supplier supplierToUnassign = lastShownSupplierList.stream()
                .filter(supplier -> supplier.getName().equals(supplierName))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_SUPPLIER_NOT_FOUND, supplierName)));

        Set<Product> updatedProductList = new HashSet<>(supplierToUnassign.getProducts());
        //Remove product from product list of supplier
        updatedProductList.remove(productToUnassign);
        Product updatedProduct = new Product(
                productToUnassign.getName(),
                productToUnassign.getStockLevel(),
                productToUnassign.getTags()
        );
        Supplier updatedSupplier = new Supplier(supplierToUnassign.getName(), supplierToUnassign.getPhone(),
                supplierToUnassign.getEmail(), supplierToUnassign.getAddress(),
                supplierToUnassign.getTags(), updatedProductList);

        model.setProduct(productToUnassign, updatedProduct);
        model.setSupplier(supplierToUnassign, updatedSupplier);
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        model.updateFilteredSupplierList(Model.PREDICATE_SHOW_ALL_SUPPLIERS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, productToUnassign.getName(),
                supplierToUnassign.getName()));
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
