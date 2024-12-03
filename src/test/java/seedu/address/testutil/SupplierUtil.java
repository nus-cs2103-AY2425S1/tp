package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddSupplierCommand;
import seedu.address.model.supplier.Supplier;

/**
 * A utility class for Supplier.
 */
public class SupplierUtil {

    /**
     * Returns an add command string for adding the {@code supplier}.
     */
    public static String getAddCommand(Supplier supplier) {
        return AddSupplierCommand.COMMAND_WORD + " " + PREFIX_SUPPLIER + " " + getSupplierDetails(supplier);
    }

    /**
     * Returns the part of command string for the given {@code supplier}'s details.
     */
    public static String getSupplierDetails(Supplier supplier) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + supplier.getName().toString() + " ");
        sb.append(PREFIX_PHONE + supplier.getPhone().getPhone() + " ");
        sb.append(PREFIX_EMAIL + supplier.getEmail().getEmail() + " ");
        sb.append(PREFIX_COMPANY + supplier.getCompany().getCompany() + " ");
        supplier.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.getTagName() + " ")
        );
        supplier.getProducts().stream().forEach(
                s -> sb.append(PREFIX_PRODUCT + s.getProductName() + " ")
        );
        return sb.toString();
    }
}
