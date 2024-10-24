package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddVendorCommand;
import seedu.address.logic.commands.util.EditVendorDescriptor;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Vendor.
 */
public class VendorUtil {

    /**
     * Returns an add_vendor command string for adding the {@code vendor}.
     */
    public static String getAddVendorCommand(Vendor vendor) {
        return AddVendorCommand.COMMAND_WORD + " " + getVendorDetails(vendor);
    }

    /**
     * Returns the part of command string for the given {@code vendor}'s details.
     */
    public static String getVendorDetails(Vendor vendor) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + vendor.getName().fullName + " ");
        sb.append(PREFIX_PHONE + vendor.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + vendor.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + vendor.getAddress().value + " ");
        sb.append(PREFIX_COMPANY + vendor.getCompany().value + " ");
        sb.append(PREFIX_BUDGET + vendor.getBudget().value + " ");
        vendor.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditVendorDescriptorDetails(EditVendorDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getCompany().ifPresent(company -> sb.append(PREFIX_COMPANY).append(company.value).append(" "));
        descriptor.getBudget().ifPresent(budget -> sb.append(PREFIX_BUDGET).append(budget.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}

