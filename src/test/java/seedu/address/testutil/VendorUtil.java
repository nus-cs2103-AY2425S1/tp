package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;

import java.util.Set;

import seedu.address.logic.commands.CreateVendorCommand;
import seedu.address.logic.commands.EditVendorCommand.EditVendorDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.vendor.Vendor;

/**
 * A utility class for Vendor.
 */
public class VendorUtil {

    /**
     * Returns an add command string for adding the {@code vendor}.
     */
    public static String getCreateVendorCommand(Vendor vendor) {
        return CreateVendorCommand.COMMAND_WORD + " " + PREFIX_VENDOR + " " + getVendorDetails(vendor);
    }

    /**
     * Returns the part of command string for the given {@code vendor}'s details.
     */
    public static String getVendorDetails(Vendor vendor) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + vendor.getName().fullName + " ");
        sb.append(PREFIX_PHONE + vendor.getPhone().value + " ");
        sb.append(PREFIX_DESCRIPTION + vendor.getDescription().value + " ");
        vendor.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given
     * {@code EditVendorDescriptor}'s details.
     */
    public static String getEditVendorDescriptorDetails(EditVendorDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getDescription()
                .ifPresent(description -> sb.append(PREFIX_DESCRIPTION).append(description.value).append(" "));
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
