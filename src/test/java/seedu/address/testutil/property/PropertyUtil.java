package seedu.address.testutil.property;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASKING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.commands.property.AddCommand;
import seedu.address.logic.commands.property.EditCommand.EditPropertyDescriptor;
import seedu.address.model.property.Property;

/**
 * A utility class for Property.
 */
public class PropertyUtil {

    /**
     * Returns an add command string for adding the {@code property}.
     */
    public static String getAddPropertyCommand(Property property) {
        return AddCommand.COMMAND_WORD + " " + getPropertyDetails(property);
    }

    /**
     * Returns the part of command string for the given {@code property}'s details.
     */
    public static String getPropertyDetails(Property property) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + property.getLandlordName().fullName + " ");
        sb.append(PREFIX_PHONE + property.getPhone().value + " ");
        sb.append(PREFIX_ADDRESS + property.getAddress().value + " ");
        sb.append(PREFIX_ASKING_PRICE + property.getAskingPrice().value + " ");
        sb.append(PREFIX_TYPE + property.getPropertyType().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPropertyDescriptor}'s details.
     */
    public static String getEditPropertyDescriptorDetails(EditPropertyDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getLandlordName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getAskingPrice().ifPresent(askingPrice -> sb.append(PREFIX_ASKING_PRICE).append(askingPrice.value)
                .append(" "));
        descriptor.getType().ifPresent(propertyType -> sb.append(PREFIX_TYPE).append(propertyType.value).append(" "));
        return sb.toString();
    }
}
