package seedu.address.model.goods;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

import seedu.address.model.person.Name;

/**
 * Represents goods.
 * Garauntees: Immutable; name is valid as declared in {@link #isValidGoodsName(String)};
 *     procurementDate is valid as declared in {@link #isValidProcurementDate(Date)};
 *     arrivalDate is valid as declared in {@link #isValidArrivalDate(Date)}
 */
public class Goods {

    public static final String MESSAGE_CONSTRAINTS = "Product quantity cannot be negative or 0!";
    public static final String VALIDATION_REGEX = "/^[\\w\\-\\s]+$/";

    private final GoodsName goodsName;
    private final int quantity;
    private final double price;
    private final GoodsCategories category;
    private final Date procurementDate;
    private final Date arrivalDate;
    private final boolean isDelivered;
    private final Name supplierName;

    /**
     * Constructs a {@Code Goods}.
     * All fields should not be null.
     *
     * @param goodsName A valid goods name.
     * @param quantity A valid quantity.
     * @param category A category for the goods.
     * @param procurementDate A valid datetime for the procurement (order) date.
     * @param arrivalDate A valid datetime for the arrival date.
     * @param isDelivered Current delivery status.
     * @param supplierName A valid supplier name.
     */
    public Goods(GoodsName goodsName, int quantity, double price, GoodsCategories category,
            Date procurementDate, Date arrivalDate, boolean isDelivered, Name supplierName) {
        requireAllNonNull(goodsName, quantity, category, procurementDate, arrivalDate, isDelivered, supplierName);
        checkArgument(isValidProcurementDate(procurementDate));
        checkArgument(isValidArrivalDate(arrivalDate));
        this.goodsName = goodsName;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.procurementDate = procurementDate;
        this.arrivalDate = arrivalDate;
        this.isDelivered = isDelivered;
        this.supplierName = supplierName;
    }

    /**
     * Returns True if the procurement date is valid.
     */
    public static boolean isValidProcurementDate(Date date) {
        return date.getDateTime().isBefore(LocalDateTime.now());
    }

    /**
     * Returns True if the arrival date is valid.
     */
    public static boolean isValidArrivalDate(Date date) {
        return date.getDateTime().isAfter(LocalDateTime.now());
    }

    /**
     * Returns True if the goods is from the given supplier.
     * @param supplier Name of the supplier to be checked against.
     */
    public boolean isFromSupplier(Name supplier) {
        requireNonNull(supplier);
        return this.supplierName == supplier;
    }

    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Returns total price of the goods.
     */
    public double getPriceTotal() {
        return this.quantity * this.price;
    }

    /**
     * Return a new goods object with the new delivery status.
     */
    public Goods markAsDelivered() {
        return new Goods(goodsName, quantity, price, category, procurementDate, arrivalDate, true, supplierName);
    }

    /**
     * Format goods as text for viewing.
     */
    @Override
    public String toString() {
        String deliveryStatus = this.isDelivered ? "Delivered" : "Pending";
        return String.format("%s (%s): %d", this.goodsName, deliveryStatus, this.quantity);
    }
}
