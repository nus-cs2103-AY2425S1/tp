package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_INDEX;

import seedu.address.logic.commands.AddDeliveryCommand;
import seedu.address.model.delivery.Delivery;

/**
 * A utility class for Delivery.
 */
public class DeliveryUtil {

    /**
     * Returns an add command string for adding the {@code supplier}.
     */
    public static String getDeliveryCommand(Delivery delivery) {
        return AddDeliveryCommand.COMMAND_WORD + " " + PREFIX_DELIVERY + " " + getDeliveryDetails(delivery);
    }

    /**
     * Returns the part of command string for the given {@code supplier}'s details.
     *
     * @param delivery Delivery object to be tested
     * @return String value of user arguments input
     */
    public static String getDeliveryDetails(Delivery delivery) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DATETIME + " " + delivery.getDeliveryDate().toString() + " ");
        sb.append(PREFIX_SUPPLIER_INDEX + " " + delivery.getSupplierIndex() + " ");
        sb.append(PREFIX_PRODUCT + " " + delivery.getDeliveryProduct().toString() + " ");
        sb.append(PREFIX_QUANTITY + " " + delivery.getDeliveryQuantity() + " ");
        sb.append(PREFIX_COST + " " + delivery.getDeliveryCost());
        return sb.toString();
    }
}
