package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

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
        List<Supplier> lastShownSupplierList = model.getFilteredSupplierList();
        List<Product> lastShownProductList = model.getFilteredProductList();

        // Check if supplier exists in supplier list
        Supplier supplierToAssign = lastShownSupplierList.stream()
                .filter(supplier -> supplier.getName().equals(supplierName))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_SUPPLIER_NOT_FOUND, supplierName)));

        // Check if product exists in product list
        Product productToAssign = lastShownProductList.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PRODUCT_NOT_FOUND, productName)));

        // Create a new supplier with the updated product list
        Set<Product> updatedProductList = new HashSet<>(supplierToAssign.getProducts());
        if (updatedProductList.contains(productToAssign)) {
            throw new CommandException(MESSAGE_PRODUCT_ALREADY_ASSIGNED);
        } else {
            updatedProductList.add(productToAssign);
        }
        Supplier updatedSupplier = new Supplier(
                supplierToAssign.getName(),
                supplierToAssign.getPhone(),
                supplierToAssign.getEmail(),
                supplierToAssign.getAddress(),
                supplierToAssign.getTags(),
                updatedProductList
        );

        productToAssign.setSupplierName(supplierName);

        model.setSupplier(supplierToAssign, updatedSupplier);
        model.updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, productToAssign.getName(), supplierToAssign.getName()));
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
        // Use Objects.hash to generate a hash code based on the fields
        return Objects.hash(productName, supplierName);
    }
}
