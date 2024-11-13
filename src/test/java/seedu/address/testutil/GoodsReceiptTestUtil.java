package seedu.address.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.goodsreceipt.Date;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.model.person.Name;

/**
 * Utility class for the test case involving GoodsUtils
 */
public class GoodsReceiptTestUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter PATTERN_READ = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private static final String DATETIME_VALID_PROCUREMENT = LocalDateTime.now().format(PATTERN_READ);
    private static final String DATETIME_VALID_ARRIVAL = LocalDateTime.now().plusDays(1).format(PATTERN_READ);

    /**
     * Returns a dummy list of goods for test cases.
     */
    public static ArrayList<GoodsReceipt> getDummyGoodsList() {
        GoodsReceipt goods1 = new GoodsReceipt(
                new Goods(new GoodsName("Milk Bread"), GoodsCategories.CONSUMABLES),
                new Name("Alex Yeoh"), new Date(DATETIME_VALID_PROCUREMENT),
                new Date(DATETIME_VALID_ARRIVAL), false, 1, 5.22);

        GoodsReceipt goods2 = new GoodsReceipt(
                new Goods(new GoodsName("Potato Chips"), GoodsCategories.CONSUMABLES),
                new Name("Bernice Yu"), new Date(DATETIME_VALID_PROCUREMENT),
                new Date(DATETIME_VALID_ARRIVAL), false, 5, 3.5);

        GoodsReceipt goods3 = new GoodsReceipt(
                new Goods(new GoodsName("Chocolate Bars"), GoodsCategories.CONSUMABLES),
                new Name("Alex Yeoh"), new Date(DATETIME_VALID_PROCUREMENT),
                new Date(DATETIME_VALID_ARRIVAL), false, 10, 2.0);

        GoodsReceipt goods4 = new GoodsReceipt(
                new Goods(new GoodsName("LEGO Toy"), GoodsCategories.CONSUMABLES),
                new Name("Bernice Yu"), new Date(DATETIME_VALID_PROCUREMENT),
                new Date(DATETIME_VALID_ARRIVAL), false, 1, 100.00);

        ArrayList<GoodsReceipt> goodsList = new ArrayList<>();
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);
        goodsList.add(goods4);

        return goodsList;
    }
}
