package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ReadOnlyReceiptLog;
import seedu.address.model.ReceiptLog;
import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.goodsreceipt.GoodsReceipt;

/**
 * A utility class containing a list of {@code Goods} objects to be used in tests.
 */
public class TypicalGoods {

    public static final Goods APPLE = new GoodsBuilder()
            .withName("Apple")
            .withGoodsCategory(GoodsCategories.CONSUMABLES)
            .build();
    public static final Goods BANANA = new GoodsBuilder()
            .withName("Banana")
            .withGoodsCategory(GoodsCategories.CONSUMABLES)
            .build();
    public static final Goods CARROT = new GoodsBuilder()
            .withName("Carrot")
            .withGoodsCategory(GoodsCategories.CONSUMABLES)
            .build();
    public static final Goods DRAGON_FRUIT = new GoodsBuilder()
            .withName("Dragonfruit")
            .withGoodsCategory(GoodsCategories.CONSUMABLES)
            .build();
    public static final Goods EGGPLANT = new GoodsBuilder()
            .withName("Eggplant")
            .withGoodsCategory(GoodsCategories.CONSUMABLES)
            .build();
    public static final Goods FISH = new GoodsBuilder()
            .withName("Fish")
            .withGoodsCategory(GoodsCategories.CONSUMABLES)
            .build();
    public static final Goods GRAPE = new GoodsBuilder()
            .withName("Grape")
            .withGoodsCategory(GoodsCategories.CONSUMABLES)
            .build();
    public static final Goods HAM = new GoodsBuilder()
            .withName("Ham")
            .withGoodsCategory(GoodsCategories.CONSUMABLES)
            .build();
    public static final Goods ICE_CREAM = new GoodsBuilder()
            .withName("Icecream")
            .withGoodsCategory(GoodsCategories.LIFESTYLE)
            .build();
    public static final Goods JAM = new GoodsBuilder()
            .withName("Jam")
            .withGoodsCategory(GoodsCategories.SPECIALTY)
            .build();

    private TypicalGoods() {} // prevents instantiation

    public static List<Goods> getTypicalGoods() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CARROT, DRAGON_FRUIT, EGGPLANT, FISH, GRAPE, HAM, ICE_CREAM,
                JAM));
    }


    public static ReadOnlyReceiptLog getTypicalGoodsReceipts() {

        ReceiptLog receiptLog = new ReceiptLog();

        List<GoodsReceipt> g = new ArrayList<>(Arrays.asList(
            new GoodsReceiptBuilder().withGoodsName(new GoodsName("Apple")).build(),
            new GoodsReceiptBuilder().withGoodsName(new GoodsName("Banana")).build(),
            new GoodsReceiptBuilder().withGoodsName(new GoodsName("Carrot")).build(),
            new GoodsReceiptBuilder().withGoodsName(new GoodsName("Dragonfruit")).build(),
            new GoodsReceiptBuilder().withGoodsName(new GoodsName("Eggplant")).build(),
            new GoodsReceiptBuilder().withGoodsName(new GoodsName("Fish")).build(),
            new GoodsReceiptBuilder().withGoodsName(new GoodsName("Grape")).build(),
            new GoodsReceiptBuilder().withGoodsName(new GoodsName("Ham")).build(),
            new GoodsReceiptBuilder().withGoodsName(new GoodsName("Icecream")).build(),
            new GoodsReceiptBuilder().withGoodsName(new GoodsName("Jam")).build()
        ));

        receiptLog.setReceipts(g);
        return receiptLog;
    }
}
