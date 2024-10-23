package seedu.address.testutil;

import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.goodsreceipt.Date;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.model.person.Name;

/**
 *
 */
public class GoodsReceiptBuilder {
    public static final double DEFAULT_PRICE = 10.00;
    public static final int DEFAULT_QUANTITY = 1;
    public static final boolean DEFAULT_IS_DELIVERED = false;
    public static final Name DEFAULT_SUPPLIER_NAME = new Name("Supplier");
    public static final GoodsName DEFAULT_GOODS_NAME = new GoodsName("Apple");
    public static final Date DEFAULT_PROCUREMENT_DATE = new Date("2021-01-01 00:00");
    public static final Date DEFAULT_ARRIVAL_DATE = new Date("2021-01-01 00:00");

    private Goods goods;
    private GoodsName goodsName;
    private Name supplierName;
    private Date procurementDate;
    private Date arrivalDate;
    private boolean isDelivered;
    private int quantity;
    private double price;

    /**
     * Creates a {@code GoodsReceiptBuilder} with the default details.
     */
    public GoodsReceiptBuilder() {
        goodsName = DEFAULT_GOODS_NAME;
        supplierName = DEFAULT_SUPPLIER_NAME;
        procurementDate = DEFAULT_PROCUREMENT_DATE;
        arrivalDate = DEFAULT_ARRIVAL_DATE;
        isDelivered = DEFAULT_IS_DELIVERED;
        quantity = DEFAULT_QUANTITY;
        price = DEFAULT_PRICE;
    }

    public GoodsReceiptBuilder withGoods(Goods goods) {
        this.goods = goods;
        return this;
    }

    /**
     * Sets the {@code GoodsName} of the {@code GoodsReceipt} that we are building.
     */
    public GoodsReceiptBuilder withGoodsName(GoodsName goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    /**
     * Sets the {@code supplierName} of the {@code GoodsReceipt} that we are building.
     */
    public GoodsReceiptBuilder withSupplierName(Name supplierName) {
        this.supplierName = supplierName;
        return this;
    }

    /**
     * Sets the {@code procurementDate} of the {@code GoodsReceipt} that we are building.
     */
    public GoodsReceiptBuilder withProcurementDate(Date procurementDate) {
        this.procurementDate = procurementDate;
        return this;
    }

    /**
     * Sets the {@code arrivalDate} of the {@code GoodsReceipt} that we are building.
     */
    public GoodsReceiptBuilder withArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    /**
     * Sets the {@code isDelivered} of the {@code GoodsReceipt} that we are building.
     */
    public GoodsReceiptBuilder withIsDelivered(boolean isDelivered) {
        this.isDelivered = isDelivered;
        return this;
    }

    /**
     * Sets the {@code price} of the {@code GoodsReceipt} that we are building.
     */
    public GoodsReceiptBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Sets the {@code price} of the {@code GoodsReceipt} that we are building.
     */
    public GoodsReceiptBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    /**
     * Builds a {@code GoodsReceipt} object with the given parameters.
     *
     * @return a GoodsReceipt object
     */
    public GoodsReceipt build() {
        Goods buildGoods = goods;
        if (buildGoods == null) {
            buildGoods = new Goods(goodsName, GoodsCategories.CONSUMABLES);
        }
        return new GoodsReceipt(buildGoods, supplierName,
                procurementDate, arrivalDate, isDelivered, quantity, price);
    }
}
