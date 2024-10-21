package seedu.address.testutil.buyer;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.buyer.AddCommand;
import seedu.address.logic.commands.buyer.EditCommand.EditBuyerDescriptor;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Buyer.
 */
public class BuyerUtil {

    /**
     * Returns an add command string for adding the {@code buyer}.
     */
    public static String getAddBuyerCommand(Buyer buyer) {
        return AddCommand.COMMAND_WORD + " " + getBuyerDetails(buyer);
    }

    /**
     * Returns the part of command string for the given {@code buyer}'s details.
     */
    public static String getBuyerDetails(Buyer buyer) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + buyer.getName().fullName + " ");
        sb.append(PREFIX_PHONE + buyer.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + buyer.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + buyer.getAddress().value + " ");
        sb.append(PREFIX_BUYER_TYPE + buyer.getBuyerType().value.toString() + " ");
        buyer.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditBuyerDescriptor}'s details.
     */
    public static String getEditBuyerDescriptorDetails(EditBuyerDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getBuyerType().ifPresent(buyerType -> sb.append(PREFIX_BUYER_TYPE).append(buyerType.value)
                .append(" "));
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
