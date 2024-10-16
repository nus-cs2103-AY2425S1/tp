package seedu.address.model.goodsReceipt;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

import seedu.address.model.goods.Goods;
import seedu.address.model.person.Name;


/**
 * The {@code GoodsReceipt} class represents the receipt of goods from a supplier
 * Guarantees: Immutable;
 */
public class GoodsReceipt {

    private final Goods goods;
    private final Name supplierName;
    private final Date procurementDate;
    private final Date arrivalDate;
    private final boolean isDelivered;
    private final int quantity;
    private final double price;

    /**
     * Constructs a {@code GoodsReceipt}.
     *
     * @param goods The goods associated with this receipt.
     * @param supplierName The name of the supplier.
     * @param procurementDate The date when the goods were procured.
     * @param arrivalDate The date when the goods are expected to or have arrived.
     * @param isDelivered A boolean indicating whether the goods have been delivered.
     * @param quantity The quantity of goods in this receipt.
     * @param price The price of the goods in this receipt.
     */
    public GoodsReceipt(Goods goods, Name supplierName, Date procurementDate, Date arrivalDate,
                        boolean isDelivered, int quantity, double price) {
        requireAllNonNull(goods, quantity, procurementDate, arrivalDate, isDelivered);
        checkArgument(isValidProcurementDate(procurementDate));
        this.supplierName = supplierName;
        this.goods = goods;
        this.procurementDate = procurementDate;
        this.arrivalDate = arrivalDate;
        this.isDelivered = isDelivered;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Returns True if the procurement date is valid.
     */
    public static boolean isValidProcurementDate(Date date) {
        return date.getDateTime().isBefore(LocalDateTime.now());
    }

    /**
     * Returns True if supplier is valid
     */
    public boolean isFromSupplier(Name supplier) {
        requireNonNull(supplier);
        return supplierName.equals(supplier);
    }

    /**
     * Returns quantity of the goods.
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Returns if goods are delivered
     */
    public boolean isDelivered() { return isDelivered; }

    /**
     * Returns goods.
     */
    public Goods getGoods() {
        return this.goods;
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
    public GoodsReceipt markAsDelivered() {
        return new GoodsReceipt(this.goods, this.supplierName, this.procurementDate, this.arrivalDate, true,
                this.quantity, this.price);
    }

    /**
     * Format goods as text for viewing.
     */
    @Override
    public String toString() {
        String deliveryStatus = this.isDelivered ? "Delivered" : "Pending";
        return String.format("[Quantity %d] %s (%s)", this.quantity, this.goods.toString(), deliveryStatus);
    }

}
