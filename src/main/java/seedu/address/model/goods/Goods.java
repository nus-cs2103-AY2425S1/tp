package seedu.address.model.goods;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.storage.CsvConverters.GoodsDateConverter;
import static seedu.address.storage.CsvConverters.GoodsNameConverter;
import static seedu.address.storage.CsvConverters.PersonNameConverter;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvNumber;

import seedu.address.model.person.Name;

/**
 * Represents goods.
 * Garauntees: Immutable; name is valid as declared in {@link #isValidGoodsName(String)};
 *     procurementDate is valid as declared in {@link #isValidProcurementDate(Date)};
 */
public class Goods {

    public static final String MESSAGE_CONSTRAINTS = "Product quantity cannot be negative or 0!";
    public static final String VALIDATION_REGEX = "/^[\\w\\-\\s]+$/";

    @CsvCustomBindByName(column = "Goods Name", converter = GoodsNameConverter.class, required = true)
    private final GoodsName goodsName;

    @CsvBindByName(column = "Quantity", required = true)
    @CsvNumber(value = "#")
    private final int quantity;

    @CsvBindByName(column = "Price", required = true)
    @CsvNumber(value = "0.00")
    private final double price;

    @CsvBindByName(column = "Category", required = true)
    private final GoodsCategories category;

    @CsvCustomBindByName(column = "Procurement Date", converter = GoodsDateConverter.class, required = true)
    @CsvDate(value = "yyyy-MM-dd HH:mm")
    private final Date procurementDate;

    @CsvCustomBindByName(column = "Arrival Date", converter = GoodsDateConverter.class, required = true)
    @CsvDate(value = "yyyy-MM-dd HH:mm")
    private final Date arrivalDate;

    @CsvBindByName(column = "Is Delivered", required = true)
    private final boolean isDelivered;

    @CsvCustomBindByName(column = "Supplier Name", converter = PersonNameConverter.class, required = true)
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
     * Returns True if the goods name is valid.
     *
     * @param test String for goods name
     */
    public static boolean isValidGoodsName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns True if the procurement date is valid.
     */
    public static boolean isValidProcurementDate(Date date) {
        return date.getDateTime().isBefore(LocalDateTime.now());
    }

    /**
     * Returns True if the goods is from the given supplier.
     *
     * @param supplier Name of the supplier to be checked against.
     */
    public boolean isFromSupplier(Name supplier) {
        requireNonNull(supplier);
        return this.supplierName.equals(supplier);
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
        return String.format("[Quantity %d] %s (%s)", this.quantity, this.goodsName, deliveryStatus);
    }
}
