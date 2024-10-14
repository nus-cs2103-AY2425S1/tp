package seedu.address.model.goodsReceipt;

import java.time.LocalDateTime;

import seedu.address.model.goods.Goods;
import seedu.address.model.person.Name;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class GoodsReceipt {

    private final Goods goods;
    private final Name supplierName;
    private final Date procurementDate;
    private final Date arrivalDate;
    private final boolean isDelivered;
    private final int quantity;
    private final double price;


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
    public boolean isFromSupplier(Name supplier) {
        requireNonNull(supplier);
        return supplierName == supplier;
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
    public GoodsReceipt markAsDelivered() {
        return new GoodsReceipt(this.goods, this.supplierName, this.procurementDate, this.arrivalDate, true
                ,this.quantity, this.price);
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
