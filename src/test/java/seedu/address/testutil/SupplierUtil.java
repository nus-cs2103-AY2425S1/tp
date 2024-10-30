package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditSupplierDescriptor;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Supplier.
 */
public class SupplierUtil {

    /**
     * Returns an add command string for adding the {@code supplier}.
     */
    public static String getAddCommand(Supplier supplier) {
        return AddCommand.COMMAND_WORD + " " + PREFIX_SUPPLIER + " " + getSupplierDetails(supplier);
    }

    /**
     * Returns the part of command string for the given {@code supplier}'s details.
     */
    public static String getSupplierDetails(Supplier supplier) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + supplier.getName().fullName + " ");
        sb.append(PREFIX_PHONE + supplier.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + supplier.getEmail().value + " ");
        sb.append(PREFIX_COMPANY + supplier.getCompany().value + " ");
        supplier.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        supplier.getProducts().stream().forEach(
                s -> sb.append(PREFIX_PRODUCT + s.productName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditSupplierDescriptor}'s details.
     */
    public static String getEditSupplierDescriptorDetails(EditSupplierDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getCompany().ifPresent(company -> sb.append(PREFIX_COMPANY).append(company.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG + " ");
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        if (descriptor.getProducts().isPresent()) {
            Set<Product> products = descriptor.getProducts().get();
            if (products.isEmpty()) {
                sb.append(PREFIX_PRODUCT + " ");
            } else {
                products.forEach(s -> sb.append(PREFIX_PRODUCT).append(s.productName).append(" "));
            }
        }
        return sb.toString();
    }
}
