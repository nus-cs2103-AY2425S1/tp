package seedu.address.model.goods;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents goods.
 * Garauntees: Immutable; name is valid as declared in {@link #isValidGoodsName(String)};
 *     procurementDate is valid as declared in {@link #isValidProcurementDate(LocalDateTime)};
 *     arrivalDate is valid as declared in {@link #isValidArrivalDate(LocalDateTime)}
 */
public class Goods {
    
    public static final String MESSAGE_CONSTRAINTS = "Product quantity cannot be negative or 0!";
    public static final String VALIDATION_REGEX = "/^[\\w\\-\\s]+$/";

    private final String goodsName;
    private final int quantity;
    private final GoodsCategories category;
    private final LocalDateTime procurementDate;
    private final LocalDateTime arrivalDate;
    private final boolean isDelivered;
    private final String supplierName;

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
    public Goods(String goodsName, int quantity, GoodsCategories category, LocalDateTime procurementDate, LocalDateTime arrivalDate, boolean isDelivered, String supplierName) {
        requireAllNonNull(goodsName, quantity, category, procurementDate, arrivalDate, isDelivered, supplierName);
        checkArgument(isValidGoodsName(goodsName));
        checkArgument(isValidProcurementDate(procurementDate));
        checkArgument(isValidArrivalDate(arrivalDate));
        this.goodsName = goodsName;
        this.quantity = quantity;
        this.category = category;
        this.procurementDate = procurementDate;
        this.arrivalDate = arrivalDate;
        this.isDelivered = isDelivered;
        this.supplierName = supplierName;
    }

    /**
     * Returns True if the goods name is valid.
     */
    public static boolean isValidGoodsName(String goodsName) {
        return goodsName.matches(VALIDATION_REGEX);
    }

    /**
     * Returns True if the procurement date is valid.
     */
    public static boolean isValidProcurementDate(LocalDateTime date) {
        return date.isBefore(LocalDateTime.now());
    }

    /**
     * Returns True if the arrival date is valid.
     */
    public static boolean isValidArrivalDate(LocalDateTime date) {
        return date.isAfter(LocalDateTime.now());
    }

    /**
     * Returns True if the goods is from the given supplier.
     * @param supplier Name of the supplier to be checked against.
     */
    public boolean isFromSupplier(String supplier) {
        requireNonNull(supplier);
        return this.supplierName == supplier;
    }

    /**
     * Return a new goods object with the new delivery status.
     */
    public Goods markAsDelivered() {
        return new Goods(goodsName, quantity, category, procurementDate, arrivalDate, true, supplierName);
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
